import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import * as L from 'leaflet';
import { GetAllTabacchiResponse } from '../dto/response/getAllTabacchiResponse';
import { globalBackendUrl } from 'environment';
import { MessageResponse } from '../dto/response/messageResponse';
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
  private backendUrl: string = globalBackendUrl + 'tabacchi/';
  public nomeTabacchiSelezionato: string = '';

  private mapCreaTabacchi: any;
  private mapRicaricaDenaro: any;

  latMarkerSelezionato: number = 0;
  lngMarkerSelezionato: number = 0;

  public lat: number = 0;
  public lng: number = 0;
  tabacchi: GetAllTabacchiResponse[] = [];


  constructor(private http: HttpClient) { }

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
      });
    });
    this.mapRicaricaDenaro = mapRicaricaDenaro;
  }


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

  eliminaTabacchiById(tabacchiId: string): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(this.backendUrl + 'eliminaTabacchi/' + tabacchiId, { headers: this.getHeader() });
  }

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
