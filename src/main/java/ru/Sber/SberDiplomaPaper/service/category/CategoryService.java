package ru.Sber.SberDiplomaPaper.service.category;

import ru.Sber.SberDiplomaPaper.domain.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category getById(Long id);

    Category save(Category category);

    void deleteById(Long id);


}
