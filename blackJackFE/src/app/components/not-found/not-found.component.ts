import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent {
  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private router: Router) { }

  // METODI PER LA NAVIGAZIONE ----------------------------------------------------------------------------
  goHome() {
    this.router.navigate(['/homepage']);
  }
}
