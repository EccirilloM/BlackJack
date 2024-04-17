import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UpdateUserDataRequest } from '../dto/request/updateUserDataRequest';
import { GetUserDataResponse } from '../dto/response/getUserDataResponse';
import { globalBackendUrl } from 'environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private backendUrl: string = globalBackendUrl + 'user/';

  constructor(private http: HttpClient) { }

  aggiornaDatiUtente(nome: string, cognome: string, email: string, username: string, vecchiaPassword: string, nuovaPassword: string): Observable<GetUserDataResponse> {
    const header = this.getHeader();
    const request: UpdateUserDataRequest = { nome, cognome, email, username, vecchiaPassword, nuovaPassword };

    return this.http.put<GetUserDataResponse>(this.backendUrl + 'aggiornaDatiUtente/' + localStorage.getItem('id')?.toString(), request, { headers: header });
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
