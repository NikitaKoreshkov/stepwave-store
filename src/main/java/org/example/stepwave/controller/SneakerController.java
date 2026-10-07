package org.example.stepwave.controller;

import org.example.stepwave.model.Sneaker;
import org.example.stepwave.model.Image;
import org.example.stepwave.model.ProductColorImage;
import org.example.stepwave.service.SneakerService;
import org.example.stepwave.service.ImageService;
import org.example.stepwave.service.ProductColorImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class SneakerController {

    @Autowired
    private SneakerService sneakerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductColorImageService productColorImageService; // Сервис для работы с изображениями

    @GetMapping("/productDetails/{id}")
    public String getSneakerDetails(@PathVariable Long id, Model model) {
        Sneaker sneaker = sneakerService.getSneakerById(id);
        if (sneaker != null) {
            // Получаем изображения из таблицы image
            List<Image> images = imageService.getImagesBySneakerId(id);
            model.addAttribute("sneaker", sneaker);
            model.addAttribute("images", images); // Изображения из таблицы image

            // Получаем изображения из таблицы product_color_images через объект Sneaker
            List<ProductColorImage> productColorImages = productColorImageService.getImagesBySneaker(sneaker);
            model.addAttribute("productColorImages", productColorImages); // Изображения из новой таблицы

            model.addAttribute("composition", sneaker.getComposition()); // Поле состава
            return "productDetails"; // Название шаблона
        }
        return "error"; // Если продукт не найден
    }
}