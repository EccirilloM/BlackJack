import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrazioneComponent } from './components/registrazione/registrazione.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { PersonalInfoComponent } from './components/profileMain/personal-info/personal-info.component';
import { ProfileContainerComponent } from './components/profileMain/profile-container/profile-container.component';
import { ChangeProfileDataComponent } from './components/profileMain/change-profile-data/change-profile-data.component';
import { ChargeMoneyComponent } from './components/charge-money/charge-money.component';
import { ForumComponent } from './components/forum/forum.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { EconomoDashboardComponent } from './components/economo-dashboard/economo-dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { EconomoGuard } from './guards/economo.guard';

const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: 'registrazione', component: RegistrazioneComponent },
  {
    path: 'homepage',
    component: HomepageComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: "dashboard", component: DashboardComponent
      },
      {
        path: "chargeMoney", component: ChargeMoneyComponent
      },
      {
        path: "forum/:tipoTavolo", component: ForumComponent
      },
      {
        path: "profile/:id", component: ProfileContainerComponent, children: [
          {
            path: "personalInfo", component: PersonalInfoComponent
          },
          {
            path: "changeProfileData", component: ChangeProfileDataComponent
          }
        ]
      },
      {
        path: "adminDashboard", component: AdminDashboardComponent, canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: "economoDashboard", component: EconomoDashboardComponent, canActivate: [AuthGuard, EconomoGuard]
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
