import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrazioneComponent } from './components/registrazione/registrazione.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ConfirmChangeOperationComponent } from './components/confirm-change-operation/confirm-change-operation.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { PersonalInfoComponent } from './components/profileMain/personal-info/personal-info.component';
import { ProfileSideBarComponent } from './components/profileMain/profile-side-bar/profile-side-bar.component';
import { ProfileContainerComponent } from './components/profileMain/profile-container/profile-container.component';

const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: 'registrazione', component: RegistrazioneComponent },
  {
    path: 'homepage', component: HomepageComponent, children: [
      {
        path: "dashboard", component: DashboardComponent
      },
      {
        path: "profile/:id", component: ProfileContainerComponent, children: [
          {
            path: "personalInfo", component: PersonalInfoComponent
          },
          {
            path: "changeProfileData", component: ConfirmChangeOperationComponent
          }
        ]
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
