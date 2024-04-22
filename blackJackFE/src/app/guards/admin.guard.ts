import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service'; // Assicurati che il percorso sia corretto
import { Ruolo } from '../types/ruolo';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService // Aggiungi ToastrService
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const isAuthenticated = this.authService.checkIsAuthenticated();
    const isAdmin = this.authService.getRole() === Ruolo.ADMIN;

    if (!isAuthenticated || !isAdmin) {
      this.toastr.error('Accesso Negato, Devi essere Admin per essere in questa pagina'); // Utilizza Toastr invece di alert
      this.router.navigate(['/homepage']);
      return false;
    }

    return true;
  }
}

