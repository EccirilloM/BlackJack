import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { globalBackendUrl } from 'environment';
import { CartaResponse } from '../dto/response/CartaResponse';
import { MessageResponse } from '../dto/response/MessageResponse';

@Injectable({
  providedIn: 'root'
})
export class TablesService {
  private backendUrl: string = globalBackendUrl + 'table';

  constructor(private http: HttpClient) { }

  initTavolo(tipoTavolo: string): Observable<MessageResponse> {
    return this.http.get<MessageResponse>(`${this.backendUrl}/init/` + tipoTavolo + `/` + localStorage.getItem('id')?.toString(), { headers: this.getHeader() });
  }

  getGameStatus(): Observable<any> {
    return this.http.get<any>(`${this.backendUrl}/status`, { headers: this.getHeader() });
  }

  deal(): Observable<any> {
    return this.http.post<any>(`${this.backendUrl}/deal`, { headers: this.getHeader() });
  }

  hit(): Observable<CartaResponse> {
    return this.http.post<CartaResponse>(`${this.backendUrl}/hit/` + localStorage.getItem('id')?.toString(), { headers: this.getHeader() });
  }

  stand(): Observable<any> {
    return this.http.post<any>(`${this.backendUrl}/stand`, { headers: this.getHeader() });
  }

  double(): Observable<any> {
    return this.http.post<any>(`${this.backendUrl}/double`, { headers: this.getHeader() });
  }

  end(): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.backendUrl}/end/` + localStorage.getItem('id')?.toString(), {});
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
