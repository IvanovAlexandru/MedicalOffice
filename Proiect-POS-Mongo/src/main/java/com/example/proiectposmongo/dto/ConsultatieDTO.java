package com.example.proiectposmongo.dto;


import com.example.proiectposmongo.utils.DiagnosticEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConsultatieDTO {
    @Id
    private String id;
    private Integer pacient;
    private Integer doctor;
    private Date data;
    private DiagnosticEnum diagnostic;
    private List<InvestigatieDTO> investigatii;
}
