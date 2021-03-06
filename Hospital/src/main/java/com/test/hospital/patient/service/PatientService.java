package com.test.hospital.patient.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.test.hospital.patient.entity.Patient;
import com.test.hospital.patient.repository.PatientRepository;
import com.test.hospital.patient.rest.PatientDTO;
import com.test.hospital.utils.Page;
import com.test.hospital.utils.ResponseStatus;
import com.test.hospital.utils.Static;

@Service
@Transactional
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	private ResponseStatus responseStatus = new ResponseStatus();

	public Page<PatientDTO> getPatientsPaging(int page, int size) {
		Page<PatientDTO> dtoPage = new Page<PatientDTO>();
		List<PatientDTO> dtos = new ArrayList();
		List<Patient> patients = patientRepository.queryFindAll(PageRequest.of(page, size)).getContent();
		for (Patient patient : patients) {
			PatientDTO dto = new PatientDTO();
			BeanUtils.copyProperties(patient, dto);
			String[] name = patient.getFullName().split(" ");
			dto.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()));
			dto.setFirstName(name[0]);
			dto.setLastName(name[1]);
			dtos.add(dto);
		}
		dtoPage.setDtoList(dtos);
		dtoPage.setTotalCount(patientRepository.count());
		return dtoPage;
	}
	
	public Page<PatientDTO> getSearchPaging(String idOrName, int page, int size) {
		Page<PatientDTO> dtoPage = new Page<PatientDTO>();
		List<PatientDTO> dtos = new ArrayList<PatientDTO>();
		List<Patient> patients = new ArrayList<Patient>();
		if (idOrName.contains("-") && idOrName.length() == 36) {
			patients.add(patientRepository.getById(idOrName));
			dtoPage.setTotalCount(patients.size());
		} else {
			patients = patientRepository.searchPatientsByNamePaging(idOrName, PageRequest.of(page, size)).getContent();
			dtoPage.setTotalCount(patientRepository.countSearchPatient(idOrName));
		}
		for (Patient patient : patients) {
			PatientDTO dto = new PatientDTO();
			BeanUtils.copyProperties(patient, dto);
			String[] name = patient.getFullName().split(" ");
			dto.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()));
			dto.setFirstName(name[0]);
			dto.setLastName(name[1]);
			dtos.add(dto);
		}
		dtoPage.setDtoList(dtos);
		return dtoPage;
	}

	/**
	 * @deprecated
	 * @param idOrName
	 * @return
	 */
	public List<PatientDTO> getPatients(String idOrName) {
		List<PatientDTO> dtos = new ArrayList();
		List<Patient> patients = new ArrayList();
		if (StringUtils.isEmpty(idOrName)) {
			patients = patientRepository.findAll();
		} else {
			if (idOrName.contains("-") && idOrName.length() == 36) {
				patients.add(patientRepository.getById(idOrName));
			} else {
				patients = patientRepository.searchPatientsByName(idOrName);
			}
		}
		for (Patient patient : patients) {
			PatientDTO dto = new PatientDTO();
			BeanUtils.copyProperties(patient, dto);
			String[] name = patient.getFullName().split(" ");
			dto.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()));
			dto.setFirstName(name[0]);
			dto.setLastName(name[1]);
			dtos.add(dto);
		}
		return dtos;
	}

	public ResponseStatus createPatientData(PatientDTO dto) {
		try {
			Patient patient = new Patient();
			BeanUtils.copyProperties(dto, patient);
			patient.setFullName(dto.getFirstName().concat(" ").concat(dto.getLastName()));
			patient.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDateOfBirth()));
			patient.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			patientRepository.save(patient);
			responseStatus.setMessage(Static.SUCCESS_CREATE);
			responseStatus.setStatus(Static.SUCCESS);
		} catch (ParseException e) {
			e.printStackTrace();
			responseStatus.setMessage(Static.FAILED_CREATE);
			responseStatus.setStatus(Static.ERROR);
		}
		return responseStatus;
	}

	public ResponseStatus updatePatientData(PatientDTO dto) {
		try {
			Patient patient = patientRepository.getById(dto.getPid());
			BeanUtils.copyProperties(dto, patient);
			patient.setFullName(dto.getFirstName().concat(" ").concat(dto.getLastName()));
			patient.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDateOfBirth()));
			patient.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
			responseStatus.setMessage(Static.SUCCESS_UPDATE);
			responseStatus.setStatus(Static.SUCCESS);
		} catch (ParseException e) {
			e.printStackTrace();
			responseStatus.setMessage(Static.FAILED_CREATE);
			responseStatus.setStatus(Static.ERROR);
		}
		return responseStatus;
	}

	public ResponseStatus deletePatientData(String pid) {
		patientRepository.deleteById(pid);
		responseStatus.setMessage(Static.SUCCESS_DELETE);
		responseStatus.setStatus(Static.SUCCESS);
		return responseStatus;
	}
}
