import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CartaResponse } from 'src/app/dto/response/CartaResponse';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { TablesService } from 'src/app/services/tables.service';
import { Tavolo } from 'src/app/types/tavolo';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  playerUsername: string = localStorage.getItem('username') || '';
  warningMessage: string = '';

  wager: number = 0;
  playerCash: number = 0;
  playerWinning: number = 0;

  tipoTavolo!: Tavolo;

  cartePlayer: CartaResponse[] = [];
  scorePlayer: number = 0;

  carteDealer: CartaResponse[] = [];
  scoreDealer: number = 0;

  puntataPlayer: number = 0;
  puntataMinima: number = 0;

  dealAttivo: boolean = true;

  tipoTavoloParam: string = '';

  constructor(private route: ActivatedRoute, private tablesService: TablesService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.tipoTavoloParam = params.get('tipoTavolo')?.toString().toUpperCase() || '';
      this.configureTableType(this.tipoTavoloParam);
      this.initTavolo(this.tipoTavoloParam);
    });
  }

  configureTableType(tipoTavoloParam: string): void {
    const puntate = {
      'BASE': 1,
      'PREMIUM': 5,
      'VIP': 10,
      'EXCLUSIVE': 20
    };

    if (tipoTavoloParam in puntate) {
      this.puntataMinima = puntate[tipoTavoloParam as keyof typeof puntate];
      this.wager = this.puntataMinima;
    } else {
      this.toastr.error('Tipo di tavolo non valido o mancante', 'Errore');
      this.router.navigate(['/homepage/dashboard']); // Rotta di errore o homepage
    }
  }


  initTavolo(tipoTavolo: string): void {
    this.tablesService.initTavolo(tipoTavolo).subscribe({
      next: (data: MessageResponse) => {
        this.toastr.success(data.message, 'Tavolo inizializzato');
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  // Funzione per iniziare una nuova partita
  deal() {
    this.carteDealer = [];
    this.scoreDealer = 0;
    this.cartePlayer = [];
    this.scorePlayer = 0;

    console.log('Inizio della partita');
    this.tablesService.deal().subscribe({
      next: (data) => {
        console.log('deal', data);
        this.dealAttivo = false;
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  hit() {
    console.log('Pesca una carta');
    this.tablesService.hit().subscribe({
      next: (data: CartaResponse) => {
        console.log('hit', data);
        this.cartePlayer.push(data);
        this.scorePlayer += data.punteggio;
        /*TODO quando la mano finisce bisogna solo fare:
          this.deakAttivo = true;
          così, l'unico pulsante cliccabile è "deal" e appena si clicca
          si riazzera tutto e si può iniziare una nuova mano*/
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  stand() {
    console.log('Stai');
    this.tablesService.stand().subscribe({
      next: (data) => {
        console.log('stand', data);
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  double() {
    console.log('Raddoppia la puntata');
    this.tablesService.double().subscribe({
      next: (data) => {
        console.log('double', data);
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  end(): void {
    console.log('Fine della partita');
    this.cartePlayer = [];
    this.scorePlayer = 0;
    this.carteDealer = [];
    this.scorePlayer = 0;

    this.tablesService.end().subscribe({
      next: (data: MessageResponse) => {
        console.log('end', data);
        this.initTavolo(this.tipoTavoloParam);
        this.dealAttivo = true;
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  goToHomePage(): void {
    this.router.navigate(['/homepage/dashboard']);
  }
}
