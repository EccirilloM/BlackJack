import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { getAllManiResponse } from 'src/app/dto/response/GetAllManiResponse';
import { GetUserDataResponse } from 'src/app/dto/response/GetUserDataResponse';
import { NotificaResponse } from 'src/app/dto/response/NotificaResponse';
import { ManoService } from 'src/app/services/mano.service';
import { NotificheService } from 'src/app/services/notifiche.service';
import { UserService } from 'src/app/services/user.service';
import * as d3 from 'd3';
import { ChartService } from 'src/app/services/chart.service';

@Component({
  selector: 'app-personal-info',
  templateUrl: './personal-info.component.html',
  styleUrls: ['./personal-info.component.css']
})
export class PersonalInfoComponent implements OnInit {
  // VARIABILI PER IL GRAFICO -----------------------------------------------------------------------------------
  @ViewChild('chartContainer') chartContainer!: ElementRef; // Aggiornato per usare il div anziché canvas
  // VARIABILI PER SALDO -----------------------------------------------------------------------------------
  saldoString: string = localStorage.getItem('saldo') || '0';
  saldo: number = parseFloat(this.saldoString);

  // VARIABILI PER INFORMAZIONI UTENTE -----------------------------------------------------------------------------------
  fullName: string = "";
  birthday: string = "";
  joined: string = "";
  email: string = "";
  daysAgo: number = 0;


  notifiche: NotificaResponse[] = [];

  maniUtente: getAllManiResponse[] = [];
  wonHands: number = 0;
  lostHands: number = 0; // Aggiunto lostHands
  sessionPlayed: number = 0;

  // COSTRUTTORE -----------------------------------------------------------------------------------
  constructor(private notificheService: NotificheService,
    private userService: UserService,
    private toastr: ToastrService,
    private manoService: ManoService,
    private chartService: ChartService) {
    this.initializeUserInfo();
  }

  // NGONINIT E AFTERVIEWINIT -----------------------------------------------------------------------------------
  ngOnInit(): void {
    console.log('PersonalInfoComponent initialized');
    this.loadNotifiche();
    this.loadUserData(parseInt(localStorage.getItem('id') || '0'));
    this.loadAllManiByUserId(parseInt(localStorage.getItem('id') || '0'));
  }

  ngAfterViewInit(): void {
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
        this.calculateWinLoss();
        this.initializeChart();
      },
      error: (error: HttpErrorResponse) => {
        this.toastr.error('Error while fetching user data');
        console.error('Error while fetching user data: ', error);
      }
    });
  }

  calculateWinLoss(): void {
    this.wonHands = this.maniUtente.filter(mano => mano.importo > 0).length;
    this.lostHands = this.maniUtente.length - this.wonHands;
  }

  private initializeChart(): void {
    if (this.chartContainer.nativeElement) {
      const data = [
        { label: 'Won Hands', value: this.wonHands },
        { label: 'Lost Hands', value: this.lostHands }
      ];
      this.chartService.createPieChart(this.chartContainer.nativeElement, data);
    }
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
}
