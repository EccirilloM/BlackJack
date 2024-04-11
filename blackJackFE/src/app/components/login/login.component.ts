import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/dto/request/loginRequest';
import { LoginResponse } from 'src/app/dto/response/loginResponse';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  showPassword: boolean = false;

  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  login(): void {
    if (!this.username || !this.password) {
      this.toastr.error("Compilare tutti i campi");
      return;
    }

    const request: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.authService.login(request).subscribe({
      next: (res: LoginResponse) => {
        this.toastr.success('Login effettuato con successo!');
        // Qui puoi salvare i dettagli dell'utente come token nel localStorage o gestire la navigazione
        localStorage.setItem('id', res.userId.toString());
        localStorage.setItem('nome', res.nome);
        localStorage.setItem('cognome', res.cognome);
        localStorage.setItem('username', res.username);
        localStorage.setItem('email', res.email);
        localStorage.setItem('ruolo', res.ruolo);
        localStorage.setItem('saldo', res.saldo.toString());
        localStorage.setItem('dataNascita', res.dataNascita);
        localStorage.setItem('dataRegistrazione', res.dataRegistrazione);
        localStorage.setItem('token', `Bearer ${res.jwt}`);

        this.authService.setIsAuthenticated(true);

        // Assumi che l'oggetto di risposta abbia un campo jwt. Modifica per adattarsi alla tua risposta effettiva
        this.router.navigate(['/homepage']);
      },
      error: (error) => {
        this.toastr.error('Errore durante il login');
        console.error(error);
      }
    });
  }

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }

  goToRegistration(): void {
    this.router.navigate(['/registrazione']);
  }

  forgotPassword(): void {
    // Implementa la logica per il recupero della password qui
    console.log('Funzionalità di recupero password non implementata.');
  }
}
