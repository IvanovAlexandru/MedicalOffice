package com.example.proiectpos.controllers;

import com.example.proiectpos.configs.GrpcClientService;
import com.example.proiectpos.dto.PatientDTO;
import com.example.proiectpos.dto.PhysicianDTO;
import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import com.example.proiectpos.hateoas.PatientHateoas;
import com.example.proiectpos.hateoas.PhysicianPatientMappingHateoas;
import com.example.proiectpos.repositories.PatientRepository;
import com.example.proiectpos.repositories.PhysicianPatientMappingRepository;
import com.example.proiectpos.repositories.PhysicianRepository;
import com.example.proiectpos.repositories.PhysiciansPatientsMappingRepositoryImplementation;
import com.example.proiectpos.services.PhysicianPatientMappingValidationService;
import com.example.proiectpos.utils.StatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/medical_office/appointments")
@CrossOrigin
public class AppointmentController {

    private final PhysicianPatientMappingRepository physicianPatientMappingRepository;
    private final PhysicianPatientMappingHateoas physicianPatientMappingHateoas;
    private final GrpcClientService grpcClientService;
    private final PhysicianPatientMappingValidationService physicianPatientMappingValidationService;

    @Autowired
    public AppointmentController(PhysicianPatientMappingRepository physicianPatientMappingRepository, PhysicianPatientMappingHateoas physicianPatientMappingHateoas, GrpcClientService grpcClientService, PhysicianPatientMappingValidationService physicianPatientMappingValidationService) {
        this.physicianPatientMappingRepository = physicianPatientMappingRepository;
        this.physicianPatientMappingHateoas = physicianPatientMappingHateoas;
        this.grpcClientService = grpcClientService;
        this.physicianPatientMappingValidationService = physicianPatientMappingValidationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PhysiciansPatientsMapping>> getAppointmentById(@PathVariable("id") Integer id){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Pacient") || role.equals("Doctor")){
                PhysiciansPatientsMapping physiciansPatientsMapping = physicianPatientMappingRepository.findById(id).orElse(null);
                if(physiciansPatientsMapping != null){
                    return new ResponseEntity<>(physicianPatientMappingHateoas.toModel(physiciansPatientsMapping),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PhysiciansPatientsMapping>>> getAllAppointments(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Pacient") || role.equals("Doctor")){
                List<PhysiciansPatientsMapping> physiciansPatientsMappings = physicianPatientMappingRepository.findAll();
                if(physiciansPatientsMappings != null){
                    return new ResponseEntity<>(physicianPatientMappingHateoas.toCollectionModel(physiciansPatientsMappings),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<EntityModel<PhysiciansPatientsMapping>> addAppointment(@RequestBody PhysiciansPatientsMapping physiciansPatientsMapping,
                                                                                 @RequestHeader("Authorization") String token){
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Pacient") || role.equals("Admin") || role.equals("Doctor")){
                if(physicianPatientMappingValidationService.isValidPhysiciansPatientsMapping(physiciansPatientsMapping)){
                    physicianPatientMappingRepository.save(physiciansPatientsMapping);
                    return new ResponseEntity<>(physicianPatientMappingHateoas.toModel(physiciansPatientsMapping), HttpStatus.CREATED);
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
    public ResponseEntity<EntityModel<PhysiciansPatientsMapping>> deletePatientAppointment(@PathVariable("id") Integer id,
                                                                                                            @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor")){
                PhysiciansPatientsMapping physiciansPatientsMapping = physicianPatientMappingRepository.findById(id).orElse(null);
                if(physiciansPatientsMapping != null){
                    physicianPatientMappingRepository.deleteById(id);
                    return new ResponseEntity<>(physicianPatientMappingHateoas.toModel(physiciansPatientsMapping),HttpStatus.OK);
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
    public ResponseEntity<EntityModel<PhysiciansPatientsMapping>> changeAppointment(@PathVariable("id") Integer id,
                                                                                          @RequestBody PhysiciansPatientsMapping physiciansPatientsMapping,
                                                                                          @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor")){
                if(physicianPatientMappingValidationService.isValidPhysiciansPatientsMapping(physiciansPatientsMapping)){
                    PhysiciansPatientsMapping physiciansPatientsMappingToFind = physicianPatientMappingRepository.findById(id).orElse(null);
                    if(physicianPatientMappingValidationService.isValidPhysiciansPatientsMapping(physiciansPatientsMappingToFind)){
                        physicianPatientMappingRepository.save(physiciansPatientsMapping);
                        return new ResponseEntity<>(physicianPatientMappingHateoas.toModel(physiciansPatientsMapping),HttpStatus.NO_CONTENT);
                    }
                    else return new ResponseEntity<>(physicianPatientMappingHateoas.toModel(physiciansPatientsMapping),HttpStatus.CREATED);
                }
                else return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

}
