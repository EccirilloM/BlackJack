import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GetAllMessagesByTipoTavoloResponse } from 'src/app/dto/response/GetAllMessagesByTipoTavoloResponse';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { ForumService } from 'src/app/services/forum.service';
import { Tavolo } from 'src/app/types/tavolo';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {
  tipoTavolo: Tavolo | null = null;
  messaggi: GetAllMessagesByTipoTavoloResponse[] = [];
  testoMessaggio: string = '';


  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(
    private forumService: ForumService,
    private route: ActivatedRoute
  ) { }

  // NGONINIT ----------------------------------------------------------------------------
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tipoTavolo = params['tipoTavolo'];
      this.loadMessages();
    });
    // this.loadMessages();
  }

  // METODI PER I TAVOLI ----------------------------------------------------------------------------
  getTavoloImage(tipoTavolo: Tavolo | null): string {
    if (!tipoTavolo) return ''; // o un percorso di immagine predefinito
    return `assets/tables/tavolo${tipoTavolo}.png`;
  }

  // METODI PER I MESSAGGI ----------------------------------------------------------------------------
  inviaMessaggio(): void {
    console.log('Scrivi messaggio');
    this.forumService.inviaMessaggio(this.tipoTavolo?.toString(), this.testoMessaggio).subscribe({
      next: (response: MessageResponse) => {
        console.log(response);
        this.loadMessages();
      },
      error: (error: any) => {
        console.error('Error while sending message: ', error);
      }
    });
  }

  loadMessages(): void {
    console.log('Carico i messaggi del tavolo');
    this.forumService.getAllMessagesByTipoTavolo(this.tipoTavolo?.toString()).subscribe({
      next: (response: GetAllMessagesByTipoTavoloResponse[]) => {
        console.log(response);
        this.messaggi = response;
      },
      error: (error: any) => {
        console.error('Error while fetching messages: ', error);
      }
    });
  }

}

