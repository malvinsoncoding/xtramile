import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-create',
  templateUrl: './patient-create.component.html',
  styleUrls: ['./patient-create.component.css']
})
export class PatientCreateComponent implements OnInit {

  patient: Patient = new Patient();
  constructor(private patientService: PatientService,
    private router: Router) { }

  ngOnInit(): void {
  }

  savePatient() {
    this.patientService.createPatient(this.patient).subscribe( data =>{
      console.log(data);
      this.goToPatientList();
    },
    error => console.log(error));
  }

  goToPatientList(){
    this.router.navigate(['/patients']);
  }

  onSubmit(){
    console.log(this.patient);
    this.savePatient();
  }

}
