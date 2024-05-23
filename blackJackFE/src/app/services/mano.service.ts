import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { globalBackendUrl } from 'environment';
import { getAllManiResponse } from '../dto/response/GetAllManiResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManoService {

  private backendUrl: string = globalBackendUrl + 'mano/';

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient) { }

  // METODI PUBBLICI -----------------------------------------------------------------------------------------------------
  // METODO PER RICHIEDERE TUTTe LE MANI
  getAllMani(): Observable<getAllManiResponse[]> {
    return this.http.get<getAllManiResponse[]>(this.backendUrl + 'getAllMani', { headers: this.getHeader() });
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
