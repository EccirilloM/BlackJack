import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router, NavigationEnd, Event } from '@angular/router';
import { filter } from 'rxjs';
import { Tavolo } from 'src/app/types/tavolo';
import { ToastrService } from 'ngx-toastr';
import { Ruolo } from 'src/app/types/ruolo';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  // VARIABILI PER L'IMMAGINE PROFILO -----------------------------------------------------------------------------------
  logoPath: string = 'assets/logos/BlackJackSaferLogo.png';
  // VARIABILI PER LA ROTTA -----------------------------------------------------------------------------------
  currentRoute: string = '';
  // VARIABILI PER L'UTENTE -----------------------------------------------------------------------------------
  userId: string = localStorage.getItem('id') ?? '';
  userNome: string = localStorage.getItem('nome') ?? '';
  userCognome: string = localStorage.getItem('cognome') ?? '';
  userUsername: string = localStorage.getItem('username') ?? '';
  userDisplayName: string = `@${this.userUsername}`;

  // COSTRUTTORE -----------------------------------------------------------------------------------
  constructor(private userService: UserService, private authService: AuthService, private router: Router) {
    this.currentRoute = this.router.url.split('/')[1];
  }

  // NGONINIT -----------------------------------------------------------------------------------
  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: Event) => {
      let navigationEndEvent = event as NavigationEnd;
      this.currentRoute = navigationEndEvent.urlAfterRedirects.split('/')[1];
    });
  }

  // METODI PER IL LOGOUT -----------------------------------------------------------------------------------
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // METODI PER LA NAVIGAZIONE IN ALTRE PAGINE -----------------------------------------------------------------------------------
  goToDashboard(): void {
    this.router.navigate(['/homepage/dashboard']);
  }

  goToProfile(): void {
    this.router.navigate([`/homepage/profile/${this.userId}`]);
  }

  goToChargeMoney(): void {
    this.router.navigate(['/homepage/chargeMoney']);
  }

  goToForum(tipoTavolo: Tavolo): void {
    this.router.navigate([`/homepage/forum/${tipoTavolo}`]);
  }

  goToAdmin(): void {
    this.router.navigate(['/homepage/adminDashboard']);
  }

  goToEconomo(): void {
    this.router.navigate(['/homepage/economoDashboard']);
  }

  // METODI PER IL MENU DEL FORUM -----------------------------------------------------------------------------------
  forumMenuOpen: boolean = false;

  toggleForumMenu(): void {
    this.forumMenuOpen = !this.forumMenuOpen;
  }

  isAdmin(): boolean {
    return this.authService.getRole() === Ruolo.ADMIN;
  }

  isEconomo(): boolean {
    return this.authService.getRole() === Ruolo.ECONOMO;
  }

}
