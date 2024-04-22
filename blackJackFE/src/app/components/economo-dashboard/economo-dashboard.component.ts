import { Component, OnInit } from '@angular/core';
import { GetRichiestaRicaricaSaldoResponse } from 'src/app/dto/response/getRichiestaRicaricaSaldoResponse';

@Component({
  selector: 'app-economo-dashboard',
  templateUrl: './economo-dashboard.component.html',
  styleUrls: ['./economo-dashboard.component.css']
})
export class EconomoDashboardComponent implements OnInit {
  richieste: GetRichiestaRicaricaSaldoResponse[] = [];
  nomeEconomo: string = 'Ettore';

  constructor() { }

  ngOnInit(): void {
    console.log('EconomoDashboardComponent');
  }

}
