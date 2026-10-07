package org.example.stepwave.model;

import javax.persistence.*;

@Entity
@Table(name = "product_color_images")
public class ProductColorImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imageUrl") // Указываем правильное имя колонки
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id") // Связь с продуктом
    private Sneaker sneaker;

    // Конструкторы, геттеры и сеттеры

    public ProductColorImage() {
    }

    public ProductColorImage(String imageUrl, Sneaker sneaker) {
        this.imageUrl = imageUrl;
        this.sneaker = sneaker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Sneaker getSneaker() {
        return sneaker;
    }

    public void setSneaker(Sneaker sneaker) {
        this.sneaker = sneaker;
    }
}