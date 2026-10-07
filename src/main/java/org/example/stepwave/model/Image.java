package org.example.stepwave.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")  // Связь с продуктом
    private Sneaker sneaker;

    // Конструкторы, геттеры и сеттеры

    public Image() {
    }

    public Image(String url, Sneaker sneaker) {
        this.url = url;
        this.sneaker = sneaker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Sneaker getSneaker() {
        return sneaker;
    }

    public void setSneaker(Sneaker sneaker) {
        this.sneaker = sneaker;
    }
}