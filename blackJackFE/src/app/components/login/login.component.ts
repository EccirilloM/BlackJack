import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/dto/request/loginRequest';

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
  ) {}

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
      next: (res) => {
        this.toastr.success('Login effettuato con successo!');
        // Qui puoi salvare i dettagli dell'utente come token nel localStorage o gestire la navigazione
        localStorage.setItem('token', `Bearer ${res.jwt}`);
        // Assumi che l'oggetto di risposta abbia un campo jwt. Modifica per adattarsi alla tua risposta effettiva
        this.router.navigate(['/dashboard']);
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
