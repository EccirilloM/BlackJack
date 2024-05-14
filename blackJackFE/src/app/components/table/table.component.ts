import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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


  constructor(private route: ActivatedRoute, private tablesService: TablesService, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const tipoTavoloParam: string = params.get('tipoTavolo')?.toString().toUpperCase() || '';
      this.initTavolo(tipoTavoloParam);
      // Verifica che tipoTavoloParam non sia null e sia un valore valido dell'enum Tavolo
      if (tipoTavoloParam && tipoTavoloParam in Tavolo) {
        this.tipoTavolo = Tavolo[tipoTavoloParam as keyof typeof Tavolo];
      } else {
        console.error('Tipo di table non valido o mancante:', tipoTavoloParam);
        // Qui potresti gestire il caso di un tipo di table non valido o mancante, es. reindirizzando l'utente
      }
    });
  }

  initTavolo(tipoTavolo: string): void {
    this.tablesService.initTavolo(tipoTavolo).subscribe({
      next: (data: MessageResponse) => {
        console.log('initTavolo', data);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Errore initTavolo', err);
      }
    });
  }

  // Funzione per iniziare una nuova partita
  deal() {
    this.tablesService.deal().subscribe({
      next: (data) => {
        console.log('deal', data);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Errore deal', err);
      }
    });
  }

  hit() {
    this.tablesService.hit().subscribe({
      next: (data: CartaResponse) => {
        console.log('hit', data);
        this.cartePlayer.push(data);
        this.scorePlayer += data.punteggio;
      },
      error: (err: HttpErrorResponse) => {
        console.error('Errore hit', err);
      }
    });
  }

  stand() {
    this.tablesService.stand().subscribe({
      next: (data) => {
        console.log('stand', data);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Errore stand', err);
      }
    });
  }

  double() {
    this.tablesService.double().subscribe({
      next: (data) => {
        console.log('double', data);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Errore doubl', err);
      }
    });
  }

  end(): void {
    this.cartePlayer = [];
    this.scorePlayer = 0;
    this.carteDealer = [];
    this.scorePlayer = 0;

    this.tablesService.end().subscribe({
      next: (data: MessageResponse) => {
        console.log('end', data);
        this.router.navigate(['/homepage/dashboard']);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Errore end', err);
      }
    });
  }

  goToHomePage(): void {
    this.router.navigate(['/homepage/dashboard']);
  }
}
