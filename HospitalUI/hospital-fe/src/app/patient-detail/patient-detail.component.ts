import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  pid: string;
  patient: Patient;
  constructor(private patientService: PatientService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.pid = this.route.snapshot.params['pid'];

    this.patient = new Patient();
    this.patientService.getPatientById(this.pid).subscribe(data => {
      this.patient = data;
    }, error => console.log(error));
  }

}
