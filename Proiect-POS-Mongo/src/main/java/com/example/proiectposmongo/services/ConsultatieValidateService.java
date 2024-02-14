package com.example.proiectposmongo.services;

import com.example.proiectposmongo.dto.ConsultatieDTO;
import com.example.proiectposmongo.utils.DiagnosticEnum;
import org.springframework.stereotype.Service;

@Service
public class ConsultatieValidateService {

    public boolean isValidConsultatie(ConsultatieDTO consultatieDTO){
        return consultatieDTO.getPacient() != null
                && consultatieDTO.getDoctor() != null
                && consultatieDTO.getData() != null
                && isValidDiagnostic(consultatieDTO.getDiagnostic());
    }

    public boolean isValidDiagnostic(DiagnosticEnum diagnosticEnum){
        return diagnosticEnum == DiagnosticEnum.Bolnav || diagnosticEnum == DiagnosticEnum.Sanatos;
    }
}
