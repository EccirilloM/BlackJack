import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router, NavigationEnd, Event } from '@angular/router';
import { filter } from 'rxjs';
import { Tavolo } from 'src/app/types/tavolo';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  logoPath: string = 'assets/logos/BlackJackSaferLogo.png';
  currentRoute: string = '';
  userId: string = localStorage.getItem('id') ?? '';
  userNome: string = localStorage.getItem('nome') ?? '';
  userCognome: string = localStorage.getItem('cognome') ?? '';
  userUsername: string = localStorage.getItem('username') ?? '';
  userDisplayName: string = `@${this.userUsername}`;
  Tavolo = Tavolo;


  constructor(private userService: UserService, private authService: AuthService, private router: Router) {
    this.currentRoute = this.router.url.split('/')[1];
  }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: Event) => {
      let navigationEndEvent = event as NavigationEnd;
      this.currentRoute = navigationEndEvent.urlAfterRedirects.split('/')[1];
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

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

  forumMenuOpen: boolean = false;

  toggleForumMenu(): void {
    this.forumMenuOpen = !this.forumMenuOpen;
  }

}
