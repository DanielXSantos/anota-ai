package com.desafio.anotaai.services;


import com.desafio.anotaai.domain.category.Category;
import com.desafio.anotaai.domain.category.CategoryDTO;
import com.desafio.anotaai.exceptions.CategoryNotFoundException;
import com.desafio.anotaai.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category insert(CategoryDTO dto) {
        Category category = new Category(dto);
        repository.save(category);
        return category;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category update(String id, CategoryDTO dto) {
        Category category = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!dto.title().isEmpty()) category.setTitle(dto.title());
        if(!dto.description().isEmpty()) category.setDescription(dto.description());
        if(!dto.ownerId().isEmpty()) category.setOwnerId(dto.ownerId());
        return repository.save(category);
    }

    public Optional<Category> getById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        Category category = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        repository.delete(category);
    }
}
