package com.desafio.anotaai.domain.product;

import com.desafio.anotaai.domain.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private Category category;

    public Product(ProductDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.ownerId = dto.ownerId();
        this.price = dto.price();
    }

//    public String deleteToString(){
//        JSONObject json = new JSONObject();
//        json.put("id", this.id);
//        json.put("ownerId", this.ownerId);
//        json.put("type", "delete-produto");
//
//        return json.toString();
//    }
}
