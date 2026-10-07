package org.example.stepwave.repository;

import org.example.stepwave.model.ProductColorImage;
import org.example.stepwave.model.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductColorImageRepository extends JpaRepository<ProductColorImage, Long> {
    // Метод для получения изображений для конкретного продукта
    List<ProductColorImage> findBySneaker(Sneaker sneaker); // Поиск по объекту Sneaker
}