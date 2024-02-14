package com.example.proiectposmongo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvestigatieDTO {
    private String denumire;
    private Integer durata_de_procesare;
    private String rezultat;
}
