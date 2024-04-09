import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  constructor(private http: HttpClient, private router: Router, private toastr: ToastrService) { }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.backendUrl + 'login', request);
  }

  registrazione(request: RegistrazioneRequest): Observable<MessageResponse> {
    console.log(request);
    return this.http.post<MessageResponse>(this.backendUrl + 'registrazionePlayer', request);
  }

  logout(): void {
    this.router.navigateByUrl('login');
    localStorage.clear();
    this.toastr.success('Logout effettuato con successo');
  }
}
