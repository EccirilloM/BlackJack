import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import * as L from 'leaflet';
import { GetAllTabacchiResponse } from '../dto/response/getAllTabacchiResponse';
import { globalBackendUrl } from 'environment';
import { MessageResponse } from '../dto/response/messageResponse';
import { ToastrService } from 'ngx-toastr';
import { RicaricaSaldoRequest } from '../dto/request/ricaricaSaldoRequest';
//configurazione dell'immagine del marker
const iconUrl = 'assets/marker/marker-icon.png';
const iconDefault = L.icon({
  iconUrl,
  iconSize: [25, 36],
  iconAnchor: [12, 41],
  shadowUrl: '',
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});

L.Marker.prototype.options.icon = iconDefault;

@Injectable({
  providedIn: 'root'
})
export class MapService {
  // Variabile che prende il valore dell'url globale e gli aggiunge IL tabacchi -------------------------------------
  private backendUrl: string = globalBackendUrl + 'tabacchi/';

  // VARIABILI PER IL CURRENT TABACCHI --------------------------------------------------------------------------------
  latMarkerSelezionato: number = 0;
  lngMarkerSelezionato: number = 0;

  // VARIABILI PER LE MAPPE -------------------------------------------------------------------------------------------
  private mapCreaTabacchi: any;
  private mapRicaricaDenaro: any;

  // VARIABILI PER LE COORDINATE --------------------------------------------------------------------------------------
  public lat: number = 0;
  public lng: number = 0;
  tabacchi: GetAllTabacchiResponse[] = [];

  // VARIABILI PER IL TABACCHI SELEZIONATO -----------------------------------------------------------------------------
  public tabacchiNomeSelezionato!: string;
  public tabacchiIdSelezionato!: number;
  public foundTabacchi!: GetAllTabacchiResponse | undefined;

  // COSTRUTTORE --------------------------------------------------------------------------------------------------------
  constructor(private http: HttpClient, private toastr: ToastrService) { }

