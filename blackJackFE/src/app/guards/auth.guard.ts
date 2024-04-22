import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService // Aggiungi ToastrService qui
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | boolean {
    return this.authService.checkIsAuthenticated().pipe(
      map(isAuthenticated => {
        if (!isAuthenticated) {
          this.toastr.error('Devi essere autenticato per accedere a questa pagina'); // Mostra il messaggio toastr
          this.router.navigate(['/login']);
          return false;
        }
        return true;
      })
    );
  }
}

