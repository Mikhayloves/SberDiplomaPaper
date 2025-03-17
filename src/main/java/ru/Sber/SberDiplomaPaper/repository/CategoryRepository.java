package ru.Sber.SberDiplomaPaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Sber.SberDiplomaPaper.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
