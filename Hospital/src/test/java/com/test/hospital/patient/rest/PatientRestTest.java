package com.test.hospital.patient.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.hospital.patient.service.PatientService;
import com.test.hospital.utils.Page;
import com.test.hospital.utils.ResponseStatus;
import com.test.hospital.utils.Static;

public class PatientRestTest {

	@InjectMocks
	PatientRest patientRest;

	@Mock
	PatientService patientService;
	
	List<PatientDTO> dtos;
	PatientDTO dto;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		dtos = new ArrayList();
		dto = new PatientDTO();
		
		dto.setPid(UUID.randomUUID().toString());
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setDateOfBirth("2021-11-23");
		dto.setGender("Male");
		dto.setAddress("This Address");
		dto.setSuburb("This Suburb");
		dto.setState("This State");
		dto.setPostcode("123456");
		dto.setPhoneNo("+12345678910");
		dto.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		
		dtos.add(dto);
		
	}
	
	@Test
	final void testGetPatients() {
		when(patientService.getPatients(null)).thenReturn(dtos).thenReturn(dtos);
		List<PatientDTO> dtoList = patientRest.getPatients();
		assertNotNull(dtoList);
	}
	
	@Test
	final void testGetPatientsPaging() {
		int page = 0;
		int size = 10;
		Page<PatientDTO> patientPage = new Page<PatientDTO>();
		patientPage.setDtoList(dtos);
		when(patientService.getPatientsPaging(page, size)).thenReturn(patientPage);
		Page<PatientDTO> dtoPage = patientRest.getPatientsPaging(page, size);
		assertNotNull(dtoPage);
	}
	
	@Test
	final void testCreatePatientData() {
		ResponseStatus status = new ResponseStatus();
		status.setStatus(Static.SUCCESS);
		status.setMessage(Static.SUCCESS_CREATE);
		when(patientService.createPatientData(dto)).thenReturn(status);
		ResponseStatus response = patientRest.createPatientData(dto);
		assertEquals(status.getStatus(), response.getStatus());
		assertEquals(status.getMessage(), response.getMessage());
	}
	
	@Test
	final void testUpdatePatientData() {
		ResponseStatus status = new ResponseStatus();
		status.setStatus(Static.SUCCESS);
		status.setMessage(Static.SUCCESS_UPDATE);
		when(patientService.updatePatientData(dto)).thenReturn(status);
		ResponseStatus response = patientRest.updatePatientData(dto.getPid(), dto);
		assertEquals(status.getStatus(), response.getStatus());
		assertEquals(status.getMessage(), response.getMessage());
	}
	
	@Test
	final void testDeletePatientData() {
		ResponseStatus status = new ResponseStatus();
		status.setStatus(Static.SUCCESS);
		status.setMessage(Static.SUCCESS_DELETE);
		when(patientService.deletePatientData(dto.getPid())).thenReturn(status);
		ResponseStatus response = patientRest.deletePatientData(dto.getPid());
		assertEquals(status.getStatus(), response.getStatus());
		assertEquals(status.getMessage(), response.getMessage());
	}
}
