import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UpdateUserDataRequest } from '../dto/request/updateUserDataRequest';
import { GetUserDataResponse } from '../dto/response/getUserDataResponse';
import { globalBackendUrl } from 'environment';
import { Observable } from 'rxjs';
import { RicaricaSaldoRequest } from '../dto/request/ricaricaSaldoRequest';
import { MessageResponse } from '../dto/response/messageResponse';
import { RegistrazioneRequest } from '../dto/request/registrazioneRequest';

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

  getAllUsers(): Observable<GetUserDataResponse[]> {
    const header = this.getHeader();
    return this.http.get<GetUserDataResponse[]>(this.backendUrl + 'getAllUsers', { headers: header });
  }

  getAllEconomoUsers(): Observable<GetUserDataResponse[]> {
    const header = this.getHeader();
    return this.http.get<GetUserDataResponse[]>(this.backendUrl + 'getAllEconomoUsers', { headers: header });
  }

  richiediRicaricaSaldo(tabacchiId: number, importo: number): Observable<RicaricaSaldoRequest> {
    const header = this.getHeader();
    const request: RicaricaSaldoRequest = { tabacchiId, importo };
    return this.http.post<RicaricaSaldoRequest>(globalBackendUrl + "ricarica/richiediRicarica/" + localStorage.getItem("id")?.toString(), request, { headers: header });
  }

  creaEconomo(request: RegistrazioneRequest): Observable<MessageResponse> {
    const header = this.getHeader();
    return this.http.post<MessageResponse>(this.backendUrl + 'creaEconomo', request, { headers: header });
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
