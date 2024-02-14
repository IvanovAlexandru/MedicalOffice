package com.example.proiectpos.controllers;

import com.example.proiectpos.configs.GrpcClientService;
import com.example.proiectpos.dto.PatientDTO;
import com.example.proiectpos.dto.PhysicianDTO;
import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import com.example.proiectpos.hateoas.PatientHateoas;
import com.example.proiectpos.hateoas.PhysicianHateoas;
import com.example.proiectpos.hateoas.PhysicianPatientMappingHateoas;
import com.example.proiectpos.repositories.PhysicianPatientMappingRepository;
import com.example.proiectpos.repositories.PhysicianRepository;
import com.example.proiectpos.services.PhysicianValidationService;
import com.example.proiectpos.utils.SpecializationEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/medical_office/physicians")
@CrossOrigin
public class PhysicianController {

    private final PhysicianRepository physicianRepository;
    private final PhysicianValidationService physicianValidationService;
    private final PhysicianHateoas physicianHateoas;
    private final GrpcClientService grpcClientService;
    private final PatientHateoas patientHateoas;
    private final PhysicianPatientMappingRepository physicianPatientMappingRepository;
    private final PhysicianPatientMappingHateoas physicianPatientMappingHateoas;
    @Autowired
    public PhysicianController(PhysicianRepository physicianRepository, PhysicianValidationService physicianValidationService, PhysicianHateoas physicianHateoas, GrpcClientService grpcClientService, PatientHateoas patientHateoas, PhysicianPatientMappingRepository physicianPatientMappingRepository, PhysicianPatientMappingHateoas physicianPatientMappingHateoas) {
        this.physicianRepository = physicianRepository;
        this.physicianValidationService = physicianValidationService;
        this.physicianHateoas = physicianHateoas;
        this.grpcClientService = grpcClientService;
        this.patientHateoas = patientHateoas;
        this.physicianPatientMappingRepository = physicianPatientMappingRepository;
        this.physicianPatientMappingHateoas = physicianPatientMappingHateoas;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PhysicianDTO>>> getAllPhysicians(@RequestParam(required = false) String name,
                                                                                       @RequestParam(required = false) SpecializationEnum specialization,
                                                                                       @RequestParam(defaultValue = "0") Integer page,
                                                                                       @RequestParam(defaultValue = "10") Integer size){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Admin") || role.equals("Pacient") || role.equals("Doctor")){
                if(name != null){
                    List<PhysicianDTO> physicianDTOList = physicianRepository.findByNumeContainsIgnoreCase(name);
                    if(physicianDTOList != null){
                        return new ResponseEntity<>(physicianHateoas.toCollectionModel(physicianDTOList),HttpStatus.FOUND);
                    }
                    else {
                        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                    }
                }
                if(specialization != null){
                    List<PhysicianDTO> physicianDTOList = physicianRepository.findBySpecialization(specialization);
                    if(physicianDTOList != null){
                        return new ResponseEntity<>(physicianHateoas.toCollectionModel(physicianDTOList),HttpStatus.FOUND);
                    }
                    else{
                        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                    }
                }
                if(page != null || size != null){
                    PageRequest pageable = PageRequest.of(page, size);
                    Page<PhysicianDTO> physicianPage = physicianRepository.findAll(pageable);
                    List<PhysicianDTO> physicianDTOList = physicianPage.toList();
                    if(physicianRepository.findAll().isEmpty()){
                        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                    }
                    else{
                        return new ResponseEntity<>(physicianHateoas.toCollectionModel(physicianDTOList), HttpStatus.OK);
                    }
                }
                if(name == null && specialization == null && page == null && size == null){
                    List<PhysicianDTO> physicianDTOList = physicianRepository.findAll();
                    if(!physicianDTOList.isEmpty()){
                        return new ResponseEntity<>(physicianHateoas.toCollectionModel(physicianDTOList),HttpStatus.OK);
                    }
                    else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PhysicianDTO>> getPhysicianById(@PathVariable("id") Integer id){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Admin") || role.equals("Pacient") || role.equals("Doctor")){
                PhysicianDTO physicianDTO = physicianRepository.findById(id).orElse(null);
                if(physicianDTO == null){
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
                else {
                    return new ResponseEntity<>(physicianHateoas.toModel(physicianDTO),HttpStatus.OK);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);

    }

    @PostMapping
    public ResponseEntity<EntityModel<PhysicianDTO>> addPhysician(@RequestBody PhysicianDTO physicianDTO,
                                                                  @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Admin")){
                if(physicianValidationService.isValidPhysicianDTO(physicianDTO)){
                    physicianRepository.save(physicianDTO);
                    return new ResponseEntity<>(physicianHateoas.toModel(physicianDTO),HttpStatus.CREATED);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<PhysicianDTO>> deletePhysicianById(@PathVariable("id") Integer id,
                                                                                          @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Admin")){
                PhysicianDTO physicianDTO = physicianRepository.findById(id).orElseThrow();
                if(physicianDTO != null){
                    physicianRepository.delete(physicianDTO);
                    return new ResponseEntity<>(physicianHateoas.toModel(physicianDTO),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PhysicianDTO>> editCreatePhysicianById(@PathVariable("id") Integer id,
                                                                        @RequestBody PhysicianDTO physicianDTO,
                                                                        @RequestHeader("Authorization") String token){
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor")){
               if(physicianValidationService.isValidPhysicianDTO(physicianDTO)){
                   PhysicianDTO physicianToFind = physicianRepository.findById(id).orElse(null);
                   if(physicianValidationService.isValidPhysicianDTO(physicianToFind)){
                       physicianRepository.save(physicianDTO);
                       return new ResponseEntity<>(physicianHateoas.toModel(physicianDTO),HttpStatus.NO_CONTENT);
                   }
                   else {
                       physicianRepository.save(physicianDTO);
                       return new ResponseEntity<>(physicianHateoas.toModel(physicianDTO),HttpStatus.CREATED);
                   }
               }
               else return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/{id}/patients")
    public ResponseEntity<CollectionModel<EntityModel<PatientDTO>>> getPatientsByDoctorId(@PathVariable Integer id,
                                                                                          @RequestHeader("Authorization") String token){
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Admin") || role.equals("Doctor")){
                List<PatientDTO> patientDTOList = new ArrayList<>();
                PhysicianDTO physicianDTO = physicianRepository.findById(id).orElse(null);
                if(physicianDTO != null){
                    List<PhysiciansPatientsMapping> physiciansPatientsMappings = physicianPatientMappingRepository.findAllByPhysicians(physicianDTO);
                    for (PhysiciansPatientsMapping patient : physiciansPatientsMappings ){
                        patientDTOList.add(patient.getPatients());
                    }
                    if(patientDTOList != null){
                        return new ResponseEntity<>(patientHateoas.toCollectionModel(patientDTOList),HttpStatus.OK);
                    }
                    else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
                else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<CollectionModel<EntityModel<PhysiciansPatientsMapping>>> getDoctorsAppointmentsById(@PathVariable Integer id,
                                                                                                               @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor") || role.equals("Admin")){
                PhysicianDTO physicianDTO = physicianRepository.findById(id).orElse(null);
                if(physicianDTO != null){
                    List<PhysiciansPatientsMapping> physiciansPatientsMappings = physicianPatientMappingRepository.findAllByPhysicians(physicianDTO);
                    if(physiciansPatientsMappings != null){
                        return new ResponseEntity<>(physicianPatientMappingHateoas.toCollectionModel(physiciansPatientsMappings),HttpStatus.OK);
                    }
                    else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
                else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }
}
