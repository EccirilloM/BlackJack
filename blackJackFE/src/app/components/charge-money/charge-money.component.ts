import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { debounceTime } from 'rxjs';
import { MapService } from 'src/app/services/map.service';

@Component({
  selector: 'app-charge-money',
  templateUrl: './charge-money.component.html',
  styleUrls: ['./charge-money.component.css']
})
export class ChargeMoneyComponent implements OnInit, AfterViewInit {

  public searchResults: any[] = [];

  location: string = "Roma";


  private map: any;

  private initMap(): void {
    this.map = L.map('map', {
      center: [39.8282, -98.5795],
      zoom: 3
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);
  }

  constructor(private mapService: MapService) {
  }

  ngAfterViewInit() {
    this.initMap();
  }

  ngOnInit(): void {
    console.log('ChargeMoneyComponent');
  }

  //NOMINATIM
  searchNominatim(query: string) {
    if (query.length < 3) {
      this.searchResults = [];
      return;
    }

    //LASCIA COSì il debounce Fidati
    this.mapService.searchNominatimLocation(query).pipe(debounceTime(5000)).subscribe({
      next: (results) => {
        this.searchResults = results;
      },
      error: (error) => {
        console.error('Errore nella ricerca:', error);
        this.searchResults = [];
      }
    });
  }

  centerMapOnResult(result: any): void {
    const lat = parseFloat(result.lat);
    const lon = parseFloat(result.lon);
    this.map.flyTo([lat, lon], 15);
  }

}
