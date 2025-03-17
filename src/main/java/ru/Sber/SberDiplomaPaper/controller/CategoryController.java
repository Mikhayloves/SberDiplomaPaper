package ru.Sber.SberDiplomaPaper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.Sber.SberDiplomaPaper.domain.dto.category.CategoryCreationDto;
import ru.Sber.SberDiplomaPaper.domain.dto.category.CategoryDto;
import ru.Sber.SberDiplomaPaper.domain.model.Category;
import ru.Sber.SberDiplomaPaper.domain.util.DtoConverter;
import ru.Sber.SberDiplomaPaper.service.category.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    public static final String CREATE_CATEGORY = "/create";
    public static final String EDIT_CATEGORY = "/edit";
    public static final String DELETE_CATEGORY = "/delete/{id}";
    public static final String LIST_CATEGORY = "/list";
    public static final String GET_CATEGORY = "/{id}";
    private final CategoryServiceImpl categoryServiceImpl;
    private final DtoConverter dtoConverter;

    @GetMapping(LIST_CATEGORY)
    public List<CategoryDto> getAllCategories() {
        return dtoConverter.toDto(categoryServiceImpl.findAll(), CategoryDto.class);
    }

    @GetMapping(GET_CATEGORY)
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return dtoConverter.toDto(categoryServiceImpl.getById(id), CategoryDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(CREATE_CATEGORY)
    public CategoryDto createCategory(@RequestBody CategoryCreationDto category) {
        Category model = dtoConverter.toModel(category, Category.class);
        return dtoConverter.toDto(categoryServiceImpl.save(model), CategoryDto.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(DELETE_CATEGORY)
    public void deleteCategory(@PathVariable Long id) {
        categoryServiceImpl.deleteById(id);
    }

    @PutMapping(EDIT_CATEGORY)
    public CategoryDto updateCategory(@RequestBody CategoryCreationDto category) {
        Category model = dtoConverter.toModel(category, Category.class);
        return dtoConverter.toDto(categoryServiceImpl.save(model), CategoryDto.class);
    }
}



