package org.example.stepwave.service;

import org.example.stepwave.model.ProductColorImage;
import org.example.stepwave.model.Sneaker;
import org.example.stepwave.repository.ProductColorImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductColorImageService {

    @Autowired
    private ProductColorImageRepository productColorImageRepository;

    // Получить изображения для конкретного продукта
    public List<ProductColorImage> getImagesBySneaker(Sneaker sneaker) {
        return productColorImageRepository.findBySneaker(sneaker); // Получаем изображения по объекту Sneaker
    }
}