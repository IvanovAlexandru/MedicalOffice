package com.example.proiectposmongo.repositories;

import com.example.proiectposmongo.dto.ConsultatieDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConsultatieRepository extends MongoRepository<ConsultatieDTO,String> {
    List<ConsultatieDTO> findAllByPacientAndDoctor(Integer pacient,Integer doctor);
}
