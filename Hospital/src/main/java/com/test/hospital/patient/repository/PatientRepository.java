package com.test.hospital.patient.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.hospital.patient.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

	@Query("select p from Patient p where (p.fullName =:name)")
	List<Patient> searchPatientsByName(@Param("name") String name);

	@Query("select p from Patient p where (p.fullName =:name)")
	Page<Patient> searchPatientsByNamePaging(@Param("name") String name, Pageable page);
	
	@Query("select count(p) from Patient p where (p.fullName =:name)")
	long countSearchPatient(@Param("name") String name);

	@Query("select p from Patient p order by p.createdDate desc")
	Page<Patient> queryFindAll(Pageable page);
}
