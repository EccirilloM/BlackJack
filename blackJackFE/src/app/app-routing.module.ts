import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrazioneComponent } from './components/registrazione/registrazione.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ConfirmChangeOperationComponent } from './components/confirm-change-operation/confirm-change-operation.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomepageComponent } from './components/homepage/homepage.component';

const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: 'registrazione', component: RegistrazioneComponent },
  {
    path: 'homepage', component: HomepageComponent, children: [
      { path: "profile/:id", component: ProfileComponent },
      { path: 'dashboard', component: DashboardComponent },
      {
        path: "confirmChangeOperation", component: ConfirmChangeOperationComponent
      }
    ]
  },

  //404
  { path: "404", component: NotFoundComponent },
  { path: "**", redirectTo: "/404" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
