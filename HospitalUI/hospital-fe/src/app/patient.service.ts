import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from './patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseURL = "http://localhost:8080/api/v1/patients";
  constructor(private httpClient: HttpClient) { }

  getPatientList(): Observable<Patient[]>{
    return this.httpClient.get<Patient[]>(`${this.baseURL}`);
  }

  getPatientListPaging(page:number, size:number): Observable<Patient[]>{
    return this.httpClient.get<any>(`${this.baseURL}/${page}/${size}`);
  }

  createPatient(patient: Patient): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`, patient);
  }

  getPatientById(pid: string): Observable<Patient> {
    return this.httpClient.get<Patient>(`${this.baseURL}/${pid}`);
  }

  getPatientByIdOrName(idOrName: string, page: number, size: number): Observable<Patient[]>{
    return this.httpClient.get<Patient[]>(`${this.baseURL}/search/${idOrName}/${page}/${size}`);
  }

  updatePatient(pid: string, patient: Patient): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${pid}`, patient);
  }

  deletePatient(pid: string): Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/${pid}`);
  }

}
