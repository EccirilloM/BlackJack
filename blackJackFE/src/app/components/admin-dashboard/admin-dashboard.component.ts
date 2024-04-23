import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { GetUserDataResponse } from 'src/app/dto/response/getUserDataResponse';
import { HttpErrorResponse } from '@angular/common/http';
import { RegistrazioneRequest } from 'src/app/dto/request/registrazioneRequest';
import { AuthService } from 'src/app/services/auth.service';
import { MessageResponse } from 'src/app/dto/response/messageResponse';
import { MapService } from 'src/app/services/map.service';
import { debounceTime } from 'rxjs';
import { TabacchiService } from 'src/app/services/tabacchi.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit, AfterViewInit {
  // VARIABILI PER I GRAFICI ----------------------------------------------------------------------------
  @ViewChild('usersChart') usersChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('commercesChart') commercesChartRef!: ElementRef<HTMLCanvasElement>;
  // VARIABILI DATI ----------------------------------------------------------------------------
  numberOfUsers: number = 0;
  utenti: GetUserDataResponse[] = [];
  public searchResults: any[] = [];
  private mapCreaTabacchi: any;
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
  saldoString: string = localStorage.getItem('saldo') || '0';
  saldo: number = parseFloat(this.saldoString);
  economi: GetUserDataResponse[] = [];
  economoSelezionatoId: number = 0;
  // VARIABILI PER Creare Tabacchi ----------------------------------------------------------------------------
  nomeTabacchi: string = '';
  // lat: number = this.mapService.lat;
  // lng: number = this.mapService.lng;
  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private userService: UserService, private toastr: ToastrService, private authService: AuthService, private mapService: MapService, private tabacchiService: TabacchiService) {
    Chart.register(...registerables);
  }

  //NGONINIT E AFTERVIEWINIT ---------------------------------------------------------------------------- 
  ngOnInit(): void {
    console.log('Admin Dashboard initialized');
    this.loadUsers();
    this.loadAllEconomi();
  }

  ngAfterViewInit(): void {
    this.mapCreaTabacchi = this.mapService.initMapCreaTabacchi(this.mapCreaTabacchi);
    this.initializeCharts();
  }

  latMarker(): number {
    return this.mapService.lat;
  }

  lngMarker(): number {
    return this.mapService.lng;
  }

  //NOMINATIM SECTION --------------------------------------------------------------
  searchNominatim(query: string) {
    if (query.length < 3) {
      this.searchResults = [];
      return;
    }

    //LASCIA COSì il debounce Fidati
    this.mapService.searchNominatimLocation(query).pipe(debounceTime(5000)).subscribe({
      next: (results) => {
        this.searchResults = results;
      },
      error: (error) => {
        console.error('Errore nella ricerca:', error);
        this.searchResults = [];
      }
    });
  }

  centerMapOnResult(result: any): void {
    const lat = parseFloat(result.lat);
    const lon = parseFloat(result.lon);
    this.mapCreaTabacchi.flyTo([lat, lon], 15);
  }


  // FUNZIONI PER CREARE ECONOMO ----------------------------------------------------------------------------
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

  // FUNZIONI PER CREARE TABACCHI ----------------------------------------------------------------------------
  creaTabacchi(): void {
    this.tabacchiService.creaTabacchi(this.nomeTabacchi, this.latMarker(), this.lngMarker(), this.economoSelezionatoId).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.nomeTabacchi = '';
      },
      error: (err: any) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la creazione del tabacchi');
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

  // Funzione per caricare tutti gli Economo ----------------------------------------------------------------------------
  loadAllEconomi(): void {
    this.userService.getAllByRuolo('ECONOMO').subscribe({
      next: (response: GetUserDataResponse[]) => {
        this.economi = response;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching users: ', error);
        this.toastr.error('Error while fetching users');
      }
    });

  }

  // FUNZIONE PER ELIMINARE UN UTENTE ----------------------------------------------------------------------------
  deleteUser(id: number): void {
    console.log('Eliminazione utente con id: ', id);
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
