package com.example.proiectposmongo.controllers;

import com.example.proiectposmongo.configs.GrpcClientService;
import com.example.proiectposmongo.dto.ConsultatieDTO;
import com.example.proiectposmongo.hateoas.ConsultatieHateoas;
import com.example.proiectposmongo.repositories.ConsultatieRepository;
import com.example.proiectposmongo.services.ConsultatieValidateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/medical_office/consultatii")
@CrossOrigin
public class ConsultatieController {
    public final ConsultatieRepository consultatieRepository;
    public final ConsultatieHateoas consultatieHateoas;
    public final GrpcClientService grpcClientService;
    public final ConsultatieValidateService consultatieValidateService;

    @Autowired
    public ConsultatieController(ConsultatieRepository consultatieRepository, ConsultatieHateoas consultatieHateoas, GrpcClientService grpcClientService, ConsultatieValidateService consultatieValidateService) {
        this.consultatieRepository = consultatieRepository;
        this.consultatieHateoas = consultatieHateoas;
        this.grpcClientService = grpcClientService;
        this.consultatieValidateService = consultatieValidateService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ConsultatieDTO>> addConsultatie(@RequestBody ConsultatieDTO consultatieDTO,
                                                                                       @RequestHeader("Authorization") String token){

        if(token != null ){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor") || role.equals("Admin")){
                if(consultatieValidateService.isValidConsultatie(consultatieDTO)){
                    consultatieRepository.insert(consultatieDTO);
                    return new ResponseEntity<>(consultatieHateoas.toModel(consultatieDTO), HttpStatus.CREATED);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ConsultatieDTO>>> getAllConsultatii(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor") || role.equals("Pacient")){
                List<ConsultatieDTO> consultatieDTOList = consultatieRepository.findAll();
                if(consultatieDTOList != null){
                    return new ResponseEntity<>(consultatieHateoas.toCollectionModel(consultatieDTOList),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultatieDTO>> getConsultatieById(@PathVariable("id") String id){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor") || role.equals("Pacient")){
                ConsultatieDTO consultatieDTO = consultatieRepository.findById(id).orElse(null);
                if(consultatieDTO != null){
                    return new ResponseEntity<>(consultatieHateoas.toModel(consultatieDTO),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultatieDTO>> deleteConsultatieById(@PathVariable("id") String id,
                                                                                              @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor")){
                ConsultatieDTO consultatieDTO = consultatieRepository.findById(id).orElse(null);
                if(consultatieDTO != null){
                    consultatieRepository.delete(consultatieDTO);
                    return new ResponseEntity<>(consultatieHateoas.toModel(consultatieDTO),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            }
            else return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultatieDTO>> editConsultatieById(@PathVariable("id") String id,
                                                                      @RequestBody ConsultatieDTO consultatieDTO,
                                                                      @RequestHeader("Authorization") String token){

        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Doctor")){
                if(consultatieValidateService.isValidConsultatie(consultatieDTO)){
                    ConsultatieDTO consultatieDTOToFind = consultatieRepository.findById(id).orElse(null);
                    if(consultatieValidateService.isValidConsultatie(consultatieDTOToFind)){
                        consultatieRepository.save(consultatieDTO);
                        return new ResponseEntity<>(consultatieHateoas.toModel(consultatieDTO),HttpStatus.NO_CONTENT);
                    }
                    else {
                        return new ResponseEntity<>(consultatieHateoas.toModel(consultatieDTO),HttpStatus.CREATED);
                    }
                }
                else return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id_user}/{id_doctor}")
    public ResponseEntity<CollectionModel<EntityModel<ConsultatieDTO>>> getConsultatieForProgramare(@PathVariable Integer id_user,
                                                                                   @PathVariable Integer id_doctor,
                                                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                                                   @RequestHeader("Authorization") String token){
        if(token != null){
            String role = grpcClientService.getUserRole(token);
            if(role.equals("Pacient") || role.equals("Doctor") || role.equals("Admin")){
                List<ConsultatieDTO> consultatieDTOList = consultatieRepository.findAllByPacientAndDoctor(id_user,id_doctor);
                if(consultatieDTOList.isEmpty()){
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
                List<ConsultatieDTO> consultatieDTOListDate = consultatieDTOList.stream().filter(consultatie -> consultatie.getData().getDay() == date.getDay()
                        && consultatie.getData().getMonth() == date.getMonth()
                        && consultatie.getData().getYear() == date.getYear()).toList();
                if(!consultatieDTOListDate.isEmpty()){
                    return new ResponseEntity<>(consultatieHateoas.toCollectionModel(consultatieDTOListDate),HttpStatus.OK);
                }
                else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }
}
