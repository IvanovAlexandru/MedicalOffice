package com.example.proiectpos.repositories;

import com.example.proiectpos.dto.PhysicianDTO;
import com.example.proiectpos.utils.SpecializationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicianRepository extends JpaRepository<PhysicianDTO,Integer> {
    List<PhysicianDTO> findBySpecialization(SpecializationEnum specialization);
    List<PhysicianDTO> findByNumeContainsIgnoreCase(String nume);
}
