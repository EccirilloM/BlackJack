import { Injectable } from '@angular/core';
import { globalBackendUrl } from 'environment';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { creaTabacchiRequest } from '../dto/request/creaTabacchiRequest';
import { Observable } from 'rxjs';
import { MessageResponse } from '../dto/response/messageResponse';
import { GetAllTabacchiResponse } from '../dto/response/getAllTabacchiResponse';


@Injectable({
  providedIn: 'root'
})
export class TabacchiService {
  private backendUrl: string = globalBackendUrl + 'tabacchi/';

  constructor(private http: HttpClient) { }

  creaTabacchi(nomeTabacchi: string, lat: number, lng: number, economoId: number): Observable<MessageResponse> {
    const request: creaTabacchiRequest = { nomeTabacchi, lat, lng, economoId };
    return this.http.post<MessageResponse>(this.backendUrl + 'creaTabacchi', request, { headers: this.getHeader() });
  }

  getAllTabacchi(): Observable<GetAllTabacchiResponse[]> {
    return this.http.get<GetAllTabacchiResponse[]>(this.backendUrl + 'getAllTabacchi', { headers: this.getHeader() });
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
