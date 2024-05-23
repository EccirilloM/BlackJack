import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { ToastrService } from 'ngx-toastr';
import { getAllManiResponse } from 'src/app/dto/response/GetAllManiResponse';
import { GetUserDataResponse } from 'src/app/dto/response/GetUserDataResponse';
import { NotificaResponse } from 'src/app/dto/response/NotificaResponse';
import { ManoService } from 'src/app/services/mano.service';
import { NotificheService } from 'src/app/services/notifiche.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-personal-info',
  templateUrl: './personal-info.component.html',
  styleUrls: ['./personal-info.component.css']
})
export class PersonalInfoComponent implements OnInit {
  // VARIABILI PER IL GRAFICO -----------------------------------------------------------------------------------
  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;
  // VARIABILI PER SALDO -----------------------------------------------------------------------------------
  saldoString: string = localStorage.getItem('saldo') || '0';
  saldo: number = parseFloat(this.saldoString);

  // VARIABILI PER INFORMAZIONI UTENTE -----------------------------------------------------------------------------------
  fullName: string = "";
  birthday: string = "";
  joined: string = "";
  email: string = "";
  daysAgo: number = 0;
  wonHands: number = 0;
  sessionPlayed: number = 0;

  notifiche: NotificaResponse[] = [];

  maniUtente: getAllManiResponse[] = [];

  // COSTRUTTORE -----------------------------------------------------------------------------------
  constructor(private notificheService: NotificheService,
    private userService: UserService,
    private toastr: ToastrService,
    private manoService: ManoService) {
    this.initializeUserInfo();
    Chart.register(...registerables);
  }

  // NGONINIT E AFTERVIEWINIT -----------------------------------------------------------------------------------
  ngOnInit(): void {
    console.log('PersonalInfoComponent initialized');
    this.loadNotifiche();
    this.loadUserData(parseInt(localStorage.getItem('id') || '0'));
    this.loadAllManiByUserId(parseInt(localStorage.getItem('id') || '0'));
  }

  ngAfterViewInit(): void {
    this.initializeChart();
  }

  loadUserData(id: number): void {
    this.userService.getUserDataById(id).subscribe({
      next: (response: GetUserDataResponse) => {
        this.saldo = response.saldo;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching user data: ', error);
        this.toastr.error('Error while fetching user data');
      }
    });
  }

  countWonHands(): void {
    this.wonHands = this.maniUtente.filter((mano) => mano.importo < 0).length;
  }

  countSessionPlayed(): void {
    if (!this.maniUtente || this.maniUtente.length === 0) {
      console.log("No games played.");
      return;
    }

    // Assicurati che le mani siano ordinate per data
    this.maniUtente.sort((a, b) => new Date(a.dataMano).getTime() - new Date(b.dataMano).getTime());

    let sessionCount = 1; // Inizia con una sessione
    let lastSessionTime = new Date(this.maniUtente[0].dataMano).getTime();

    this.maniUtente.forEach(mano => {
      const currentTime = new Date(mano.dataMano).getTime();
      // Se l'intervallo di tempo tra le mani è superiore a 20 minuti, consideralo una nuova sessione
      if (currentTime - lastSessionTime > 1200000) { // 1200000 ms = 20 minuti
        sessionCount++;
        lastSessionTime = currentTime;
      }
    });
    this.sessionPlayed = sessionCount;
  }


  loadAllManiByUserId(userId: number): void {
    this.manoService.getAllManiByUserId(userId).subscribe({
      next: (response: getAllManiResponse[]) => {
        this.maniUtente = response;
        this.countWonHands();
        this.countSessionPlayed();
        console.log('Mani utente: ', this.maniUtente);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching user data: ', error);
        this.toastr.error('Error while fetching user data');
      }
    });
  }

  // METODI PER INIZIALIZZARE I DATI UTENTE -----------------------------------------------------------------------------------
  private initializeUserInfo(): void {
    this.fullName = this.getFullName();
    this.birthday = this.formatDate(localStorage.getItem('dataNascita'));
    this.email = localStorage.getItem('email') ?? '';
    this.joined = this.formatDate(localStorage.getItem('dataRegistrazione'));
    this.daysAgo = this.calculateDaysAgo(localStorage.getItem('dataRegistrazione'));
  }

  private getFullName(): string {
    const nome = localStorage.getItem('nome') ?? '';
    const cognome = localStorage.getItem('cognome') ?? '';
    return `${nome} ${cognome}`;
  }

  loadNotifiche(): void {
    console.log('Caricamento notifiche');
    this.notificheService.getAllByUserId().subscribe({
      next: (response: NotificaResponse[]) => {
        response.forEach((notifica: NotificaResponse) => {
          this.notifiche.push({ data: new Date(notifica.data), messaggio: notifica.messaggio });
        });
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
      }
    });
  }

  private formatDate(dateString: string | null): string {
    if (!dateString) return 'Data non disponibile';
    const date = new Date(dateString);
    return date.toLocaleDateString("it-IT", {
      day: '2-digit',
      month: 'long',
      year: 'numeric'
    });
  }

  private calculateDaysAgo(dateString: string | null): number {
    if (!dateString) return 0;
    const date = new Date(dateString);
    const today = new Date();
    const diffTime = Math.abs(today.getTime() - date.getTime());
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  }

  // METODI PER IL GRAFICO -----------------------------------------------------------------------------------
  private initializeChart(): void {
    if (this.chartCanvas && this.chartCanvas.nativeElement) {
      const ctx = this.chartCanvas.nativeElement.getContext('2d');
      if (ctx) {
        const chart = new Chart(ctx, {
          type: 'bar', // O qualsiasi altro tipo di grafico
          data: {
            labels: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile'], // Esempio di etichette
            datasets: [{
              label: 'Esempio di Dati',
              data: [10, 20, 30, 40], // Esempio di dati
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)'
              ],
              borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)'
              ],
              borderWidth: 1
            }]
          },
          options: {
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });
      }
    }
  }
}
