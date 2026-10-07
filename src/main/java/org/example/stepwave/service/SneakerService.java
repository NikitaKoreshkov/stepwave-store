package org.example.stepwave.service;

import org.example.stepwave.model.Sneaker;
import org.example.stepwave.model.Image;
import org.example.stepwave.repository.SneakerRepository;
import org.example.stepwave.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SneakerService {

    @Autowired
    private SneakerRepository sneakerRepository;

    @Autowired
    private ImageRepository imageRepository;

    public Sneaker getSneakerById(Long id) {
        Sneaker sneaker = sneakerRepository.findById(id).orElse(null);
        if (sneaker != null) {
            // Получаем изображения для кроссовка
            List<Image> images = imageRepository.findBySneakerId(id);
            sneaker.setImages(images);  // Устанавливаем изображения в кроссовок
        }
        return sneaker;
    }
}