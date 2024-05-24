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

  end(): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.backendUrl}end/` + localStorage.getItem('id')?.toString(), {});
  }

  // COMMANDDD---------------------------------------------------------------------------
  getAllCommandActions(): Observable<string[]> {
    return this.http.get<string[]>(`${this.backendUrl}getCommandsAvaliable`, { headers: this.getHeader() });
  }

  executeCommandAction(command: string, data: any): Observable<TavoloStatusResponse> {
    const userId = localStorage.getItem('id');
    const url = `${this.backendUrl}${command}/${userId}`;
    console.log("Sending to backend:", JSON.stringify(data));
    return this.http.post<TavoloStatusResponse>(url, JSON.stringify(data), { headers: this.getHeader() });
  }

  //creo l'header con il token da mandare al backend
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json', // Assicurati che sia impostato
      'Authorization': localStorage.getItem('token') ? `${localStorage.getItem('token')}` : '',
      id: localStorage.getItem('id') ? `${localStorage.getItem('id')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }

}
