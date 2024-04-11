import { Component } from '@angular/core';

@Component({
  selector: 'app-change-profile-data',
  templateUrl: './change-profile-data.component.html',
  styleUrls: ['./change-profile-data.component.css']
})
export class ChangeProfileDataComponent {
  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = ''; // Assicurati di gestire correttamente la formattazione della data per il backend
  showPassword = false;
  showRepeatPassword = false;

}
