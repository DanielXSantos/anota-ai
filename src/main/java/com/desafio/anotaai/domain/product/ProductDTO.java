package com.desafio.anotaai.domain.product;

import com.desafio.anotaai.domain.category.Category;

public record ProductDTO(String id, String title, String description, String ownerId, Integer price, String categoryId) {
}