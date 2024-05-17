import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CartaResponse } from 'src/app/dto/response/CartaResponse';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { TavoloStatusResponse } from 'src/app/dto/response/TavoloStatusResponse';
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

  conteggio: number = 0;



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

  // Funzione per iniziare una nuova Mano
  deal() {
    this.carteDealer = [];
    this.scoreDealer = 0;
    this.cartePlayer = [];
    this.scorePlayer = 0;
    this.warningMessage = '';

    console.log('Inizio della partita');
    this.tablesService.deal(this.puntataPlayer).subscribe({
      next: (data: TavoloStatusResponse) => {
        console.log('deal', data);
        this.dealAttivo = false;
        this.cartePlayer = data.cartePlayer;
        this.scorePlayer = data.punteggioPlayer;
        this.carteDealer = data.carteDealer;
        this.scoreDealer = data.punteggioDealer;
        this.updateConteggio([...data.cartePlayer, ...data.carteDealer]);
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
      next: (data: TavoloStatusResponse) => {
        console.log('hit', data);
        this.handleTavoloStatus(data);
        this.updateConteggio(data.cartePlayer);
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  stand() {
    console.log('Stai');
    this.tablesService.stand().subscribe({
      next: (data: TavoloStatusResponse) => {
        console.log('stand', data);
        this.handleTavoloStatus(data);
        this.updateConteggio([...data.cartePlayer, ...data.carteDealer]);
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
      next: (data: TavoloStatusResponse) => {
        console.log('double', data);
        this.handleTavoloStatus(data);
        this.updateConteggio([...data.cartePlayer, ...data.carteDealer]);
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Errore');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
  }

  private handleTavoloStatus(response: TavoloStatusResponse): void {
    this.cartePlayer = response.cartePlayer;
    this.scorePlayer = response.punteggioPlayer;
    this.carteDealer = response.carteDealer;
    this.scoreDealer = response.punteggioDealer;

    switch (response.tavoloStatus) {
      case 'PLAYER_WIN':
        this.warningMessage = 'Hai vinto!';
        this.dealAttivo = true;
        break;
      case 'PLAYER_LOSE':
        this.warningMessage = 'Hai perso!';
        this.dealAttivo = true;
        break;
      case 'DRAW':
        this.warningMessage = 'Pareggio!';
        this.dealAttivo = true;
        break;
      case 'CONTINUE':
        this.warningMessage = '';
        this.dealAttivo = false;
        break;
      default:
        this.toastr.error('Stato tavolo sconosciuto', 'Errore');
        break;
    }
  }

  updateConteggio(carte: CartaResponse[]): void {
    carte.forEach(carta => {
      const valore = carta.valore; // Assumo che il valore sia una stringa come '2', '3', ..., '10', 'J', 'Q', 'K', 'A'
      if (['2', '3', '4', '5', '6'].includes(valore)) {
        this.conteggio += 1;
      } else if (['10', 'J', 'Q', 'K', 'A'].includes(valore)) {
        this.conteggio -= 1;
      }
      // Le carte 7, 8, 9 non modificano il conteggio
    });

    // Controlla se il conteggio è alto e mostra un messaggio
    if (this.conteggio > 6) {
      this.toastr.info('Il Mazzo è carico! Aumenta la puntata!', 'Informazione', {
        timeOut: 3000
      });
    }
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
