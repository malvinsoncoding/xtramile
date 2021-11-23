import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-update',
  templateUrl: './patient-update.component.html',
  styleUrls: ['./patient-update.component.css']
})
export class PatientUpdateComponent implements OnInit {

  pid: string;
  patient: Patient = new Patient();
  constructor(private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.pid = this.route.snapshot.params['pid'];
    this.patientService.getPatientById(this.pid).subscribe(data => {
      this.patient = data;
    }, error => console.log(error));
  }

  onSubmit(){
    this.patientService.updatePatient(this.pid, this.patient).subscribe(data =>{
      this.goToPatientList();
    }, error => console.log(error));
  }

  goToPatientList(){
    this.router.navigate(['/patients']);
  }

}
