import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { globalBackendUrl } from 'environment';
import { MessageResponse } from '../dto/response/messageResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RicaricaService {

  private backendUrl: string = globalBackendUrl + 'ricarica/';

  constructor(private http: HttpClient) { }

  richiediRicaricaDenaro(idTabacchi: number, importo: number): Observable<MessageResponse> {

    return this.http.get<MessageResponse>(this.backendUrl + 'richiediRicaricaDenaro', { headers: this.getHeader() });
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
