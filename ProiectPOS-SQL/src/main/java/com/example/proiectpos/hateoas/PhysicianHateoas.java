package com.example.proiectpos.hateoas;

import com.example.proiectpos.controllers.PatientController;
import com.example.proiectpos.controllers.PhysicianController;
import com.example.proiectpos.dto.PatientDTO;
import com.example.proiectpos.dto.PhysicianDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PhysicianHateoas implements RepresentationModelAssembler<PhysicianDTO, EntityModel<PhysicianDTO>> {

    @Override
    public EntityModel<PhysicianDTO> toModel(PhysicianDTO physicianDTO) {
        return EntityModel.of(physicianDTO, //
                linkTo(methodOn(PhysicianController.class).getPhysicianById(physicianDTO.getId_doctor())).withSelfRel(),
                linkTo(methodOn(PhysicianController.class).getAllPhysicians(null,null,null,null)).withRel("physicians"));
    }
}