  // CHIAMATE AL BACKEND PER IL TABACCHI -----------------------------------------------------------------------------
  // METODO PER ELIMINARE IL TABACCHI SELEZIONATO
  eliminaTabacchiById(tabacchiId: string): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(this.backendUrl + 'eliminaTabacchi/' + tabacchiId, { headers: this.getHeader() });
  }

  // METODI PER LA CREAZIONE DELLA MAPPA PER RICARICARE DENARO  -----------------------------------------------------------------------------
  initMap(mapRicaricaDenaro: any): any {
    mapRicaricaDenaro = L.map('mapRicaricaDenaro', {
      center: [41.9027835, 12.4963655],
      zoom: 10
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(mapRicaricaDenaro);

    this.mapRicaricaDenaro = mapRicaricaDenaro;
    return mapRicaricaDenaro;
  }

  // METODI PER LA CREAZIONE DELLA MAPPA PER CREARE TABACCHI  -----------------------------------------------------------------------------
  initMapCreaTabacchi(mapCreaTabacchi: any): any {
    mapCreaTabacchi = L.map('mapCreaTabacchi', {
      center: [41.9027835, 12.4963655],
      zoom: 10
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(mapCreaTabacchi);

    mapCreaTabacchi.eachLayer((layer: any) => {
      if (layer instanceof L.Marker) {
        mapCreaTabacchi.removeLayer(layer);
      }

    });
    mapCreaTabacchi.on('click', (e: any) => {
      this.lat = e.latlng.lat;
      this.lng = e.latlng.lng;

      L.marker([this.lat, this.lng]).addTo(mapCreaTabacchi);
    });

    mapCreaTabacchi.on('popupopen', (e: any) => {
      this.handleEliminaTabacchi(e);

    });

    this.mapCreaTabacchi = mapCreaTabacchi;
    return mapCreaTabacchi;
  }

  // METODI PER CARICARE I MARKER DEI TABACCHI IN CHARGE MONEY -----------------------------------------------------------------------------
  placeTabacchiMarkersChargeMoney(tabacchi: GetAllTabacchiResponse[], mapRicaricaDenaro: any): void {
    mapRicaricaDenaro.eachLayer((layer: any) => {
      if (layer instanceof L.Marker) {
        mapRicaricaDenaro.removeLayer(layer);
      }

    });

    this.tabacchi = tabacchi;
    // Aggiungi un popup al marker con un pulsante
    tabacchi.forEach((tabacchi: GetAllTabacchiResponse) => {
      let popupContent: string = `
     <p id="nome-tabacchi">${tabacchi.nomeTabacchi}</p>
   `;
      L.marker([tabacchi.lat, tabacchi.lng]).addTo(mapRicaricaDenaro).bindPopup(popupContent).on('click', (e: any) => {
        this.latMarkerSelezionato = e.latlng.lat;
        this.lngMarkerSelezionato = e.latlng.lng;
        //TODO Chiamare la funzione
        this.findTabacchiByCoordinates(this.latMarkerSelezionato, this.lngMarkerSelezionato);
      });
    });
    this.mapRicaricaDenaro = mapRicaricaDenaro;
  }

  // METODI CHE DATA UNA LATITUDINE E LONGITUDINE, RITORNA IL NOME E L'ID DEL TABACCHI --------------------------------
  findTabacchiByCoordinates(lat: number, lng: number): void {
    console.log(`Cerco tabacchi con lat: ${lat} e lng: ${lng}`);
    this.foundTabacchi = this.tabacchi.find(tabacchi =>
      tabacchi.lat == lat && tabacchi.lng == lng
    );

    if (this.foundTabacchi) {
      this.tabacchiIdSelezionato = this.foundTabacchi.tabacchiId;
      this.tabacchiNomeSelezionato = this.foundTabacchi.nomeTabacchi;
      console.log(`Trovato tabacchi: ${this.foundTabacchi.nomeTabacchi} con ID: ${this.foundTabacchi.tabacchiId}`);
    } else {
      console.log('Nessun tabacchi trovato con queste coordinate.');
      this.toastr.error('Nessun tabacchi trovato con queste coordinate.');
    }
  }

  // CHIAMATA PER RICHIEDERE LA RICARICA DEL DENARO
  richiediRicaricaDenaro(importo: number): Observable<MessageResponse> {
    const request: RicaricaSaldoRequest = { tabacchiId: this.tabacchiIdSelezionato, importo: importo };
    console.log('Richiesta di ricarica: ', request);
    console.log(localStorage.getItem("id"));
    return this.http.post<MessageResponse>('http://localhost:8080/api/v1/ricarica/richiediRicarica/' + localStorage.getItem("id"), request, { headers: this.getHeader() });
  }

  // METODI PER CARICARE I MARKER DEI TABACCHI IN CREATE TABACCHI -----------------------------------------------------------------------------
  placeTabacchiMarkers(tabacchi: GetAllTabacchiResponse[], mapCreaTabacchi: any): void {
    mapCreaTabacchi.eachLayer((layer: any) => {
      if (layer instanceof L.Marker) {
        mapCreaTabacchi.removeLayer(layer);
      }

    });

    this.tabacchi = tabacchi;
    // Aggiungi un popup al marker con un pulsante
    tabacchi.forEach((tabacchi: GetAllTabacchiResponse) => {
      let popupContent: string = `
      <p>${tabacchi.nomeTabacchi}</p>
      <button id="elimina-tabacchi" name="${tabacchi.tabacchiId}" class="p-2.5 text-sm font-medium text-black rounded-lg border border-3 border-red-800">
        Elimina Tabacchi #${tabacchi.tabacchiId}
      </button>
    `;
      L.marker([tabacchi.lat, tabacchi.lng]).addTo(mapCreaTabacchi).bindPopup(popupContent);
    });
    this.mapCreaTabacchi = mapCreaTabacchi;
  }

  // METODO PER MOSTRARE IL NOME DEL TABACCHI SELEZIONATO -----------------------------------------------------------------------------
  handleMostraNome(e: any): void {
    const button = document.getElementById('nome-tabacchi');
    if (button) {
      button.addEventListener('click', (e) => {
        e.preventDefault();
        if (e.target) {
          const target = e.target as HTMLButtonElement;
          console.log(target.name);
        }
      });
    }
  }

  // METODO PER ELIMINARE IL TABACCHI SELEZIONATO LATO FE-----------------------------------------------------------------------------
  handleEliminaTabacchi(e: any): void {
    const button = document.getElementById('elimina-tabacchi');
    if (button) {
      button.addEventListener('click', (e) => {
        e.preventDefault();
        if (e.target) {
          const target = e.target as HTMLButtonElement;
          this.eliminaTabacchiById(target.name.toString()).subscribe({
            next: (response: MessageResponse) => {
              this.tabacchi = this.tabacchi.filter((tabacchi: GetAllTabacchiResponse) => tabacchi.tabacchiId.toString() !== target.name);
              this.placeTabacchiMarkers(this.tabacchi, this.mapCreaTabacchi);
            }, error: (error: any) => {
              console.error('Error while deleting tabacchi: ', error);
            }
          });
        }
      });
    }
  }

  // METODI PER NOMINATIM  -----------------------------------------------------------------------------
  private nominatimUrl = 'https://nominatim.openstreetmap.org';
  searchNominatimLocation(query: string): Observable<any> {
    const url = `${this.nominatimUrl}/search?format=json&q=${encodeURIComponent(query)}`;
    return this.http.get<any>(url).pipe(
      catchError(error => {
        console.error('Errore nella richiesta a Nominatim:', error);
        throw error;
      })
    );
  }

  //creo l'header con il token da mandare al backend
  private getHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': localStorage.getItem('token') ? `${localStorage.getItem('token')}` : '',
      id: localStorage.getItem('id') ? `${localStorage.getItem('id')}` : '',
      ruolo: localStorage.getItem('ruolo') ? `${localStorage.getItem('ruolo')}` : ''
    });
  }
}
