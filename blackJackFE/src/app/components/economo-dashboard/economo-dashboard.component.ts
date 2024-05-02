import { Component, OnInit } from '@angular/core';
import { GetAllRichiestaRicaricaSaldoResponse } from 'src/app/dto/response/getAllRichiestaRicaricaSaldoResponse';
import { RicaricaService } from 'src/app/services/ricarica.service';

@Component({
  selector: 'app-economo-dashboard',
  templateUrl: './economo-dashboard.component.html',
  styleUrls: ['./economo-dashboard.component.css']
})
export class EconomoDashboardComponent implements OnInit {
  richieste: GetAllRichiestaRicaricaSaldoResponse[] = [];
  nomeEconomo: string = localStorage.getItem('nome') || '';

  constructor(private ricaricaService: RicaricaService) { }

  ngOnInit(): void {
    console.log('EconomoDashboardComponent');
    this.loadRichieste();
  }

  // METODI PER LE RICHIESTE DI RICARICA SALDO ----------------------------------------------------------------------------
  loadRichieste(): void {
    console.log('Carico le richieste');
    this.ricaricaService.getAllRichiesteByEconomo().subscribe({
      next: (response: GetAllRichiestaRicaricaSaldoResponse[]) => {
        console.log(response);
        this.richieste = response;
      },
      error: (error: any) => {
        console.error('Error while fetching richieste: ', error);
      }
    });
  }

  accettaRichiesta(richiesta: GetAllRichiestaRicaricaSaldoResponse): void {
    console.log('Accetto richiesta: ');
    console.log(richiesta);
    this.ricaricaService.accettaRichiesta(richiesta.richiestaId, richiesta.playerId).subscribe({
      next: (response: any) => {
        console.log(response);
        this.loadRichieste();
      },
      error: (error: any) => {
        console.error('Error while accepting richiesta: ', error);
      }
    });
  }

  rifiutaRichiesta(richiesta: GetAllRichiestaRicaricaSaldoResponse): void {
    console.log('Rifiuto richiesta: ');
    console.log(richiesta);
    this.ricaricaService.rifiutaRichiesta(richiesta.richiestaId).subscribe({
      next: (response: any) => {
        console.log(response);
        this.loadRichieste();
      },
      error: (error: any) => {
        console.error('Error while refusing richiesta: ', error);
      }
    });
  }
}
