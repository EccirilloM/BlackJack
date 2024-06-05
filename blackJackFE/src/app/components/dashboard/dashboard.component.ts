import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tavolo } from 'src/app/types/tavolo';
// -----------------------------------------------------------------------------------
// COMPONENTE DASHBOARD
// Questo componente gestisce la visualizzazione della dashboard dei tavoli dell'applicazione.
// Implementa OnInit, un'interfaccia che espone un metodo che viene eseguito non appena il componente viene visualizzato.
// -----------------------------------------------------------------------------------
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // -----------------------------------------------------------------------------------
  // VARIABILI PER I TAVOLI
  // Queste variabili memorizzano le puntate minime per diversi tipi di tavoli e la località.
  // -----------------------------------------------------------------------------------
  protected puntataMinimaTavoloBase: number = 1;
  protected puntataMinimaTavoloPremium: number = 5;
  protected puntataMinimaTavoloVip: number = 10;
  protected puntataMinimaTavoloExclusive: number = 20;
  protected location: string = "Roma";
  protected currentHours: string = "";
  protected Tavolo = Tavolo;

  // -----------------------------------------------------------------------------------
  // COSTRUTTORE
  // Il costruttore inietta il servizio Router necessario per la navigazione.
  // -----------------------------------------------------------------------------------
  constructor(private router: Router) { }

  // -----------------------------------------------------------------------------------
  // METODO ngOnInit
  // Questo metodo viene eseguito non appena il componente viene visualizzato.
  // -----------------------------------------------------------------------------------
  ngOnInit(): void {
    console.log("DashboardComponent initialized");
    this.setCurrentTime();
  }

  // -----------------------------------------------------------------------------------
  // METODO PER IMPOSTARE L'ORA CORRENTE
  // Imposta l'ora corrente nel formato hh:mm.
  // -----------------------------------------------------------------------------------
  setCurrentTime() {
    const currentTime = new Date();
    this.currentHours = currentTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  }

  // -----------------------------------------------------------------------------------
  // METODO PER LA NAVIGAZIONE AL TAVOLO
  // Naviga alla pagina del tavolo selezionato.
  // -----------------------------------------------------------------------------------
  goToTable(tipoTavolo: Tavolo) {
    this.router.navigate(['/homepage/table', tipoTavolo]);
  }
}
