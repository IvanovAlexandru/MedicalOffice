package com.example.proiectposmongo.hateoas;

import com.example.proiectposmongo.controllers.ConsultatieController;
import com.example.proiectposmongo.dto.ConsultatieDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConsultatieHateoas implements RepresentationModelAssembler<ConsultatieDTO, EntityModel<ConsultatieDTO>> {

    @Override
    public EntityModel<ConsultatieDTO> toModel(ConsultatieDTO consultatieDTO) {
        return EntityModel.of(consultatieDTO, //
                linkTo(methodOn(ConsultatieController.class).getConsultatieById(consultatieDTO.getId())).withSelfRel(),
                linkTo(methodOn(ConsultatieController.class).getAllConsultatii()).withRel("consultatii"));
    }
}
