import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Data, Router } from '@angular/router';
import { Ruolo } from 'src/app/types/ruolo';
import { RegistrazioneRequest } from 'src/app/dto/request/registrazioneRequest';
import { MessageResponse } from 'src/app/dto/response/messageResponse';

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrls: ['./registrazione.component.css']
})
export class RegistrazioneComponent implements OnInit {

  protected registrazioneForm!: FormGroup;

  protected password: string = '';
  protected ripeti_password: string = '';

  protected showPassword: boolean = false;
  protected showRepeatPassword: boolean = false;

  //costruttore dove istanzio le classi con cui interagire
  constructor(private authService: AuthService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.registrazioneForm = new FormGroup({
      nome: new FormControl('', [Validators.required]),
      cognome: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      ripeti_password: new FormControl('', [Validators.required]),
      dataNascita: new FormControl('', [Validators.required])
    });
  }  

   //per la registrazione
   register(): void {

    console.log("Primo Controllo");
    
    //Se il form non è valido lancio un messaggio di errore
    console.log(this.registrazioneForm.value);
    if (!this.checkForm(this.registrazioneForm)) {
      this.toastr.error("Compilare tutti i campi");
      return;
    }

    console.log("Fine Primo Controllo");

    //Assegno i valori del form a delle variabili
    const nome: string = this.registrazioneForm.controls['nome'].value;
    const cognome: string = this.registrazioneForm.controls['cognome'].value;
    const email: string = this.registrazioneForm.controls['email'].value;
    const username: string = this.registrazioneForm.controls['username'].value;
    const password: string = this.registrazioneForm.controls['password'].value;
    const passwordRipetuta: string = this.registrazioneForm.controls['ripeti_password'].value;
    const dataNascita: Date = this.registrazioneForm.controls['dataNascita'].value;

    //Creo il DTO per mandare la richiesta al server
    const request: RegistrazioneRequest = {
      nome,
      cognome,
      email,
      username,
      password,
      passwordRipetuta,
      dataNascita, // Inclusa la data di nascita
    };

    console.log(request);

    //Tramite il service mando la richiesta al server
    this.authService.registrazione(request).subscribe({
      next: (res: MessageResponse) => {
        //In caso di successo mando l'utente al login
        this.toastr.success(res.message);
        this.router.navigateByUrl('/login');
      },
      error: (err: any) => {
        //In caso di errore visualizzo un messaggio
        console.log(err);
        this.toastr.error(err.error.message);
      },
    });
  }


  //controlla se c'è almeno un campo vuoto, null o undefined e in quel caso torna false
  checkForm(form: FormGroup): boolean {
    let isOk: boolean = true;

    Object.keys(form.controls).forEach(key => {
      if (form.controls[key].value === '' || form.controls[key].value === null || form.controls[key].value === undefined) {
        console.log("Campo non valido:", key); // Aggiunto per debug
        isOk = false;
      }
    });

    return isOk;
  }


   //Sceglie quale icona visualizzare nel form
   toggleShowPassword(type: string): void {
    type === 'repeat' ? this.showRepeatPassword = !this.showRepeatPassword : this.showPassword = !this.showPassword;
  }

  //true se il campo è vuoto ed è stato toccato (si circonda di rosso per far capire che bisogna compilarlo)
  emptyAndTouched(campo: string): boolean {
    return (this.registrazioneForm.controls[campo].value === '' && this.registrazioneForm.controls[campo].touched);
  }

  /* true se password e ripeti password sono diversi e il campo è stato toccato 
  (si circonda di rosso per far capire che bisogna compilarlo e dev'essere uguale a password) */
  ripetiPasswordNotOk(): boolean {
    return (this.password !== this.ripeti_password && this.registrazioneForm.controls['ripeti_password'].touched);
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
