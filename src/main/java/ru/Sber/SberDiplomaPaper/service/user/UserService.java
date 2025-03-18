package ru.Sber.SberDiplomaPaper.service.user;

import ru.Sber.SberDiplomaPaper.domain.model.User;

public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    User save(User user);


    void updateEnabled(Long id, boolean enabled);

}
