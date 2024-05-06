import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { globalBackendUrl } from 'environment';
import { MessageResponse } from '../dto/response/MessageResponse';
import { Observable } from 'rxjs';
import { GetAllRichiestaRicaricaSaldoResponse } from '../dto/response/GetAllRichiestaRicaricaSaldoResponse';
import { AccettaRichiestaRequest } from '../dto/request/AccettaRichiestaRequest';

@Injectable({
  providedIn: 'root'
})
export class RicaricaService {

  private backendUrl: string = globalBackendUrl + 'ricarica/';

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient) { }

  // CHIAMATE AL BACKEND PER LA RICARICA --------------------------------------------------------------------------------

  // METODI PER IL RECUPERO DEI DATI DELL'UTENTE LOGGATO ---------------------------------------------------------------

  //
  getAllRichiesteByEconomo(): Observable<GetAllRichiestaRicaricaSaldoResponse[]> {
    return this.http.get<GetAllRichiestaRicaricaSaldoResponse[]>(this.backendUrl + 'getAllRichiesteByEconomo/' + localStorage.getItem('id'), { headers: this.getHeader() });
  }

  accettaRichiesta(richiestaId: number, playerId: number): Observable<MessageResponse> {
    const request: AccettaRichiestaRequest = { richiestaId, playerId };
    return this.http.put<MessageResponse>(this.backendUrl + 'accettaRichiesta', request, { headers: this.getHeader() });
  }

  rifiutaRichiesta(richiestaId: number): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(this.backendUrl + 'rifiutaRichiesta/' + richiestaId.toString(), { headers: this.getHeader() });
  }
  //creo l'header con il token da mandare al backend
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('token') ? `${localStorage.getItem('token')}` : '',
      id: localStorage.getItem('id') ? `${localStorage.getItem('id')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
