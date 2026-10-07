package org.example.stepwave.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Boolean isNewArrival;
    private Boolean isOnSale;

    private String description;
    private String imageUrl;

    private String composition; // Новое поле для состава

    @OneToMany(mappedBy = "sneaker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;  // Список изображений для кроссовка

    // Конструкторы, геттеры и сеттеры

    public Sneaker() {
    }

    public Sneaker(String name, Double price, Boolean isNewArrival, Boolean isOnSale, String description, String imageUrl, String composition) {
        this.name = name;
        this.price = price;
        this.isNewArrival = isNewArrival;
        this.isOnSale = isOnSale;
        this.description = description;
        this.imageUrl = imageUrl;
        this.composition = composition;  // Инициализация нового поля
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsNewArrival() {
        return isNewArrival;
    }

    public void setIsNewArrival(Boolean isNewArrival) {
        this.isNewArrival = isNewArrival;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;  // Сеттер для нового поля
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}