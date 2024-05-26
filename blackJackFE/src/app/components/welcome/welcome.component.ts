import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  protected userId: string = localStorage.getItem('id') ?? '';
  protected userUsername: string = localStorage.getItem("username") || "";
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goToProfile(): void {
    this.router.navigate([`/homepage/profile/${this.userId}`]);
  }
}
