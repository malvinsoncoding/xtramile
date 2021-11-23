package com.test.hospital.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.test.hospital.patient.entity.Patient;
import com.test.hospital.patient.repository.PatientRepository;
import com.test.hospital.patient.rest.PatientDTO;

public class PatientServiceTest {

	@Mock
	PatientRepository patientRepository;

	@InjectMocks
	PatientService patientService;

	Patient patient;
	List<Patient> patients;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		patient = new Patient();
		patients = new ArrayList();
		
		patient.setPid("1def2d9d-cdd6-4cdb-9816-4e8eeb014369");
		patient.setFullName("John Doe");
		patient.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-10"));
		patient.setGender("Male");
		patient.setAddress("This Address");
		patient.setSuburb("This suburb");
		patient.setState("This State");
		patient.setPostcode("123456");
		patient.setPhoneNo("+12345678910");
		patient.setCreatedDate(new Timestamp(System.currentTimeMillis()));

		patients.add(patient);
	}

	@Test
	final void testGetPatientsPaging() {
		int page = 0;
		int size = 10;
		Page<Patient> pagedResponse = new PageImpl<Patient>(patients);

		when(patientRepository.queryFindAll(PageRequest.of(page, size))).thenReturn(pagedResponse);
		
		long totalCountData = patients.size();
		
		when(patientRepository.count()).thenReturn(totalCountData);
		
		com.test.hospital.utils.Page<PatientDTO> dtoPage = patientService.getPatientsPaging(page, size);
		
		assertNotNull(dtoPage.getDtoList());
		assertEquals(patients.size(), dtoPage.getDtoList().size());
		
	}
	
	@Test
	final void testGetSearchNamePaging() {
		int page = 0;
		int size = 10;
		String idOrName = "John Doe";
		
		Page<Patient> pagedResponse = new PageImpl<Patient>(patients);
		
		when(patientRepository.searchPatientsByNamePaging(idOrName, PageRequest.of(page, size))).thenReturn(pagedResponse);
		
		com.test.hospital.utils.Page<PatientDTO> dtoPage = patientService.getSearchPaging(idOrName, page, size);
		
		String fullName = dtoPage.getDtoList().get(0).getFirstName()+" "+dtoPage.getDtoList().get(0).getLastName();
		
		assertNotNull(dtoPage.getDtoList());
		assertEquals(idOrName, fullName);
	}
	
	@Test
	final void testGetPatientById() {
		String id = patient.getPid();
		
		when(patientRepository.getById(id)).thenReturn(patient);
		
		PatientDTO returnData = patientService.getPatients(id).get(0);
		
		assertNotNull(returnData.getPid());
		assertNotNull(returnData.getFirstName());
		assertNotNull(returnData.getLastName());
		assertNotNull(returnData.getDateOfBirth());
		assertNotNull(returnData.getGender());
		assertNotNull(returnData.getAddress());
		assertNotNull(returnData.getSuburb());
		assertNotNull(returnData.getState());
		assertNotNull(returnData.getPostcode());
		assertNotNull(returnData.getPhoneNo());
		assertNotNull(returnData.getCreatedDate());
	}
}
