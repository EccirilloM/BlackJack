import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrazioneComponent } from './components/registrazione/registrazione.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TavoloComponent } from './components/tavolo/tavolo.component';
import { ConfirmChangeOperationComponent } from './components/confirm-change-operation/confirm-change-operation.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { FormsModule } from '@angular/forms';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { PersonalInfoComponent } from './components/profileMain/personal-info/personal-info.component';
import { ChangeProfileDataComponent } from './components/profileMain/change-profile-data/change-profile-data.component';
import { ProfileSideBarComponent } from './components/profileMain/profile-side-bar/profile-side-bar.component';
import { ProfileContainerComponent } from './components/profileMain/profile-container/profile-container.component';
import { ChargeMoneyComponent } from './components/charge-money/charge-money.component';
import { ForumComponent } from './components/forum/forum.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { EconomoDashboardComponent } from './components/economo-dashboard/economo-dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrazioneComponent,
    DashboardComponent,
    TavoloComponent,
    ConfirmChangeOperationComponent,
    NotFoundComponent,
    StatisticsComponent,
    NavbarComponent,
    HomepageComponent,
    RegistrazioneComponent,
    PersonalInfoComponent,
    ChangeProfileDataComponent,
    ProfileSideBarComponent,
    ProfileContainerComponent,
    ChargeMoneyComponent,
    ForumComponent,
    AdminDashboardComponent,
    EconomoDashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
