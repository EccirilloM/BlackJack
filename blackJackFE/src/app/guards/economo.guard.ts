import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Ruolo } from '../types/ruolo';

@Injectable({
  providedIn: 'root'
})
export class EconomoGuard implements CanActivate {
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
    const ruolo = this.authService.getRole();

    if (!isAuthenticated || ruolo !== Ruolo.ECONOMO) {
      this.toastr.error('Access Denied. You must be an economo to access this page.');
      this.router.navigate(['/homepage']);
      return false;
    }

    return true;
  }


}
