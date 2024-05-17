import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { globalBackendUrl } from 'environment';
import { CartaResponse } from '../dto/response/CartaResponse';
import { MessageResponse } from '../dto/response/MessageResponse';
import { TavoloStatusResponse } from '../dto/response/TavoloStatusResponse';

@Injectable({
  providedIn: 'root'
})
export class TablesService {
  private backendUrl: string = globalBackendUrl + 'tavolo/';

  constructor(private http: HttpClient) { }

  initTavolo(tipoTavolo: string): Observable<MessageResponse> {
    return this.http.get<MessageResponse>(this.backendUrl + "init/" + tipoTavolo + "/" + localStorage.getItem('id')?.toString(), { headers: this.getHeader() });
  }

  deal(plot: number): Observable<TavoloStatusResponse> {
    const userId = localStorage.getItem('id')?.toString();
    const params = new HttpParams().set('plot', plot.toString());
    return this.http.post<TavoloStatusResponse>(`${this.backendUrl}deal/${userId}`, {}, { headers: this.getHeader(), params: params });
  }

  hit(): Observable<TavoloStatusResponse> {
    const userId = localStorage.getItem('id')?.toString();
    return this.http.post<TavoloStatusResponse>(`${this.backendUrl}hit/${userId}`, {}, { headers: this.getHeader() });
  }

  stand(): Observable<TavoloStatusResponse> {
    const userId = localStorage.getItem('id')?.toString();
    return this.http.post<TavoloStatusResponse>(`${this.backendUrl}stand/${userId}`, {}, { headers: this.getHeader() });
  }

  double(): Observable<TavoloStatusResponse> {
    const userId = localStorage.getItem('id')?.toString();
    return this.http.post<TavoloStatusResponse>(`${this.backendUrl}double/${userId}`, {}, { headers: this.getHeader() });
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
