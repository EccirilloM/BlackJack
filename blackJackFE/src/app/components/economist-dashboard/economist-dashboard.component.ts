import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { GetAllRichiestaRicaricaSaldoResponse } from 'src/app/dto/response/GetAllRichiestaRicaricaSaldoResponse';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { RicaricaService } from 'src/app/services/ricarica.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-economist-dashboard',
  templateUrl: './economist-dashboard.component.html',
  styleUrls: ['./economist-dashboard.component.css']
})
export class EconomistDashboardComponent implements OnInit {
  richieste: GetAllRichiestaRicaricaSaldoResponse[] = [];
  nomeEconomo: string = localStorage.getItem('nome') || '';

  constructor(private ricaricaService: RicaricaService, private toastr: ToastrService) { }

  ngOnInit(): void {
    console.log('EconomistDashboardComponent');
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
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching richieste: ', error);
      }
    });
  }

  accettaRichiesta(richiesta: GetAllRichiestaRicaricaSaldoResponse): void {
    console.log('Accetto richiesta: ');
    console.log(richiesta);
    this.ricaricaService.accettaRichiesta(richiesta.richiestaId, richiesta.playerId).subscribe({
      next: (response: MessageResponse) => {
        console.log(response);
        this.loadRichieste();
        this.toastr.success('Richiesta accettata', 'Successo');
      },
      error: (error: HttpErrorResponse) => {
        this.toastr.error('Errore durante l\'accettazione della richiesta', 'Errore');
      }
    });
  }

  rifiutaRichiesta(richiesta: GetAllRichiestaRicaricaSaldoResponse): void {
    console.log('Rifiuto richiesta: ');
    console.log(richiesta);
    this.ricaricaService.rifiutaRichiesta(richiesta.richiestaId).subscribe({
      next: (response: MessageResponse) => {
        console.log(response);
        this.loadRichieste();
        this.toastr.success('Richiesta rifiutata', 'Successo');
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while refusing richiesta: ', error);
        this.toastr.error('Errore durante il rifiuto della richiesta', 'Errore');
      }
    });
  }
}
