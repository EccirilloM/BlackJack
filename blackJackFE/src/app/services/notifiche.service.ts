import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { globalBackendUrl } from 'environment';
import { NotificaResponse } from '../dto/response/NotificaResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificheService {

  private backendUrl: string = globalBackendUrl + 'notifica/';

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient) { }

  getAllByUserId(): Observable<NotificaResponse[]> {
    const header = this.getHeader();
    return this.http.get<NotificaResponse[]>(this.backendUrl + 'getAllNotificheByUserId/' + localStorage?.getItem('id'), { headers: header });
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
