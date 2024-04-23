import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { debounceTime } from 'rxjs';
import { MapService } from 'src/app/services/map.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-charge-money',
  templateUrl: './charge-money.component.html',
  styleUrls: ['./charge-money.component.css']
})
export class ChargeMoneyComponent implements OnInit, AfterViewInit {

  public searchResults: any[] = [];

  location: string = "Roma";

  private map: any;

  constructor(private mapService: MapService, private userService: UserService) {
  }

  ngAfterViewInit() {
    this.map = this.mapService.initMap(this.map);
  }

  ngOnInit(): void {
    console.log('ChargeMoneyComponent');
  }

  //NOMINATIM SECTION --------------------------------------------------------------
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

  // FINE COMPONENTE 

}
