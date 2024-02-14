package com.example.proiectpos.dto;

import com.example.proiectpos.utils.SpecializationEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhysicianDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_doctor;
    private Integer id_user;
    private String nume;
    private String prenume;
    @Column(unique = true)
    private String email;
    private String telefon;
    @Enumerated(EnumType.STRING)
    private SpecializationEnum specialization;
    @OneToMany(mappedBy = "physicians")
    @JsonIgnore
    private Set<PhysiciansPatientsMapping> programare;
    public PhysicianDTO(Integer id_user, String nume, String prenume, String email, String telefon, SpecializationEnum specializationEnum) {
        this.id_user = id_user;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
        this.specialization = specializationEnum;
    }
}
