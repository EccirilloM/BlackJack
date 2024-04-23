import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service'; // Aggiusta con il percorso corretto del tuo servizio
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-change-profile-data',
  templateUrl: './change-profile-data.component.html',
  styleUrls: ['./change-profile-data.component.css']
})
export class ChangeProfileDataComponent implements OnInit {
  // VARIABILI PER I DATI UTENTE -----------------------------------------------------------------------------------
  userData = {
    nome: localStorage.getItem('nome') || '',
    cognome: localStorage.getItem('cognome') || '',
    email: localStorage.getItem('email') || '',
    username: localStorage.getItem('username') || '',
    vecchiaPassword: '',
    nuovaPassword: '',
  };

  // COSTRUTTORE -----------------------------------------------------------------------------------
  constructor(private userService: UserService, private toastr: ToastrService, private router: Router) { }

  // NGONINIT -----------------------------------------------------------------------------------
  ngOnInit(): void {
    console.log("ChangeProfileDataComponent initialized");
  }

  // METODI PER L'AGGIORNAMENTO DATI UTENTE -----------------------------------------------------------------------------------
  aggiornaDatiUtente() {
    if (!this.userData.vecchiaPassword) {
      this.toastr.error("La vecchia password è necessaria.");
      return;
    }

    this.userService.aggiornaDatiUtente(this.userData.nome, this.userData.cognome, this.userData.email, this.userData.username, this.userData.vecchiaPassword, this.userData.nuovaPassword)
      .subscribe({
        next: (res) => {
          this.toastr.success("Dati modificati con successo");
          this.userData.vecchiaPassword = '';
          this.userData.nuovaPassword = '';
          this.router.navigateByUrl('/login').then(() => {
            this.toastr.info("Eseguire nuovamente il login");
          });
        },
        error: (err: HttpErrorResponse) => {
          this.toastr.error(err.error.message);
          this.userData.vecchiaPassword = '';
          this.userData.nuovaPassword = '';
        }
      });
  }
}

