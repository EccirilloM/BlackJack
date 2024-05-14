import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { GetUserDataResponse } from 'src/app/dto/response/GetUserDataResponse';
import { HttpErrorResponse } from '@angular/common/http';
import { RegistrazioneRequest } from 'src/app/dto/request/RegistrazioneRequest';
import { AuthService } from 'src/app/services/auth.service';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { MapService } from 'src/app/services/map.service';
import { debounceTime } from 'rxjs';
import { TabacchiService } from 'src/app/services/tabacchi.service';
import { GetAllTabacchiResponse } from 'src/app/dto/response/GetAllTabacchiResponse';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit, AfterViewInit {
  // VARIABILI PER I GRAFICI ----------------------------------------------------------------------------
  @ViewChild('usersChart') usersChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('commercesChart') commercesChartRef!: ElementRef<HTMLCanvasElement>;
  // VARIABILI DATI ADMIN ----------------------------------------------------------------------------
  numberOfUsers: number = 0;
  utenti: GetUserDataResponse[] = [];
  public searchResults: any[] = [];
  private mapCreaTabacchi: any;
  saldoString: string = localStorage.getItem('saldo') || '0';
  saldo: number = parseFloat(this.saldoString);
  // VARIABILI PER Creare Economo Oppure Modificare Dati di un User----------------------------------------------------------------------------
  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = new Date();
  showPassword = false;
  showRepeatPassword = false;
  economi: GetUserDataResponse[] = [];
  economoSelezionatoId: number = 0;

  // VARIABILI PER Tabacchi ----------------------------------------------------------------------------
  tabacchi: GetAllTabacchiResponse[] = [];
  // VARIABILI PER Creare Tabacchi ----------------------------------------------------------------------------
  nomeTabacchi: string = '';
  // lat: number = this.mapService.lat;
  // lng: number = this.mapService.lng;

  showEditDataUserByAdmin: boolean = false
  idSelected: number = 0;
  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private userService: UserService, private toastr: ToastrService, private authService: AuthService, private mapService: MapService, private tabacchiService: TabacchiService) {
    Chart.register(...registerables);
  }

  //NGONINIT E AFTERVIEWINIT ----------------------------------------------------------------------------
  ngOnInit(): void {
    console.log('Admin Dashboard initialized');
    this.loadUsers();
    this.loadAllEconomi();
    this.loadAllTabacchi();

  }

  ngAfterViewInit(): void {
    this.mapCreaTabacchi = this.mapService.initMapCreaTabacchi(this.mapCreaTabacchi);
    this.initializeCharts();
  }

  // METODI PER PRENDERE LA LATITUDINE E LONGITUDINE DEL MARKER ----------------------------------------------------------------------------
  latMarker(): number {
    return this.mapService.lat;
  }

  lngMarker(): number {
    return this.mapService.lng;
  }

  // METODI PER Modificare i dati di un utente ----------------------------------------------------------------------------
  adminEditUserData() {
    this.userService.adminAggiornaDatiUtente(this.idSelected, this.nome, this.cognome, this.email, this.username)
      .subscribe({
        next: (res: GetUserDataResponse) => {
          this.toastr.success("Dati modificati con successo");
          this.showEditDataUserByAdmin = false;
        },
        error: (err: HttpErrorResponse) => {
          this.toastr.error(err.error.message);
        }
      });
  }

  toggleModalEditUserData(id: number) {
    this.showEditDataUserByAdmin = !this.showEditDataUserByAdmin;
    this.idSelected = id;
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
        this.toastr.success("Economo creato con successo");
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la registration');
      }
    });
  }

  // FUNZIONI PER CREARE TABACCHI ----------------------------------------------------------------------------
  creaTabacchi(): void {
    this.tabacchiService.creaTabacchi(this.nomeTabacchi, this.latMarker(), this.lngMarker(), this.economoSelezionatoId).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.nomeTabacchi = '';
        this.tabacchi.push({ nomeTabacchi: this.nomeTabacchi, lat: this.latMarker(), lng: this.lngMarker(), userId: this.economoSelezionatoId, tabacchiId: 0 });
        this.mapService.placeTabacchiMarkers(this.tabacchi, this.mapCreaTabacchi);
        this.toastr.success("Tabacchi creato con successo");
      },
      error: (err: HttpErrorResponse) => {
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
        this.numberOfUsers = this.utenti.length;
      },
      error: (error: HttpErrorResponse) => {
        this.toastr.error('Error while fetching users');
      }
    });
  }

  // Funzione per caricare tutti gli Economi ----------------------------------------------------------------------------
  loadAllEconomi(): void {
    this.userService.getAllByRuolo('ECONOMO').subscribe({
      next: (response: GetUserDataResponse[]) => {
        this.economi = response;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching Economi: ', error);
        this.toastr.error('Error while fetching Economi');
      }
    });

  }

  // Funzione per caricare tutti i Tabacchi ----------------------------------------------------------------------------
  loadAllTabacchi(): void {
    this.tabacchiService.getAllTabacchi().subscribe({
      next: (response: GetAllTabacchiResponse[]) => {
        console.log(response);
        this.tabacchi = response;
        this.mapService.placeTabacchiMarkers(response, this.mapCreaTabacchi);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching tabacchi: ', error);
        this.toastr.error('Error while fetching tabacchi');
      }
    });
  }

  // FUNZIONE PER ELIMINARE UN UTENTE ----------------------------------------------------------------------------
  deleteUser(userId: number): void {
    console.log('Eliminazione utente con id: ', userId);
    this.userService.deleteUser(userId).subscribe({
      next: (response: MessageResponse) => {
        console.log(response);
        this.loadUsers();
        this.toastr.success("Utente eliminato con successo");
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while deleting user: ', error);
        this.toastr.error('Error while deleting user');
      }
    });
  }

  // FUNZIONi PER INIZIALIZZARE I GRAFICI ----------------------------------------------------------------------------
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
