package com.example.proiectpos.services;

import com.example.proiectpos.dto.PhysicianDTO;
import com.example.proiectpos.utils.SpecializationEnum;
import org.springframework.stereotype.Service;

@Service
public class PhysicianValidationService {
    public boolean isValidPhysicianDTO(PhysicianDTO physicianDTO) {
        return physicianDTO != null
                && isValidName(physicianDTO.getNume())
                && isValidName(physicianDTO.getPrenume())
                && isValidEmail(physicianDTO.getEmail())
                && isValidPhoneNumber(physicianDTO.getTelefon())
                && physicianDTO.getSpecialization() != null;
    }

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.startsWith("07");
    }
    private boolean isValidSpecialization(SpecializationEnum specializationEnum){
        return specializationEnum != null && (specializationEnum == SpecializationEnum.Neurologist
        || specializationEnum == SpecializationEnum.Cardiologist || specializationEnum == SpecializationEnum.Pediatrician
        || specializationEnum == SpecializationEnum.Dermatologist || specializationEnum == SpecializationEnum.Psychiatrist);
    }
}
