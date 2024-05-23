import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';
import { GetUserDataResponse } from 'src/app/dto/response/GetUserDataResponse';
import { HttpErrorResponse } from '@angular/common/http';
import { RegistrazioneRequest } from 'src/app/dto/request/RegistrazioneRequest';
import { MessageResponse } from 'src/app/dto/response/MessageResponse';
import { MapService } from 'src/app/services/map.service';
import { debounceTime } from 'rxjs';
import { TabacchiService } from 'src/app/services/tabacchi.service';
import { GetAllTabacchiResponse } from 'src/app/dto/response/GetAllTabacchiResponse';
import { getAllManiResponse } from 'src/app/dto/response/GetAllManiResponse';
import { ManoService } from 'src/app/services/mano.service';
import * as d3 from 'd3';
/**
 * Componente per visualizzare la dashboard dell'admin.
 * implementa OnInit, un'interfaccia che espone il metodo ngOnInit() il quale 
 * viene chiamato automaticamente quando il componente viene caricato.
 */
@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit, AfterViewInit {
  // VARIABILI PER I GRAFICI ----------------------------------------------------------------------------
  @ViewChild('usersChart', { static: true }) private usersChartContainer!: ElementRef;
  @ViewChild('commercesChart', { static: true }) private commercesChartContainer!: ElementRef;
  // VARIABILI DATI ADMIN ----------------------------------------------------------------------------
  numberOfUsers: number = 0;
  utenti: GetUserDataResponse[] = [];
  public searchResults: any[] = [];
  private mapCreaTabacchi: any;
  saldoString: string = localStorage.getItem('saldo') || '0';
  saldo: number = parseFloat(this.saldoString);
  // VARIABILI PER Creare Economo Oppure Modificare Dati di un User----------------------------------------------------------------------------
  nome = '';
  cognome = '';
  email = '';
  username = '';
  password = '';
  passwordRipetuta = '';
  dataNascita = new Date();
  showPassword = false;
  showRepeatPassword = false;
  economi: GetUserDataResponse[] = [];
  // VARIABILI PER Tabacchi ----------------------------------------------------------------------------
  tabacchi: GetAllTabacchiResponse[] = [];
  // VARIABILI PER Creare Tabacchi ----------------------------------------------------------------------------
  nomeTabacchi: string = '';
  economoSelezionatoId: number = 0;
  showEditDataUserByAdmin: boolean = false
  idSelected: number = 0;
  //VARIABILI PER LE MANI ----------------------------------------------------------------------------
  mani: getAllManiResponse[] = [];
  //VARIABILI PER IL CURRENT USER ----------------------------------------------------------------------------
  currentUser!: GetUserDataResponse;
  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor(private userService: UserService,
    private toastr: ToastrService,
    private mapService: MapService,
    private tabacchiService: TabacchiService,
    private manoService: ManoService) {
  }

  // NGONINIT E AFTERVIEWINIT ----------------------------------------------------------------------------
  ngOnInit(): void {
    console.log('Admin Dashboard initialized');
    this.loadUsers();
    this.loadAllEconomi();
    this.loadAllTabacchi();
    this.loadAllMani();  // Carica i dati e inizializza i grafici dopo il caricamento
  }

  ngAfterViewInit(): void {
    this.mapCreaTabacchi = this.mapService.initMapCreaTabacchi(this.mapCreaTabacchi);
    this.initializeCharts();
  }



  // METODI PER PRENDERE LA LATITUDINE E LONGITUDINE DEL MARKER ----------------------------------------------------------------------------
  latMarker(): number {
    return this.mapService.lat;
  }

  lngMarker(): number {
    return this.mapService.lng;
  }

  // METODI PER Modificare i dati di un utente ----------------------------------------------------------------------------
  adminEditUserData() {
    this.userService.adminAggiornaDatiUtente(this.idSelected, this.nome, this.cognome, this.email, this.username)
      .subscribe({
        next: (res: GetUserDataResponse) => {
          this.toastr.success("Dati modificati con successo");
          this.showEditDataUserByAdmin = false;
        },
        error: (err: HttpErrorResponse) => {
          this.toastr.error(err.error.message);
        }
      });
  }

  toggleModalEditUserData(id: number) {
    this.showEditDataUserByAdmin = !this.showEditDataUserByAdmin;
    this.idSelected = id;
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
    this.mapCreaTabacchi.flyTo([lat, lon], 15);
  }

  // FUNZIONI PER CREARE ECONOMO ----------------------------------------------------------------------------
  creaEconomo(): void {
    // Validazione semplice. Potresti voler aggiungere validazioni più specifiche
    if (!this.nome || !this.cognome || !this.email || !this.username || !this.password || !this.passwordRipetuta || !this.dataNascita) {
      this.toastr.error("Compilare tutti i campi");
      return;
    }

    if (this.password !== this.passwordRipetuta) {
      this.toastr.error("Le password non coincidono");
      return;
    }

    const request: RegistrazioneRequest = {
      nome: this.nome,
      cognome: this.cognome,
      email: this.email,
      username: this.username,
      password: this.password,
      dataNascita: new Date(this.dataNascita)
    };

    this.userService.creaEconomo(request).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.nome = '';
        this.cognome = '';
        this.email = '';
        this.username = '';
        this.password = '';
        this.passwordRipetuta = '';
        this.dataNascita = new Date();
        this.toastr.success("Economo creato con successo");
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la registration');
      }
    });
  }

  // FUNZIONI PER CREARE TABACCHI ----------------------------------------------------------------------------
  creaTabacchi(): void {
    this.tabacchiService.creaTabacchi(this.nomeTabacchi, this.latMarker(), this.lngMarker(), this.economoSelezionatoId).subscribe({
      next: (res: MessageResponse) => {
        this.toastr.success(res.message);
        this.nomeTabacchi = '';
        this.tabacchi.push({ nomeTabacchi: this.nomeTabacchi, lat: this.latMarker(), lng: this.lngMarker(), userId: this.economoSelezionatoId, tabacchiId: 0 });
        this.mapService.placeTabacchiMarkers(this.tabacchi, this.mapCreaTabacchi);
        this.toastr.success("Tabacchi creato con successo");
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.toastr.error(err.error.message || 'Errore durante la creazione del tabacchi');
      }
    });
  }

  // FUNZIONE PER CARICARE GLI UTENTI ----------------------------------------------------------------------------
  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (response: GetUserDataResponse[]) => {
        console.log(response);
        this.utenti = response;
        this.numberOfUsers = this.utenti.length;
      },
      error: (error: HttpErrorResponse) => {
        this.toastr.error('Error while fetching users');
      }
    });
  }

  // Funzione per caricare tutti gli Economi ----------------------------------------------------------------------------
  loadAllEconomi(): void {
    this.userService.getAllByRuolo('ECONOMO').subscribe({
      next: (response: GetUserDataResponse[]) => {
        this.economi = response;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching Economi: ', error);
        this.toastr.error('Error while fetching Economi');
      }
    });

  }

  // Funzione per caricare tutti i Tabacchi ----------------------------------------------------------------------------
  loadAllTabacchi(): void {
    this.tabacchiService.getAllTabacchi().subscribe({
      next: (response: GetAllTabacchiResponse[]) => {
        console.log(response);
        this.tabacchi = response;
        this.mapService.placeTabacchiMarkers(response, this.mapCreaTabacchi);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching tabacchi: ', error);
        this.toastr.error('Error while fetching tabacchi');
      }
    });
  }

  // FUNZIONE PER ELIMINARE UN UTENTE ----------------------------------------------------------------------------
  deleteUser(userId: number): void {
    console.log('Eliminazione utente con id: ', userId);
    this.userService.deleteUser(userId).subscribe({
      next: (response: MessageResponse) => {
        console.log(response);
        this.loadUsers();
        this.toastr.success("Utente eliminato con successo");
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while deleting user: ', error);
        this.toastr.error('Error while deleting user');
      }
    });
  }

  // FUNZIONE PER CARICARE LE MANI ----------------------------------------------------------------------------
  loadAllMani(): void {
    this.manoService.getAllMani().subscribe({
      next: (response: getAllManiResponse[]) => {
        this.mani = response;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error while fetching hands: ', error);
        this.toastr.error('Error while fetching hands');
      }
    });
  }

  private initializeCharts(): void {
    // Assicurati che il contenitore sia disponibile e visibile
    if (this.usersChartContainer.nativeElement && this.commercesChartContainer.nativeElement) {
      setTimeout(() => {
        this.createPieChart(this.usersChartContainer.nativeElement, this.calculateWinLossData());
        this.createPieChart(this.commercesChartContainer.nativeElement, this.calculateSessionDurationData());
      }, 500); // Potrebbe essere necessario un ritardo per assicurare che il DOM sia pronto
    }
  }


  private calculateWinLossData(): { label: string; value: number }[] {
    const winnings = this.mani.filter(m => m.importo > 0).reduce((acc, curr) => acc + curr.importo, 0);
    const losses = this.mani.filter(m => m.importo < 0).reduce((acc, curr) => acc + Math.abs(curr.importo), 0);
    return [
      { label: 'Casino Wins', value: winnings },
      { label: 'Casino Losses', value: losses }
    ];
  }

  private calculateSessionDurationData(): { label: string; value: number }[] {
    let sessionsOver10Minutes = 0;
    let totalSessions = 0;
    const handsByUser = this.groupAndSortHandsByUser();

    Object.values(handsByUser).forEach(hands => {
      let sessionStart = hands[0];
      totalSessions++;
      for (let i = 1; i < hands.length; i++) {
        const timeDiff = (new Date(hands[i].dataMano).getTime() - new Date(sessionStart.dataMano).getTime()) / 60000;
        if (timeDiff > 10) {
          sessionsOver10Minutes++;
          sessionStart = hands[i]; // Start a new session
          totalSessions++;
        }
      }
    });

    return [
      { label: 'Sessions Over 10 Minutes', value: sessionsOver10Minutes },
      { label: 'Other Sessions', value: totalSessions - sessionsOver10Minutes }
    ];
  }

  private groupAndSortHandsByUser(): { [username: string]: getAllManiResponse[] } {
    return this.mani.reduce((acc, curr) => {
      acc[curr.playerUsername] = acc[curr.playerUsername] || [];
      acc[curr.playerUsername].push(curr);
      return acc;
    }, {} as { [username: string]: getAllManiResponse[] });
  }

  // Assumi che d3Tip sia già importato correttamente come indicato
  private createPieChart(container: HTMLElement, data: { label: string; value: number }[]): void {
    const width = 360;
    const height = 360;
    const radius = Math.min(width, height) / 2;
    const color = d3.scaleOrdinal(d3.schemeCategory10);
    const tooltip = d3.select('#tooltip');

    const svg = d3.select(container).append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', `translate(${width / 2}, ${height / 2})`);

    const pie = d3.pie<{ label: string, value: number }>().value(d => d.value);
    const path = d3.arc<d3.PieArcDatum<{ label: string; value: number }>>()
      .outerRadius(radius - 10)
      .innerRadius(0);

    const arcs = svg.selectAll('.arc')
      .data(pie(data))
      .enter().append('g')
      .attr('class', 'arc')
      .on('mouseover', function (event, d) {
        tooltip.transition().duration(200).style('opacity', 1);
        tooltip.html(`<strong>Value:</strong> <span style='color:red'>${d.data.value}</span>`)
          .style('left', (event.pageX + 10) + 'px')
          .style('top', (event.pageY - 10) + 'px');
      })
      .on('mouseout', function () {
        tooltip.transition().duration(500).style('opacity', 0);
      });

    arcs.append('path')
      .attr('d', path)
      .attr('fill', (d, i) => color(i.toString()))
      .transition()
      .duration(1000)
      .attrTween('d', function (d) {
        const interpolate = d3.interpolate(d.startAngle + 0.1, d.endAngle);
        return function (t) {
          d.endAngle = interpolate(t);
          return path(d) || "";
        };
      });

    arcs.append('text')
      .attr('transform', d => `translate(${path.centroid(d)})`)
      .attr('dy', '0.35em')
      .style('text-anchor', 'middle')
      .text(d => `${d.data.label}: ${d.data.value}`);
  }
  // FINE COMPONETE ----------------------------------------------------------------------------
}
