package com.example.proiectpos.hateoas;

import com.example.proiectpos.controllers.AppointmentController;
import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PhysicianPatientMappingHateoas implements RepresentationModelAssembler<PhysiciansPatientsMapping, EntityModel<PhysiciansPatientsMapping>> {
    @Override
    public EntityModel<PhysiciansPatientsMapping> toModel(PhysiciansPatientsMapping physiciansPatientsMapping) {
        return EntityModel.of(physiciansPatientsMapping,
                linkTo(methodOn(AppointmentController.class).getAppointmentById(physiciansPatientsMapping.getId())).withSelfRel(),
                linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("appointments"));
    }
}
