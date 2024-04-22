import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UpdateUserDataRequest } from '../dto/request/updateUserDataRequest';
import { GetUserDataResponse } from '../dto/response/getUserDataResponse';
import { globalBackendUrl } from 'environment';
import { Observable } from 'rxjs';
import { GetRichiestaRicaricaSaldoResponse } from '../dto/response/getRichiestaRicaricaSaldoResponse';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class HandlerService {

  constructor(private http: HttpClient, private userService: UserService) { }


}
