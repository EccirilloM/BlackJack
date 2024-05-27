package it.polimi.blackjackbe.unit.dto;

import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.*;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ResponseTest {

    @Test
    void testTabacchiResponse() {
        TabacchiResponse tabacchiResponse = new TabacchiResponse(1L, "Tabacchi", 45.0, 9.0, 1L);
        Long tabacchiId = tabacchiResponse.getTabacchiId();
        String nomeTabacchi = tabacchiResponse.getNomeTabacchi();
        Double lat = tabacchiResponse.getLat();
        Double lng = tabacchiResponse.getLng();
        Long userId = tabacchiResponse.getUserId();
    }

    @Test
    void testNotificaResponse() {
        NotificaResponse notificaResponse = new NotificaResponse(LocalDateTime.now(), "Messaggio");
        LocalDateTime data = notificaResponse.getData();
        String messaggio = notificaResponse.getMessaggio();
    }

    @Test
    void testCartaResponse() {
        CartaResponse cartaResponse = new CartaResponse("w", "w", 3, 3);
        String seme = cartaResponse.getSeme();
        String valore = cartaResponse.getValore();
        int punteggio = cartaResponse.getPunteggio();
        int order = cartaResponse.getOrder();
    }

    @Test
    void testMessageResponse() {
        MessageResponse messageResponse = new MessageResponse("Messaggio");
        String message = messageResponse.getMessage();
    }

    @Test
    void testLoginResponse() {
        LoginResponse response = new LoginResponse(1L, "Nome", "Cognome", "Email", "Username", null, "Message", "fc", 0.0, LocalDateTime.now(), LocalDateTime.now());
        Long userId = response.getUserId();
        String nome = response.getNome();
        String cognome = response.getCognome();
        String email = response.getEmail();
        String username = response.getUsername();
        Ruolo ruolo = response.getRuolo();
        String message = response.getMessage();
        String jwtToken = response.getJwtToken();
        Double saldo = response.getSaldo();
        LocalDateTime dataNascita = response.getDataNascita();
        LocalDateTime dataRegistrazione = response.getDataRegistrazione();
    }

    @Test
    void testGetAllRichiesteRicaricaSaldoResponse() {
        GetAllRichiesteRicaricaSaldoResponse response = new GetAllRichiesteRicaricaSaldoResponse(1L, 1L, "Nome", "Cognome", "Email", "Username", LocalDateTime.now(), 0.0);
        Long richiestaId = response.getRichiestaId();
        Long playerId = response.getPlayerId();
        String nome = response.getNome();
        String cognome = response.getCognome();
        String email = response.getEmail();
        String username = response.getUsername();
        LocalDateTime dataRichiesta = response.getDataRichiesta();
        Double importo = response.getImporto();
    }

    @Test
    void testGetAllMessagesByTipoTavoloResponse() {
        GetAllMessagesByTipoTavoloResponse response = new GetAllMessagesByTipoTavoloResponse("Messaggio", LocalDateTime.now(), "Username", "Ruolo");
        String testoMessaggio = response.getTestoMessaggio();
        LocalDateTime createdAt = response.getCreatedAt();
        String usernameMittente = response.getUsernameMittente();
        String ruoloMittente = response.getRuoloMittente();
    }

    @Test
    void testGetAllManiResponse() {
        GetAllManiResponse response = new GetAllManiResponse(1L, 1L, "Username", LocalDateTime.now(), 0.0);
        Long manoId = response.getManoId();
        Long tavoloId = response.getTavoloId();
        String playerUsername = response.getPlayerUsername();
        LocalDateTime dataMano = response.getDataMano();
        Double importo = response.getImporto();
    }

    @Test
    void testEndTavoloRequest() {
        EndTavoloRequest request = new EndTavoloRequest(0.0);
        Double plotUser = request.getPlotUser();
    }

    @Test
    void testTavoloStatusResponse() {
        TavoloStatusResponse t = new TavoloStatusResponse();
        TavoloStatusResponse response = new TavoloStatusResponse(null, 0, null, 0, null, 0.0, 0.0);
        List<CartaResponse> cartePlayer = response.getCartePlayer();
        int punteggioPlayer = response.getPunteggioPlayer();
        List<CartaResponse> carteDealer = response.getCarteDealer();
        int punteggioDealer = response.getPunteggioDealer();
        TavoloStatus tavoloStatus = response.getTavoloStatus();
        Double saldo = response.getSaldo();
        Double winning = response.getWinning();
    }

    @Test
    void testUserResponse() {
        UserResponse response = new UserResponse(1L, "Nome", "Cognome", "Email", "Username", null, LocalDateTime.now(), LocalDateTime.now(), 0.0);
        Long userId = response.getUserId();
        String nome = response.getNome();
        String cognome = response.getCognome();
        String email = response.getEmail();
        String username = response.getUsername();
        Ruolo ruolo = response.getRuolo();
        LocalDateTime dataNascita = response.getDataNascita();
        LocalDateTime dataRegistrazione = response.getDataRegistrazione();
        Double saldo = response.getSaldo();
    }
}
