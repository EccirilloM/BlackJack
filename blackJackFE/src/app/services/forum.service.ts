import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Tavolo } from '../types/tavolo';
import { GetAllMessagesByTipoTavoloResponse } from '../dto/response/GetAllMessagesByTipoTavoloResponse';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { globalBackendUrl } from 'environment';
import { MessageResponse } from '../dto/response/MessageResponse';
import { InviaMessaggioRequest } from '../dto/request/InviaMessaggioRequest';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private backendUrl: string = globalBackendUrl + 'messaggio/';

  private currentTavolo = new BehaviorSubject<Tavolo | null>(null);

  constructor(private http: HttpClient) { }

  // Metodo per aggiornare il tipo di table selezionato
  changeTavolo(tipoTavolo: Tavolo): void {
    this.currentTavolo.next(tipoTavolo);
  }

  // Metodo per ottenere l'Observable del tipo di table attuale
  getCurrentTavolo(): Observable<Tavolo | null> {
    return this.currentTavolo.asObservable();
  }

  getAllMessagesByTipoTavolo(tipoTavolo: string | undefined): Observable<GetAllMessagesByTipoTavoloResponse[]> {
    return this.http.get<GetAllMessagesByTipoTavoloResponse[]>(this.backendUrl + 'getAllMessageByTipoTavolo/' + tipoTavolo, { headers: this.getHeader() });
  }

  inviaMessaggio(tipoTavolo: string | undefined, testo: string): Observable<MessageResponse> {
    const request: InviaMessaggioRequest = { testo: testo, mittenteId: parseInt(localStorage.getItem('id') || '0'), tipoTavolo: tipoTavolo || '' };
    return this.http.post<MessageResponse>(this.backendUrl + 'invia', request, { headers: this.getHeader() });
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

