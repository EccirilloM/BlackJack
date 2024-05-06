import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UpdateUserDataRequest } from '../dto/request/UpdateUserDataRequest';
import { GetUserDataResponse } from '../dto/response/GetUserDataResponse';
import { globalBackendUrl } from 'environment';
import { Observable } from 'rxjs';
import { RicaricaSaldoRequest } from '../dto/request/RicaricaSaldoRequest';
import { MessageResponse } from '../dto/response/MessageResponse';
import { RegistrazioneRequest } from '../dto/request/RegistrazioneRequest';
import { AdminUpdateUserDataRequest } from '../dto/request/adminUpdateUserData';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private backendUrl: string = globalBackendUrl + 'user/';

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient) { }

  // CHIAMATE AL BACKEND PER L'UTENTE -----------------------------------------------------------------------------------
  // METODO PER AGGIORNA I DATI DELL'UTENTE
  aggiornaDatiUtente(nome: string, cognome: string, email: string, username: string, vecchiaPassword: string, nuovaPassword: string): Observable<GetUserDataResponse> {
    const header = this.getHeader();
    const request: UpdateUserDataRequest = { nome, cognome, email, username, vecchiaPassword, nuovaPassword };

    return this.http.put<GetUserDataResponse>(this.backendUrl + 'aggiornaDatiUtente/' + localStorage.getItem('id')?.toString(), request, { headers: header });
  }

  adminAggiornaDatiUtente(id: number, nome: string, cognome: string, email: string, username: string): Observable<GetUserDataResponse> {
    const header = this.getHeader();
    const request: AdminUpdateUserDataRequest = { nome, cognome, email, username };

    return this.http.put<GetUserDataResponse>(this.backendUrl + 'adminAggiornaDatiUtente/' + id.toString(), request, { headers: header });
  }

  // METODO PER RICHIEDERE TUTTI GLI UTENTI DELL'APPLICAZIONE
  getAllUsers(): Observable<GetUserDataResponse[]> {
    const header = this.getHeader();
    return this.http.get<GetUserDataResponse[]>(this.backendUrl + 'getAllUsers', { headers: header });
  }

  // METODO PER RICHIEDERE TUTTI GLI UTENTI CON UN DETERMINATO RUOLO
  getAllByRuolo(ruolo: string): Observable<GetUserDataResponse[]> {
    const header = this.getHeader();
    return this.http.get<GetUserDataResponse[]>(this.backendUrl + 'getAllByRuolo/' + ruolo, { headers: header });
  }

  getUserDataById(id: number): Observable<GetUserDataResponse> {
    const header = this.getHeader();
    return this.http.get<GetUserDataResponse>(this.backendUrl + 'getUserDataById/' + id, { headers: header });
  }

  // METODO PER CREARE UN ECONOMO
  creaEconomo(request: RegistrazioneRequest): Observable<MessageResponse> {
    const header = this.getHeader();
    return this.http.post<MessageResponse>(this.backendUrl + 'creaEconomo', request, { headers: header });
  }

  // METODO PER CREARE UN UTENTE
  creaUtente(request: RegistrazioneRequest): Observable<MessageResponse> {
    const header = this.getHeader();
    return this.http.post<MessageResponse>(this.backendUrl + 'creaUtente', request, { headers: header });
  }

  deleteUser(userId: number): Observable<MessageResponse> {
    const header = this.getHeader();
    return this.http.delete<MessageResponse>(this.backendUrl + 'delete/' + userId, { headers: header });
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
