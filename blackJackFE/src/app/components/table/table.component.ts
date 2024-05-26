import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CartaResponse } from 'src/app/dto/response/CartaResponse';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { TavoloStatusResponse } from 'src/app/dto/response/TavoloStatusResponse';
import { TablesService } from 'src/app/services/tables.service';
import { Tavolo } from 'src/app/types/tavolo';
import { Wager } from 'src/app/types/wager';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  protected playerUsername: string = localStorage.getItem('username') || '';
  protected warningMessage: string = '';

  protected wager: number = 0;
  protected playerCash: number = 0;
  protected playerWinning: number = 0;

  protected tipoTavolo!: Tavolo;

  protected cartePlayer: CartaResponse[] = [];
  protected scorePlayer: number = 0;

  protected carteDealer: CartaResponse[] = [];
  protected scoreDealer: number = 0;

  protected puntataPlayer: number = 0;
  protected puntataMinima: number = 0;

  protected dealAttivo: boolean = true;

  protected tipoTavoloParam: string = '';

  protected conteggio: number = 0;
  protected carteUnicheGiocate: Set<number> = new Set();

  protected valoreReale: number = 0;

  protected availableCommands: string[] = [];


  constructor(private route: ActivatedRoute,
    private tablesService: TablesService,
    private router: Router,
    private toastr: ToastrService) {
    const saldo = localStorage.getItem('saldo');
    this.playerCash = saldo ? parseFloat(saldo) : 0;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.tipoTavoloParam = params.get('tipoTavolo')?.toString().toUpperCase() || '';
      this.configureTableType(this.tipoTavoloParam);
      this.initTavolo(this.tipoTavoloParam);
      this.loadAvailableCommands();
    });
  }

  numeroDiMazzi: number = 6; // Default al valore massimo se non specificato

  configureTableType(tipoTavoloParam: string): void {
    const puntate = {
      'BASE': { minima: 1, mazzi: 6 },
      'PREMIUM': { minima: 5, mazzi: 4 },
      'VIP': { minima: 10, mazzi: 3 },
      'EXCLUSIVE': { minima: 20, mazzi: 2 }
    };

    const config = puntate[tipoTavoloParam as keyof typeof puntate];
    if (config) {
      this.puntataMinima = config.minima;
      this.numeroDiMazzi = config.mazzi;
      this.wager = this.puntataMinima;
    } else {
      this.toastr.error('Tipo di tavolo non valido o mancante', 'Errore');
      this.router.navigate(['/homepage/dashboard']);
    }
  }

  loadAvailableCommands(): void {
    this.tablesService.getAllCommandActions().subscribe({
      next: (commands: string[]) => {
        this.availableCommands = commands;
      },
      error: (error: HttpErrorResponse) => {
        this.toastr.error('Failed to load commands', 'Error');
      }
    });
  }

  onCommandClick(command: string): void {
    if (command === 'deal' && this.wager > 0) {
      this.sendCommandToBackend(command, this.wager);
      this.dealAttivo = false; // Disabilita Deal appena viene cliccato
    } else if (command !== 'deal') {
      this.sendCommandToBackend(command, NaN);
      if (!this.dealAttivo) {
        this.dealAttivo = true; // Potrebbe non essere necessario se vuoi che Deal sia disabilitato fino a un nuovo inizio
      }
    } else {
      this.toastr.error('Wager is not set properly', 'Error');
    }
  }



  sendCommandToBackend(command: string, plot: number): void {
    const body: Wager = { plot };
    console.log("Final body to send:", body); // Assicurati che questo mostri { plot: this.wager } correttamente
    this.tablesService.executeCommandAction(command, body).subscribe({
      next: (data) => {
        console.log('Data received:', data);
        this.playerCash = data.saldo;
        this.playerWinning = data.winning;
        this.handleTavoloStatus(data);
        this.updateConteggio([...data.cartePlayer, ...data.carteDealer]);
        this.dealAttivo = data.tavoloStatus === 'PLAYER_WIN' || data.tavoloStatus === 'PLAYER_LOSE' || data.tavoloStatus === 'DRAW' ? true : false;
      },
      error: (err: HttpErrorResponse) => {
        this.toastr.error(err.error.message, 'Error');
        this.router.navigate(['/homepage/dashboard']);
      }
    });
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
    console.log("dealAttivo updated to:", this.dealAttivo);
  }

  updateConteggio(carte: CartaResponse[]): void {
    carte.forEach(carta => {
      if (!this.carteUnicheGiocate.has(carta.order)) {
        this.carteUnicheGiocate.add(carta.order);
        const valore = carta.valore;
        if (['2', '3', '4', '5', '6'].includes(valore)) {
          this.conteggio += 1;
        } else if (['10', 'J', 'Q', 'K', 'A'].includes(valore)) {
          this.conteggio -= 1;
        }
        // Le carte 7, 8, 9 non modificano il conteggio
      }
    });

    const mazziRimasti = Math.max(this.numeroDiMazzi - (this.carteUnicheGiocate.size / 52), 0.5); // Evita la divisione per zero
    this.valoreReale = this.conteggio / mazziRimasti; // Calcola il valore reale

    if (this.valoreReale > 4) { // Cambio soglia per mostrare il messaggio
      this.toastr.info('Il Mazzo è carico! Aumenta la puntata!', 'Informazione', {
        timeOut: 3000
      });
    } else if (this.valoreReale < -4) {
      this.toastr.info('Il Mazzo è scarico! Diminuisci la puntata!', 'Informazione', {
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
