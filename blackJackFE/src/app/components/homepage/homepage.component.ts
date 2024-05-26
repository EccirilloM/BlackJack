import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  protected isAuthenticated: boolean = false;

  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(public authService: AuthService, public router: Router) {
    this.authService.isAuthenticated$.subscribe(
      (isAuthenticated) => this.isAuthenticated = isAuthenticated
    );
  }
  ngOnInit(): void {

  }

}
