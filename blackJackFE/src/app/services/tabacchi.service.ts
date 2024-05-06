import { Injectable } from '@angular/core';
import { globalBackendUrl } from 'environment';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { CreaTabacchiRequest } from '../dto/request/CreaTabacchiRequest';
import { Observable } from 'rxjs';
import { MessageResponse } from '../dto/response/MessageResponse';
import { GetAllTabacchiResponse } from '../dto/response/GetAllTabacchiResponse';


@Injectable({
  providedIn: 'root'
})
export class TabacchiService {
  private backendUrl: string = globalBackendUrl + 'tabacchi/';

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient) { }

  // CHIAMATE AL BACKEND PER IL TABACCHI -----------------------------------------------------------------------------
  // METODO PER CREARE UN TABACCHI
  creaTabacchi(nomeTabacchi: string, lat: number, lng: number, economoId: number): Observable<MessageResponse> {
    const request: CreaTabacchiRequest = { nomeTabacchi, lat, lng, economoId };
    return this.http.post<MessageResponse>(this.backendUrl + 'creaTabacchi', request, { headers: this.getHeader() });
  }

  // METODO PER RICHIEDERE TUTTI I TABACCHI
  getAllTabacchi(): Observable<GetAllTabacchiResponse[]> {
    return this.http.get<GetAllTabacchiResponse[]>(this.backendUrl + 'getAllTabacchi', { headers: this.getHeader() });
  }

  // METODI PER RECUPERARE I DATI DELL'UTENTE LOGGATO ---------------------------------------------------------------
  //creo l'header con il token da mandare al backend
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('token') ? `${localStorage.getItem('token')}` : '',
      id: localStorage.getItem('id') ? `${localStorage.getItem('id')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
