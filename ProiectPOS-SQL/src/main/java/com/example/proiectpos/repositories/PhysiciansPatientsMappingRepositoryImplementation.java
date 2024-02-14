package com.example.proiectpos.repositories;

import com.example.proiectpos.dto.PatientDTO;
import com.example.proiectpos.dto.PhysicianDTO;
import com.example.proiectpos.dto.PhysiciansPatientsMapping;
import com.example.proiectpos.utils.StatusEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class PhysiciansPatientsMappingRepositoryImplementation{
    @PersistenceContext
    private EntityManager entityManager;
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;

    public PhysiciansPatientsMappingRepositoryImplementation(PhysicianRepository physicianRepository, PatientRepository patientRepository) {
        this.physicianRepository = physicianRepository;
        this.patientRepository = patientRepository;
    }

    public List<PhysiciansPatientsMapping> getProgramareByDateType(String patient_id,Integer date,String type){

        List<PhysiciansPatientsMapping> result = null;

        if(type.equals("day")){

            String jpql = "SELECT ppm from PhysiciansPatientsMapping ppm " +
                    "WHERE ppm.patients.cnp = :patientId AND DAY(ppm.date) = :appointmentDate";
            Query query = entityManager.createQuery(jpql, PhysiciansPatientsMapping.class);
            query.setParameter("patientId", patient_id);
            query.setParameter("appointmentDate", date);

            result = query.getResultList();
        } else if (type.equals("month")) {
            String jpql = "SELECT ppm FROM PhysiciansPatientsMapping ppm " +
                    "WHERE ppm.patients.cnp = :patientId " +
                    "AND MONTH(ppm.date)= :month";

            Query query = entityManager.createQuery(jpql, PhysiciansPatientsMapping.class);
            query.setParameter("patientId", patient_id);
            query.setParameter("month", date);

            result = query.getResultList();
        }

        return result;
    }

    @Transactional
    public void deletePatientAppointmentById(String cnp,Integer id) {
        PhysicianDTO physicianDTO = physicianRepository.findById(id).orElse(null);
        PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);

        if (physicianDTO != null && patientDTO != null) {
            String jpql = "DELETE FROM PhysiciansPatientsMapping ppm " +
                    "WHERE ppm.patients = :patient AND ppm.physicians = :physician";

            Query query = entityManager.createQuery(jpql);
            query.setParameter("patient", patientDTO);
            query.setParameter("physician", physicianDTO);

            query.executeUpdate();
        }
    }
    @Transactional
    public PhysiciansPatientsMapping findAppointmentByIdAndCnp(String cnp,Integer id){
        PhysicianDTO physicianDTO = physicianRepository.findById(id).orElse(null);
        PatientDTO patientDTO = patientRepository.findById(cnp).orElse(null);

        if (physicianDTO != null && patientDTO != null) {
            String jpql = "SELECT ppm FROM PhysiciansPatientsMapping ppm " +
                    "WHERE ppm.patients = :patient AND ppm.physicians = :physician";

            TypedQuery<PhysiciansPatientsMapping> query = entityManager.createQuery(jpql, PhysiciansPatientsMapping.class);
            query.setParameter("patient", patientDTO);
            query.setParameter("physician", physicianDTO);

            return query.getSingleResult();
        }

        return null;
    }


}
