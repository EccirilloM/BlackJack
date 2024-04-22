import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-economo-dashboard',
  templateUrl: './economo-dashboard.component.html',
  styleUrls: ['./economo-dashboard.component.css']
})
export class EconomoDashboardComponent implements OnInit {
  nomeEconomo: string = 'Ettore';

  constructor() { }

  ngOnInit(): void {
    console.log('EconomoDashboardComponent');
  }

}
