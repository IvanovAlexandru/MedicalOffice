package com.example.proiectpos.repositories;

import com.example.proiectpos.dto.PatientDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientDTO,String> {
    PatientDTO findByIdUser(Integer idUser);
}
