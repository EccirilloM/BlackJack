import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { GetAllTabacchiResponse } from '../dto/response/getAllTabacchiResponse';
import { globalBackendUrl } from 'environment';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  constructor(private http: HttpClient) { }

  private nominatimUrl = 'https://nominatim.openstreetmap.org';
  searchNominatimLocation(query: string): Observable<any> {
    const url = `${this.nominatimUrl}/search?format=json&q=${encodeURIComponent(query)}`;
    return this.http.get<any>(url).pipe(
      catchError(error => {
        console.error('Errore nella richiesta a Nominatim:', error);
        throw error;
      })
    );
  }

  getAllUsers(): Observable<GetAllTabacchiResponse[]> {
    const header = this.getHeader();
    return this.http.get<GetAllTabacchiResponse[]>(globalBackendUrl + "" + 'getAllTabacchi', { headers: header });
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
