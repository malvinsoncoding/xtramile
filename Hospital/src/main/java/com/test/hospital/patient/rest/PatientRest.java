package com.test.hospital.patient.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.hospital.patient.service.PatientService;
import com.test.hospital.utils.Page;
import com.test.hospital.utils.ResponseStatus;

@RestController
@RequestMapping("/api/v1/")
public class PatientRest {

	@Autowired
	PatientService patientService;

	/**
	 * get patient paging
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/patients/{page}/{size}")
	public Page<PatientDTO> getPatientsPaging(@PathVariable int page, @PathVariable int size) {
		return patientService.getPatientsPaging(page, size);
	}

	@GetMapping("/patients")
	public List<PatientDTO> getPatients() {
		return patientService.getPatients(null);
	}

	/**
	 * create Patient Data
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/patients")
	public ResponseStatus createPatientData(@RequestBody PatientDTO dto) {
		return patientService.createPatientData(dto);
	}

	/**
	 * get patient data by id
	 * 
	 * @param pid
	 * @return
	 */
	@GetMapping("/patients/{pid}")
	public PatientDTO getPatientById(@PathVariable String pid) {
		return patientService.getPatients(pid).get(0);
	}

	/**
	 * get patient list by id or name
	 * 
	 * @param idOrName
	 * @return
	 */
	@GetMapping("/patients/search/{idOrName}/{page}/{size}")
	public Page<PatientDTO> getPatientByIdOrName(@PathVariable String idOrName, @PathVariable int page,
			@PathVariable int size) {
		return patientService.getSearchPaging(idOrName, page, size);
	}

	/**
	 * update patient data
	 * 
	 * @param dto
	 * @return
	 */
	@PutMapping("/patients/{pid}")
	public ResponseStatus updatePatientData(@PathVariable String pid, @RequestBody PatientDTO dto) {
		dto.setPid(pid);
		return patientService.updatePatientData(dto);
	}

	@DeleteMapping("/patients/{pid}")
	public ResponseStatus deletePatientData(@PathVariable String pid) {
		return patientService.deletePatientData(pid);
	}
}
