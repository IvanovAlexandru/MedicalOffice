package com.example.proiectpos.repositories;

import com.example.proiectpos.dto.PatientDTO;
import com.example.proiectpos.dto.PhysicianDTO;
import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PhysicianPatientMappingRepository extends JpaRepository<PhysiciansPatientsMapping,Integer> {
    List<PhysiciansPatientsMapping> findAllByPatients(PatientDTO patientDTO);
    List<PhysiciansPatientsMapping> findAllByPhysicians(PhysicianDTO physicianDTO);
    List<PhysiciansPatientsMapping> findAllByPatientsAndDateBefore(PatientDTO patientDTO,Date date);
}
