import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tavolo } from 'src/app/types/tavolo';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  protected puntataMinimaTavoloBase: number = 1;
  protected puntataMinimaTavoloPremium: number = 5;
  protected puntataMinimaTavoloVip: number = 10;
  protected puntataMinimaTavoloExclusive: number = 20;
  protected location: string = "Roma";
  protected currentHours: number = new Date().getHours();
  protected Tavolo = Tavolo;

  constructor(private router: Router) { }

  ngOnInit(): void {
    console.log("DashboardComponent initialized");
  }

  goToTable(tipoTavolo: Tavolo) {
    this.router.navigate(['/homepage/table', tipoTavolo]);
  }

}
