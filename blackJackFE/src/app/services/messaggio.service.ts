import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { globalBackendUrl } from 'environment';
import { MessageResponse } from '../dto/response/MessageResponse';

@Injectable({
  providedIn: 'root'
})
export class MessaggioService {
  private backendUrl: string = globalBackendUrl + 'messaggio/';

  constructor(private http: HttpClient) { }

  // CHIAMATA PER RICHIEDERE LA RICARICA DEL DENARO
  // richiediRicaricaDenaro(importo: number): Observable<MessageResponse> {
  //   const request: InviaMessaggioRequest = { };
  //   return this.http.post<MessageResponse>(this.backendUrl + request, { headers: this.getHeader() });
  // }

  //creo l'header con il token da mandare al backend
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('token') ? `${localStorage.getItem('token')}` : '',
      id: localStorage.getItem('id') ? `${localStorage.getItem('id')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
