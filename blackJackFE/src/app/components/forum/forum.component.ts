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

  constructor(
    private forumService: ForumService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tipoTavolo = params['tipoTavolo'];
      this.forumService.loadMessages(this.tipoTavolo as Tavolo);
    });
  }

  getTavoloImage(tipoTavolo: Tavolo | null): string {
    if (!tipoTavolo) return ''; // o un percorso di immagine predefinito
    return `assets/tables/tavolo${tipoTavolo}.png`;
  }
}

