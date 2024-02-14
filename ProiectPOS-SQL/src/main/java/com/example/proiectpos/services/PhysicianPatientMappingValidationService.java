package com.example.proiectpos.services;

import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import com.example.proiectpos.utils.StatusEnum;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PhysicianPatientMappingValidationService {

    public boolean isValidPhysiciansPatientsMapping(PhysiciansPatientsMapping mapping) {
        return mapping != null
                && mapping.getPhysicians() != null
                && mapping.getPatients() != null
                && isValidDate(mapping.getDate())
                && isValidStatus(mapping.getStatus());
    }

    public boolean isValidDate(Date date){
        return date != null && date.after(new Date());
    }

    public boolean isValidStatus(StatusEnum statusEnum){
        return (statusEnum == StatusEnum.Neprezentat
                || statusEnum == StatusEnum.Anulat || statusEnum == StatusEnum.Onorata);
    }
}
