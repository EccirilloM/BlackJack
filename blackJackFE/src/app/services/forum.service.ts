import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Tavolo } from '../types/tavolo';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private currentTavolo = new BehaviorSubject<Tavolo | null>(null);

  constructor() { }

  // Metodo per aggiornare il tipo di tavolo selezionato
  changeTavolo(tipoTavolo: Tavolo): void {
    this.currentTavolo.next(tipoTavolo);
  }

  // Metodo per ottenere l'Observable del tipo di tavolo attuale
  getCurrentTavolo(): Observable<Tavolo | null> {
    return this.currentTavolo.asObservable();
  }

  // Metodo fittizio per simulare il caricamento dei messaggi dal backend
  loadMessages(tipoTavolo: Tavolo): void {
    console.log(`Caricamento messaggi per il tavolo tipo: ${tipoTavolo}`);
    // Qui andrà la logica di chiamata al backend
  }
}

