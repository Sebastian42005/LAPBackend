package org.example.buchibackend.service;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.Category;
import org.example.buchibackend.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        Category dbCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dbCategory.setName(category.getName());
        dbCategory.setColor(category.getColor());
        dbCategory.setTransactions(category.getTransactions());
        return categoryRepository.save(dbCategory);
    }

    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        categoryRepository.delete(category);
    }
}
