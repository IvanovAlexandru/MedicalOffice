package com.example.proiectpos.controllers;

import com.example.proiectpos.configs.GrpcClientService;
import com.example.proiectpos.dto.PatientDTO;
import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import com.example.proiectpos.hateoas.PatientHateoas;
import com.example.proiectpos.hateoas.PhysicianPatientMappingHateoas;
import com.example.proiectpos.repositories.PatientRepository;
import com.example.proiectpos.repositories.PhysicianPatientMappingRepository;
import com.example.proiectpos.repositories.PhysiciansPatientsMappingRepositoryImplementation;
import com.example.proiectpos.services.PatientValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/medical_office/patients")
@CrossOrigin
public class PatientController {
    private final PatientRepository patientRepository;
    private final PatientHateoas patientHateoas;
    private final GrpcClientService grpcClientConfig;
    private final PatientValidationService patientValidationService;
    private final PhysicianPatientMappingHateoas physicianPatientMappingHateoas;
    private final PhysicianPatientMappingRepository physicianPatientMappingRepository;
    private final PhysiciansPatientsMappingRepositoryImplementation physiciansPatientsMappingRepositoryImplementation;
    @Autowired
    public PatientController(PatientRepository patientRepository, PatientHateoas patientHateoas, GrpcClientService grpcClientConfig, PatientValidationService patientValidationService, PhysicianPatientMappingHateoas physicianPatientMappingHateoas, PhysicianPatientMappingRepository physicianPatientMappingRepository, PhysiciansPatientsMappingRepositoryImplementation physiciansPatientsMappingRepositoryImplementation) {
        this.patientRepository = patientRepository;
        this.patientHateoas = patientHateoas;
        this.grpcClientConfig = grpcClientConfig;
        this.patientValidationService = patientValidationService;
        this.physicianPatientMappingHateoas = physicianPatientMappingHateoas;
        this.physicianPatientMappingRepository = physicianPatientMappingRepository;
        this.physiciansPatientsMappingRepositoryImplementation = physiciansPatientsMappingRepositoryImplementation;
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PatientDTO>>> getAllPatients(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Admin") || role.equals("Doctor")){
                if(patientRepository.findAll().isEmpty()){
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
                else {
                    return new ResponseEntity<>(patientHateoas.toCollectionModel(patientRepository.findAll()), HttpStatus.OK);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/{cnp}")
    public ResponseEntity<EntityModel<PatientDTO>> getPatientById(@PathVariable("cnp") String cnp){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Admin") || role.equals("Doctor")){
                PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);
                if(patientDTO != null){
                    return new ResponseEntity<>(patientHateoas.toModel(patientDTO),HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else {
                if(role.equals("Pacient")){
                    PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);
                    if(patientDTO != null){
                        if(patientDTO.getIdUser() == Integer.parseInt(grpcClientConfig.getId(token))){
                            return new ResponseEntity<>(patientHateoas.toModel(patientDTO),HttpStatus.OK);
                        }
                        else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
                    }
                    else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

                }else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{cnp}")
    @Transactional
    public ResponseEntity<EntityModel<PatientDTO>> createEditPatient(@RequestBody PatientDTO patientDTO,
                                                                     @PathVariable("cnp") String cnp,
                                                                     @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Pacient") || role.equals("Admin")){
                if(patientValidationService.isValidPatient(patientDTO)) {
                    PatientDTO patientToFind = patientRepository.findById(cnp).orElse(null);
                    if (patientValidationService.isValidPatient(patientToFind)) {
                        patientRepository.save(patientDTO);
                        return new ResponseEntity<>(patientHateoas.toModel(patientDTO),HttpStatus.NO_CONTENT);
                    }
                    else {
                        patientRepository.save(patientDTO);
                        return new ResponseEntity<>(patientHateoas.toModel(patientDTO),HttpStatus.CREATED);
                    }
                }
                return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{cnp}")
    public ResponseEntity<EntityModel<PatientDTO>> deletePatientById(@PathVariable String cnp,
                                                                     @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Admin")){
                PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);
                if(patientDTO != null){
                    patientRepository.delete(patientDTO);
                    return new ResponseEntity<>(patientHateoas.toModel(patientDTO),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{cnp}/appointments")
    public ResponseEntity<CollectionModel<EntityModel<PhysiciansPatientsMapping>>> getPatientsAppointmentsById(@PathVariable String cnp,
                                                                                                           @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Pacient") || role.equals("Doctor")){
                PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);
                if(patientDTO != null){
                    List<PhysiciansPatientsMapping> physiciansPatientsMappings = physicianPatientMappingRepository.findAllByPatients(patientDTO);
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

    @GetMapping("/date/{cnp}/appointments")
    public ResponseEntity<CollectionModel<EntityModel<PhysiciansPatientsMapping>>> searchAppointmentByDate(@PathVariable String cnp,
                                                                                                           @RequestParam("date")Integer date,
                                                                                                           @RequestParam("type")String type,
                                                                                                           @RequestHeader("Authorization") String token){
        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Doctor") || role.equals("Pacient")){
                List<PhysiciansPatientsMapping> physiciansPatientsMapping = physiciansPatientsMappingRepositoryImplementation.getProgramareByDateType(cnp,date,type);
                if(physiciansPatientsMapping != null){
                    return new ResponseEntity<>(physicianPatientMappingHateoas.toCollectionModel(physiciansPatientsMapping),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/id/{id_user}")
    public ResponseEntity<EntityModel<PatientDTO>> getByUserId(@PathVariable Integer id_user,
                                                               @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Pacient")){
                if(id_user == Integer.parseInt(grpcClientConfig.getId(token))){
                    PatientDTO patientDTO = patientRepository.findByIdUser(id_user);
                    if(patientDTO != null){
                        return new ResponseEntity<>(patientHateoas.toModel(patientDTO),HttpStatus.OK);
                    }
                    else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
                else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/history/{cnp}/appointments")
    public ResponseEntity<CollectionModel<EntityModel<PhysiciansPatientsMapping>>> getHistory(@PathVariable("cnp") String cnp,
                                                                                              @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientConfig.getUserRole(token);
            if(role.equals("Pacient")){
                PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);
                if(patientValidationService.isValidPatient(patientDTO)){
                    List<PhysiciansPatientsMapping> physiciansPatientsMappings = physicianPatientMappingRepository
                            .findAllByPatientsAndDateBefore(patientDTO,new Date());
                    return new ResponseEntity<>(physicianPatientMappingHateoas.toCollectionModel(physiciansPatientsMappings),HttpStatus.OK);
                }
                else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }
}
