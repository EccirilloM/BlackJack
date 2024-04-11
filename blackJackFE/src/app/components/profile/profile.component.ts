import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = ''; // Assicurati di gestire correttamente la formattazione della data per il backend
  showPassword = false;
  showRepeatPassword = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
  }
}
