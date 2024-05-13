import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tavolo } from 'src/app/types/tavolo';

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
  Tavolo = Tavolo;

  constructor(private router: Router) { }

  ngOnInit(): void {
    console.log("DashboardComponent initialized");
  }

  goToTable(tipoTavolo: Tavolo) {
    this.router.navigate(['/homepage/tavolo', tipoTavolo]);
  }

}
