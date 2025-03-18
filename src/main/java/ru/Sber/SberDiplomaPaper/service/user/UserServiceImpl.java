package ru.Sber.SberDiplomaPaper.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.exception.ResourceNotFoundException;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(""));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public void updateEnabled(Long id, boolean enabled) {
        userRepository.updateEnabledById(id, enabled);
    }
}
