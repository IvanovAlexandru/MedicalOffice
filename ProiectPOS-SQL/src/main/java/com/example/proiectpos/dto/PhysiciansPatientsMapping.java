package com.example.proiectpos.dto;

import com.example.proiectpos.utils.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PhysiciansPatientsMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private PhysicianDTO physicians;

    @ManyToOne
    @JoinColumn(name = "cnp")
    private PatientDTO patients;

    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public PhysiciansPatientsMapping(PhysicianDTO physician, PatientDTO patient, Date date, StatusEnum status) {
        this.physicians = physician;
        this.patients = patient;
        this.date = date;
        this.status = status;
    }
}
