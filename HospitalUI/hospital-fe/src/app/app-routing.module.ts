import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientCreateComponent } from './patient-create/patient-create.component';
import { PatientDetailComponent } from './patient-detail/patient-detail.component';
import { PatientListComponent } from './patient-list/patient-list.component';
import { PatientUpdateComponent } from './patient-update/patient-update.component';

const routes: Routes = [
  {path: 'patients', component: PatientListComponent},
  {path: 'patient-create', component: PatientCreateComponent},
  {path: '', redirectTo: 'patients', pathMatch: 'full'},
  {path: 'patient-update/:pid', component: PatientUpdateComponent},
  {path: 'patient-detail/:pid', component: PatientDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
