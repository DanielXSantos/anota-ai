package com.desafio.anotaai.services;


import com.desafio.anotaai.domain.category.Category;
import com.desafio.anotaai.exceptions.CategoryNotFoundException;
import com.desafio.anotaai.domain.product.Product;
import com.desafio.anotaai.domain.product.ProductDTO;
import com.desafio.anotaai.exceptions.ProductNotFoundException;
import com.desafio.anotaai.repositories.ProductRepository;
import com.desafio.anotaai.services.aws.AwsSnsService;
import com.desafio.anotaai.services.aws.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AwsSnsService snsService;


    public Product insert(ProductDTO dto) {
        Category category = categoryService.getById(dto.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(dto);
        product.setCategory(category);
        repository.save(product);
        this.snsService.publish(new MessageDTO(product.getOwnerId()));

        return product;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product update(String id, ProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(ProductNotFoundException::new);
        if(dto.categoryId() != null) {
            categoryService.getById(dto.categoryId())
                    .ifPresent(product::setCategory);
        }


        if(!dto.title().isEmpty()) product.setTitle(dto.title());
        if(!dto.description().isEmpty()) product.setDescription(dto.description());
        if(dto.price() != null) product.setPrice(dto.price());
        repository.save(product);
        this.snsService.publish(new MessageDTO(product.getOwnerId()));
        return product;

    }

    public void delete(String id) {
        Product product = repository.findById(id).orElseThrow(ProductNotFoundException::new);
        repository.delete(product);
//        this.snsService.publish(new MessageDTO(product.deleteToString()));

    }
}
