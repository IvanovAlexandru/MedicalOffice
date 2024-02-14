package com.example.proiectpos.hateoas;

import com.example.proiectpos.controllers.PatientController;
import com.example.proiectpos.dto.PatientDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PatientHateoas implements RepresentationModelAssembler<PatientDTO, EntityModel<PatientDTO>> {

    @Override
    public EntityModel<PatientDTO> toModel(PatientDTO patientDTO) {
        return EntityModel.of(patientDTO, //
                linkTo(methodOn(PatientController.class).getPatientById(patientDTO.getCnp())).withSelfRel(),
                linkTo(methodOn(PatientController.class).getAllPatients()).withRel("patients"));
    }
}
