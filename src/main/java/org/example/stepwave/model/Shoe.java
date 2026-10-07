package org.example.stepwave.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")  // Указываем имя таблицы как products
public class Shoe {

    @Id
    private Integer productId;  // Идентификатор продукта
    private String name;        // Название кроссовка
    private Double price;       // Цена
    private Integer modelId;    // Идентификатор модели
    private String imageUrl;    // URL изображения

    // Конструкторы, геттеры и сеттеры

    public Shoe() {}

    public Shoe(Integer productId, String name, Double price, Integer modelId, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.modelId = modelId;
        this.imageUrl = imageUrl;
    }

    // Геттеры и Сеттеры
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
