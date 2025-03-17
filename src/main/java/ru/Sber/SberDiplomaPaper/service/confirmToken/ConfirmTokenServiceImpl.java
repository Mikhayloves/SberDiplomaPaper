package ru.Sber.SberDiplomaPaper.service.confirmToken;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.exception.ResourceNotFoundException;
import ru.Sber.SberDiplomaPaper.domain.model.ConfirmToken;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.repository.ConfirmTokenRepository;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {
    private final ConfirmTokenRepository confirmTokenRepository;

    @Override
    public ConfirmToken generateToken(User user) {
        ConfirmToken confirmToken = new ConfirmToken();
        confirmToken.setUser(user);
        return confirmTokenRepository.save(confirmToken);
    }

    @Override
    public ConfirmToken getConfirmToken(UUID id) {
        return confirmTokenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Confirm token not found"));
    }

    @Override
    public void deleteTokensByUser(User user) {
        confirmTokenRepository.deleteAllByUser(user);
    }
}
