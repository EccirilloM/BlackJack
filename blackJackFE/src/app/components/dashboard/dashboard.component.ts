import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  puntataMinimaTavoloBase: number = 1;
  puntataMinimaTavoloPremium: number = 5;
  puntataMinimaTavoloVip: number = 10;
  puntataMinimaTavoloExclusive: number = 20;
  location: string = "Roma";
  currentHours: number = new Date().getHours();

  constructor() { }

  ngOnInit(): void {
    console.log("DashboardComponent initialized");
  }

  startGame() {
    console.log("Game started");
  }

}
