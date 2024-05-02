import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from '../dto/request/loginRequest';
import { RegistrazioneRequest } from '../dto/request/registrazioneRequest';
import { LoginResponse } from '../dto/response/loginResponse';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MessageResponse } from '../dto/response/messageResponse';
import { globalBackendUrl } from 'environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Variabile che prende il valore dell'url globale e gli aggiunge l'atentificazione -------------------------------------
  private backendUrl: string = globalBackendUrl + 'auth/';
  // VARIABILE PER IL CONTROLLO DELLO STATO DELL'UTENTE LOGGATO ---------------------------------------------------------
  private isAuthenticatedSource = new BehaviorSubject<boolean>(this.checkIsAuthenticatedInitial());
  isAuthenticated$ = this.isAuthenticatedSource.asObservable();

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) { }

  // METODI PER IL CONTROLLO DELLO STATO DELL'UTENTE LOGGATO -----------------------------------------------------------
  // Rinominato per evitare confusione con il getter Observable
  private checkIsAuthenticatedInitial(): boolean {
    const token = localStorage.getItem('token');
    return !!token; // Restituisce true se token esiste, altrimenti false
  }

  // Usato per accedere allo stato corrente in un modo reattivo
  checkIsAuthenticated(): Observable<boolean> {
    return this.isAuthenticated$;
  }

  setIsAuthenticated(value: boolean) {
    this.isAuthenticatedSource.next(value);
  }

  // METODI PER IL LOGIN  -----------------------------------------------------------------------------
  login(request: LoginRequest): Observable<LoginResponse> {
    this.setIsAuthenticated(true);
    return this.http.post<LoginResponse>(this.backendUrl + 'login', request);
  }

  // METODI PER LA REGISTRAZIONE  -----------------------------------------------------------------------------
  registrazione(request: RegistrazioneRequest): Observable<MessageResponse> {
    console.log(request);
    return this.http.post<MessageResponse>(this.backendUrl + 'registrazionePlayer', request);
  }

  // METODI PER IL LOGOUT  -----------------------------------------------------------------------------
  logout(): void {
    this.router.navigateByUrl('/login');
    localStorage.clear();
    this.toastr.success('Logout effettuato con successo');
    this.setIsAuthenticated(false);
  }

  // METODI PER IL RECUPERO DI DATI DELL'UTENTE LOGGATO  -----------------------------------------------------------------------------
  getHttpHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getToken(): string {
    return localStorage.getItem('token') ?? '';
  }

  getRole(): string {
    return localStorage.getItem('ruolo') ?? '';
  }
}
