import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { ToastrService } from 'ngx-toastr';
import { debounceTime } from 'rxjs';
import { GetAllTabacchiResponse } from 'src/app/dto/response/GetAllTabacchiResponse';
import { MapService } from 'src/app/services/map.service';
import { RicaricaService } from 'src/app/services/ricarica.service';
import { TabacchiService } from 'src/app/services/tabacchi.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-charge-money',
  templateUrl: './charge-money.component.html',
  styleUrls: ['./charge-money.component.css']
})
export class ChargeMoneyComponent implements OnInit, AfterViewInit {

  // VARIABILE PER NOMINATIM ----------------------------------------------------------------------------
  public searchResults: any[] = [];
  // VARIABILI PER LA MAPPA ----------------------------------------------------------------------------
  private mapRicaricaDenaro: any;
  tabacchi: GetAllTabacchiResponse[] = [];
  // VARIABILI PER IL TABACCHI SELEZIONATO ----------------------------------------------------------------------------
  //Queste 2 variabili devono avere il valore aggiornato della funzione nel service findTabacchiByCoordinates, fai qualcosa perché ora non mi funziona

  // VARIABILI PER LE COORDINATE DEL MARKER SELEZIONATO ----------------------------------------------------------------------------
  latMarkerSelezionato: number = this.mapService.latMarkerSelezionato;
  lngMarkerSelezionato: number = this.mapService.lngMarkerSelezionato;
  // VARIABILE PER LA RICARICA DENARO ----------------------------------------------------------------------------
  importo: number = 0;

  // TODO: implementare la funzione che data latitudine e longitudine, ritorna il nome e l'id del tabacchi

  //COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private mapService: MapService, private userService: UserService, private tabacchiService: TabacchiService, private toastr: ToastrService, private ricaricaService: RicaricaService) {
  }

  //NGONINIT E AFTERVIEWINIT ----------------------------------------------------------------------------
  ngAfterViewInit() {
    this.mapRicaricaDenaro = this.mapService.initMap(this.mapRicaricaDenaro);
  }

  ngOnInit(): void {
    console.log('ChargeMoneyComponent');
    this.tabacchiService.getAllTabacchi().subscribe({
      next: (response: GetAllTabacchiResponse[]) => {
        console.log(response);
        this.tabacchi = response;
        this.mapService.placeTabacchiMarkersChargeMoney(response, this.mapRicaricaDenaro);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching users: ', error);
        this.toastr.error('Error while fetching users');
      }
    });
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
    this.mapRicaricaDenaro.flyTo([lat, lon], 15);
  }

  // METODI PER MANDARE LA RICHIESTA DI RICARICA DENARO --------------------------------------------------------------
  mandaRichiestaRicaricaDenaro() {
    this.mapService.richiediRicaricaDenaro(this.importo).subscribe({
      next: (response: any) => {
        console.log(response);
        this.toastr.success('Richiesta effettuata con successo');
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching users: ', error);
        this.toastr.error('Error while fetching users');
      }
    });

  }

  // METODI CHE DATA UNA LATITUDINE E LONGITUDINE, RITORNA IL NOME E L'ID DEL TABACCHI --------------------------------
  // findTabacchiByCoordinates(lat: number, lng: number): GetAllTabacchiResponse {
  //   console.log(`Cerco tabacchi con lat: ${lat} e lng: ${lng}`);
  //   const foundTabacchi = this.tabacchi.find(tabacchi =>
  //     tabacchi.lat == lat && tabacchi.lng == lng
  //   );

  //   if (foundTabacchi) {
  //     this.tabacchiIdSelezionato = foundTabacchi.tabacchiId;
  //     this.tabacchiNomeSelezionato = foundTabacchi.nomeTabacchi;
  //     console.log(`Trovato tabacchi: ${foundTabacchi.nomeTabacchi} con ID: ${foundTabacchi.tabacchiId}`);
  //     return foundTabacchi;
  //   } else {
  //     console.log('Nessun tabacchi trovato con queste coordinate.');
  //     this.toastr.error('Nessun tabacchi trovato con queste coordinate.');
  //     return {} as GetAllTabacchiResponse;
  //   }
  // }
  // FINE COMPONENTE ----------------------------------------------------------------------------
}
