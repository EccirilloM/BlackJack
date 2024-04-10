package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserData(Long userId);

    void deleteUser(Long userId);

    List<UserResponse> getAllByRuolo(String ruolo);

    void ricaricaSaldo();
}
