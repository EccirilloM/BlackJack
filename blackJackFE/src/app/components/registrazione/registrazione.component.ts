import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegistrazioneRequest } from 'src/app/dto/request/registrazioneRequest';
import { MessageResponse } from 'src/app/dto/response/messageResponse';

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrls: ['./registrazione.component.css']
})
export class RegistrazioneComponent {

  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = ''; // Assicurati di gestire correttamente la formattazione della data per il backend
  showPassword = false;
  showRepeatPassword = false;

  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) { }

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
      passwordRipetuta: this.passwordRipetuta,
      dataNascita: new Date(this.dataNascita)
    };

    this.authService.registrazione(request).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.router.navigateByUrl('/login');
      },
      error: (err: any) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la registrazione');
      }
    });
  }

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }

  toggleShowRepeatPassword(): void {
    this.showRepeatPassword = !this.showRepeatPassword;
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}

