package com.example.proiectpos.services;

import com.example.proiectpos.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PatientValidationService {
    public boolean isValidPatient(PatientDTO patientDTO) {
        return patientDTO != null
                && isValidCNP(patientDTO.getCnp())
                && patientDTO.getNume() != null
                && patientDTO.getCnp() != null
                && isValidEmail(patientDTO.getEmail())
                && isValidPhoneNumber(patientDTO.getTelefon())
                && isValidData(patientDTO.getData_nasterii());
    }

    private boolean isValidCNP(String cnp) {
        return cnp != null && !cnp.isEmpty() && (cnp.startsWith("5") || cnp.startsWith("6")) && cnp.length() == 13;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && !phoneNumber.isEmpty() && phoneNumber.startsWith("07");
    }
    private boolean isValidData(Date date){
        return date != null && date.before(new Date());
    }
}
