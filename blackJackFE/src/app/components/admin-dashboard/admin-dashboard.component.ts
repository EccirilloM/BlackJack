import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { GetUserDataResponse } from 'src/app/dto/response/getUserDataResponse';
import { HttpErrorResponse } from '@angular/common/http';
import { RegistrazioneRequest } from 'src/app/dto/request/registrazioneRequest';
import { AuthService } from 'src/app/services/auth.service';
import { MessageResponse } from 'src/app/dto/response/messageResponse';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  // VARIABILI PER I GRAFICI ----------------------------------------------------------------------------
  @ViewChild('usersChart') usersChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('commercesChart') commercesChartRef!: ElementRef<HTMLCanvasElement>;
  // VARIABILI DATI ----------------------------------------------------------------------------
  income: number = 5000;
  numberOfUsers: number = 0;
  utenti: GetUserDataResponse[] = [];
  // VARIABILI PER Creare Economo ----------------------------------------------------------------------------
  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = new Date(); // Assicurati di gestire correttamente la formattazione della data per il backend
  showPassword = false;
  showRepeatPassword = false;
  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private userService: UserService, private toastr: ToastrService, private authService: AuthService) {
    Chart.register(...registerables);
  }


  //NGONINIT E AFTERVIEWINIT ---------------------------------------------------------------------------- 
  ngOnInit(): void {
    console.log('Admin Dashboard initialized');
    this.loadUsers();
  }

  ngAfterViewInit(): void {
    this.initializeCharts();
  }

  creaEconomo(): void {
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

    this.userService.creaEconomo(request).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.nome = '';
        this.cognome = '';
        this.email = '';
        this.username = '';
        this.password = '';
        this.passwordRipetuta = '';
        this.dataNascita = new Date();
      },
      error: (err: any) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la registrazione');
      }
    });
  }

  // FUNZIONE PER CARICARE GLI UTENTI ----------------------------------------------------------------------------

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (response: GetUserDataResponse[]) => {
        console.log(response);
        this.utenti = response;
        this.numberOfUsers = this.utenti.length;  // Aggiorna qui il numero di utenti
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching users: ', error);
        this.toastr.error('Error while fetching users');
      }
    });
  }

  // FUNZIONE PER INIZIALIZZARE I GRAFICI ----------------------------------------------------------------------------

  private initializeCharts(): void {
    this.initializeUsersChart();
    this.initializeCommercesChart();
  }

  private initializeUsersChart(): void {
    const ctx = this.usersChartRef.nativeElement.getContext('2d');
    if (ctx) {
      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Nuevos', 'Registrados'],
          datasets: [{
            data: [30, 65],
            backgroundColor: ['#00F0FF', '#8B8B8D'],
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'bottom'
            }
          }
        }
      });
    }
  }

  private initializeCommercesChart(): void {
    const ctx = this.commercesChartRef.nativeElement.getContext('2d');
    if (ctx) {
      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Nuevos', 'Registrados'],
          datasets: [{
            data: [60, 40],
            backgroundColor: ['#FEC500', '#8B8B8D'],
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'bottom'
            }
          }
        }
      });
    }
  }

  // FINE COMPONETE ----------------------------------------------------------------------------
}
