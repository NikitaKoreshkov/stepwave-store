package org.example.stepwave.service;

import org.example.stepwave.model.Image;
import org.example.stepwave.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // Получить все изображения для конкретного продукта
    public List<Image> getImagesBySneakerId(Long sneakerId) {
        return imageRepository.findBySneakerId(sneakerId);
    }

    // Добавить новое изображение для продукта
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    // Другие методы для работы с изображениями (удаление, обновление, и т.д.)
}