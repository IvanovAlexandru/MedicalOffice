package com.example.proiectpos.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PatientDTO {
    @Id
    private String cnp;
    private Integer idUser;
    private String nume;
    private String prenume;
    @Column(unique = true)
    private String email;
    private String telefon;
    private Date data_nasterii;
    private boolean is_active;
    @JsonIgnore
    @OneToMany(mappedBy = "patients")
    private Set<PhysiciansPatientsMapping> programare;
    public PatientDTO(String cnp, Integer id_user, String nume, String prenume, String email, String telefon, Date data_nasterii, boolean is_active) {
        this.cnp = cnp;
        this.idUser = id_user;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
        this.data_nasterii = data_nasterii;
        this.is_active = is_active;
    }
}
