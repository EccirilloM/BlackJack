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
  private backendUrl: string = globalBackendUrl + 'auth/';
  private isAuthenticatedSource = new BehaviorSubject<boolean>(this.checkIsAuthenticatedInitial());
  isAuthenticated$ = this.isAuthenticatedSource.asObservable();

  constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) { }

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

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.backendUrl + 'login', request);
  }

  registrazione(request: RegistrazioneRequest): Observable<MessageResponse> {
    console.log(request);
    return this.http.post<MessageResponse>(this.backendUrl + 'registrazionePlayer', request);
  }

  logout(): void {
    this.router.navigateByUrl('/login');
    localStorage.clear();
    this.toastr.success('Logout effettuato con successo');
    this.setIsAuthenticated(false);
  }

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
