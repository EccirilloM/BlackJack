import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegistrazioneRequest } from 'src/app/dto/request/RegistrazioneRequest';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  // VARIABILI PER LA REGISTRAZIONE -----------------------------------------------------------------------------------
  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = ''; // Assicurati di gestire correttamente la formattazione della data per il backend
  showPassword = false;
  showRepeatPassword = false;

  // COSTRUTTORE -----------------------------------------------------------------------------------
  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  // METODO PER LA REGISTRAZIONE -----------------------------------------------------------------------------------
  register(): void {
    // Validazione semplice. Potresti voler aggiungere validazioni più specifiche
    if (!this.nome || !this.cognome || !this.email || !this.username || !this.password || !this.passwordRipetuta || !this.dataNascita) {
      this.toastr.error("Compilare tutti i campi");
      return;
    }

    if (this.password !== this.passwordRipetuta) {
      this.toastr.error("Le password non coincidono");
      return;
    }

    const request: RegistrazioneRequest = {
      nome: this.nome,
      cognome: this.cognome,
      email: this.email,
      username: this.username,
      password: this.password,
      dataNascita: new Date(this.dataNascita)
    };

    this.authService.registrazione(request).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.router.navigateByUrl('/login');
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la registration');
      }
    });
  }

  // METODI PER MOSTRARE LE PASSWORD -----------------------------------------------------------------------------------
  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }

  toggleShowRepeatPassword(): void {
    this.showRepeatPassword = !this.showRepeatPassword;
  }

  // METODI PER LA NAVIGAZIONE IN ALTRE PAGINE ----------------------------------------------------------------------------
  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}

