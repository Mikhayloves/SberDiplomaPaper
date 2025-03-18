package ru.Sber.SberDiplomaPaper.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Sber.SberDiplomaPaper.domain.exception.ResourceNotFoundException;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.repository.UserRepository;
import ru.Sber.SberDiplomaPaper.service.user.UserServiceImpl;
import util.DataUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Проверка создания пользователя")
    public  void givenUserToSave_whenSaveUser_thenRepositorySaveIsCalled(){

        //given
        User userToSave = DataUtils.getAntonByTransient();
        BDDMockito.given(userRepository.save(any(User.class)))
                .willReturn(DataUtils.getAntonByPersisted());
        //when
        User saveUser = userService.save(userToSave);
        //then
        assertThat(saveUser.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("Тест получения пользователя по ID")
    public void givenUserId_whenGetById_thenUserIsReturned() {
        // given
        User user = DataUtils.getAntonByPersisted();
        BDDMockito.given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        // when
        User result = userService.getById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Тест получения пользователя по несуществующему ID")
    public void givenNonExistentUserId_whenGetById_thenExceptionIsThrown() {
        // given
        BDDMockito.given(userRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> userService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("");
    }

    @Test
    @DisplayName("Тест получения пользователя по email")
    public void givenUserEmail_whenGetByEmail_thenUserIsReturned() {
        // given
        User user = DataUtils.getAntonByPersisted();
        BDDMockito.given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));

        // when
        User result = userService.getByEmail("anton@example.com");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Тест получения пользователя по несуществующему email")
    public void givenNonExistentEmail_whenGetByEmail_thenExceptionIsThrown() {
        // given
        BDDMockito.given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> userService.getByEmail("nonexistent@example.com"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("");
    }

    @Test
    @DisplayName("Тест сохранения пользователя")
    public void givenUserToSave_whenSaveUser_thenUserIsSaved() {
        // given
        User userToSave = DataUtils.getAntonByTransient();
        User savedUser = DataUtils.getAntonByPersisted();
        BDDMockito.given(userRepository.save(any(User.class)))
                .willReturn(savedUser);

        // when
        User result = userService.save(userToSave);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("Тест обновления статуса enabled пользователя")
    public void givenUserIdAndEnabledStatus_whenUpdateEnabled_thenRepositoryMethodIsCalled() {
        // given
        Long userId = 1L;
        boolean enabled = true;

        // when
        userService.updateEnabled(userId, enabled);

        // then
        BDDMockito.then(userRepository).should().updateEnabledById(userId, enabled);
    }
}
