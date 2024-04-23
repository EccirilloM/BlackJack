import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ForumService } from 'src/app/services/forum.service';
import { Tavolo } from 'src/app/types/tavolo';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {
  tipoTavolo: Tavolo | null = null;

  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(
    private forumService: ForumService,
    private route: ActivatedRoute
  ) { }

  // NGONINIT ----------------------------------------------------------------------------
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tipoTavolo = params['tipoTavolo'];
      this.forumService.loadMessages(this.tipoTavolo as Tavolo);
    });
  }

  // METODI PER I TAVOLI ----------------------------------------------------------------------------
  getTavoloImage(tipoTavolo: Tavolo | null): string {
    if (!tipoTavolo) return ''; // o un percorso di immagine predefinito
    return `assets/tables/tavolo${tipoTavolo}.png`;
  }

  // METODI PER I MESSAGGI ----------------------------------------------------------------------------
  scriviMessaggio(): void {
    console.log('Scrivi messaggio');
  }

  loadMessages(): void {
    console.log('Carico i messaggi del tavolo');
  }

}

