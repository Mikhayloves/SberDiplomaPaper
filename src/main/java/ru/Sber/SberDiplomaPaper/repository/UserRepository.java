package ru.Sber.SberDiplomaPaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Sber.SberDiplomaPaper.domain.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
    Integer updateEnabledById(@Param("id") Long id, @Param("enabled") Boolean enabled);

    @Query("SELECT u.enabled FROM User u WHERE u.id = :id")
    Boolean getEnabledStatusById(@Param("id") Long id);
}
