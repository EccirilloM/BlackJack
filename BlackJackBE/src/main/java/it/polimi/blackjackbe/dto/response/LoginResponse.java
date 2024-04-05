package it.polimi.blackjackbe.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private long id;
    private String ruolo;
}
