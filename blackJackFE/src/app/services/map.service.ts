import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import * as L from 'leaflet';
import { GetAllTabacchiResponse } from '../dto/response/getAllTabacchiResponse';
import { globalBackendUrl } from 'environment';
//configurazione dell'immagine del marker
const iconUrl = 'assets/marker/marker_icon.png';
const iconDefault = L.icon({
  iconUrl,
  iconSize: [40, 40],
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
  private mapCreaTabacchi: any;
  private mapRicaricaDenaro: any;

  public lat: number = 0;
  public lng: number = 0;
  private markersInMap: number = 0;

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
    mapCreaTabacchi.on('click', (e: any) => {
      this.lat = e.latlng.lat;
      this.lng = e.latlng.lng;
      if (!this.markersInMap) {
        this.markersInMap++;
      } else {
        mapCreaTabacchi.eachLayer((layer: any) => {
          if (layer instanceof L.Marker) {
            mapCreaTabacchi.removeLayer(layer);
          }

        });

      }
      L.marker([this.lat, this.lng]).addTo(mapCreaTabacchi);
    });

    this.mapCreaTabacchi = mapCreaTabacchi;
    return mapCreaTabacchi;
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
