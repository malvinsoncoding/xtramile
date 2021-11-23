import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  patients: Patient[];
  patient: Patient = new Patient();

  currentPage: number = 0;
  pageSize: number = 10;
  totalCount: number = 0;
  totalPage: number = 0;

  pageUI: number;


  constructor(private patientService: PatientService,
    private router: Router) { }

  ngOnInit(): void {
    this.getPatientsPaging(this.currentPage);
  }

  private getPatients() {
    this.patientService.getPatientList().subscribe(data => {
      this.patients = data;
    });
  }

  detailPatient(pid: string) {
    this.router.navigate(['patient-detail', pid]);
  }

  updatePatient(pid: string) {
    this.router.navigate(['patient-update', pid]);
  }

  deletePatient(pid: string) {
    this.patientService.deletePatient(pid).subscribe(data => {
      console.log(data);
      this.getPatients();
    })
  }

  getPatientsByIdOrName(idOrName: string, page: number) {
    this.patientService.getPatientByIdOrName(idOrName, page, this.pageSize).subscribe(data => {
      this.patients = data['dtoList'];
      console.log(data);
      this.totalCount = data['totalCount'];
      this.totalPage = Math.ceil(this.totalCount / this.pageSize);

      this.pageUI = page + 1;
      this.isNextDisabled();
      this.isPrevDisabled();
    })
  }

  search(idOrName: string) {
    this.getPatientsByIdOrName(idOrName, 0);
  }

  getPatientsPaging(page: number) {
    this.patientService.getPatientListPaging(page, this.pageSize).subscribe(data => {
      this.patients = data['dtoList'];
      console.log(data);
      this.totalCount = data['totalCount'];
      this.totalPage = Math.ceil(this.totalCount / this.pageSize);

      this.pageUI = page + 1;
      this.isNextDisabled();
      this.isPrevDisabled();
    })
  }

  goNext() {
    this.currentPage++;
    if (this.patient.idOrName !== "") {
      this.getPatientsByIdOrName(this.patient.idOrName, this.currentPage);
    } else {
      this.getPatientsPaging(this.currentPage);
    }

  }

  goPrevious() {
    this.currentPage--;
    if (this.patient.idOrName !== "") {
      this.getPatientsByIdOrName(this.patient.idOrName, this.currentPage);
    } else {
      this.getPatientsPaging(this.currentPage);
    }
  }

  isPrevDisabled(): boolean {
    if (this.currentPage < 1) {
      return true;
    } else {
      return false;
    }
  }

  isNextDisabled(): boolean {
    this.totalPage = this.totalPage < 1 ? this.totalPage : this.totalPage - 1;
    if (this.currentPage === this.totalPage) {
      return true;
    } else {
      return false;
    }
  }

}
