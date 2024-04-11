import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-personal-info',
  templateUrl: './personal-info.component.html',
  styleUrls: ['./personal-info.component.css']
})
export class PersonalInfoComponent implements OnInit {
  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;

  fullName: string = "";
  birthday: string = "";
  joined: string = "";
  email: string = "";
  daysAgo: number = 0;

  constructor() {
    this.initializeUserInfo();
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    console.log('PersonalInfoComponent initialized');
  }

  ngAfterViewInit(): void {
    this.initializeChart();
  }

  private initializeUserInfo(): void {
    this.fullName = this.getFullName();
    this.birthday = this.formatDate(localStorage.getItem('dataNascita'));
    this.email = localStorage.getItem('email') ?? '';
    this.joined = this.formatDate(localStorage.getItem('dataRegistrazione'));
    this.daysAgo = this.calculateDaysAgo(localStorage.getItem('dataRegistrazione'));
  }

  private getFullName(): string {
    const nome = localStorage.getItem('nome') ?? '';
    const cognome = localStorage.getItem('cognome') ?? '';
    return `${nome} ${cognome}`;
  }

  private formatDate(dateString: string | null): string {
    if (!dateString) return 'Data non disponibile';
    const date = new Date(dateString);
    return date.toLocaleDateString("it-IT", {
      day: '2-digit',
      month: 'long',
      year: 'numeric'
    });
  }

  private calculateDaysAgo(dateString: string | null): number {
    if (!dateString) return 0;
    const date = new Date(dateString);
    const today = new Date();
    const diffTime = Math.abs(today.getTime() - date.getTime());
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  }

  private initializeChart(): void {
    if (this.chartCanvas && this.chartCanvas.nativeElement) {
      const ctx = this.chartCanvas.nativeElement.getContext('2d');
      if (ctx) {
        const chart = new Chart(ctx, {
          type: 'bar', // O qualsiasi altro tipo di grafico
          data: {
            labels: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile'], // Esempio di etichette
            datasets: [{
              label: 'Esempio di Dati',
              data: [10, 20, 30, 40], // Esempio di dati
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)'
              ],
              borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)'
              ],
              borderWidth: 1
            }]
          },
          options: {
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });
      }
    }
  }
}
