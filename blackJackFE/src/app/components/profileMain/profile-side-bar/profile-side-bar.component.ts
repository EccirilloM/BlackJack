import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-profile-side-bar',
  templateUrl: './profile-side-bar.component.html',
  styleUrls: ['./profile-side-bar.component.css']
})
export class ProfileSideBarComponent implements OnInit {
  // VARIABILI PER IL SUBPATH CORRENTE ----------------------------------------------------------------------------
  currentSubPath: string = '';

  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private router: Router, private route: ActivatedRoute) { }

  // NGONINIT ----------------------------------------------------------------------------
  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      // Aggiorna currentSubPath in base al percorso corrente
      const fullPath = this.router.url.split('/');
      this.currentSubPath = fullPath[fullPath.length - 1];
    });
  }

  // METODI PER LA NAVIGAZIONE IN ALTRE PAGINE ----------------------------------------------------------------------------
  navigateTo(subPath: string): void {
    // Naviga verso il sottopercorso relativo all'interno del profilo
    this.router.navigate([subPath], { relativeTo: this.route });
  }
}

