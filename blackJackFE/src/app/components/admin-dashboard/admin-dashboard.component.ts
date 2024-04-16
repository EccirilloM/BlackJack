import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  @ViewChild('usersChart') usersChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('commercesChart') commercesChartRef!: ElementRef<HTMLCanvasElement>;
  income: number = 5000;
  numberOfUsers: number = 95;

  constructor() {
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    console.log('Admin Dashboard initialized');
  }

  ngAfterViewInit(): void {
    this.initializeCharts();
  }

  private initializeCharts(): void {
    this.initializeUsersChart();
    this.initializeCommercesChart();
  }

  private initializeUsersChart(): void {
    const ctx = this.usersChartRef.nativeElement.getContext('2d');
    if (ctx) {
      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Nuevos', 'Registrados'],
          datasets: [{
            data: [30, 65],
            backgroundColor: ['#00F0FF', '#8B8B8D'],
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'bottom'
            }
          }
        }
      });
    }
  }

  private initializeCommercesChart(): void {
    const ctx = this.commercesChartRef.nativeElement.getContext('2d');
    if (ctx) {
      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Nuevos', 'Registrados'],
          datasets: [{
            data: [60, 40],
            backgroundColor: ['#FEC500', '#8B8B8D'],
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'bottom'
            }
          }
        }
      });
    }
  }
}

