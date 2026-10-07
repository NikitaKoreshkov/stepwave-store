package org.example.stepwave.controller;

import org.example.stepwave.model.ProductColorImage;
import org.example.stepwave.model.Sneaker;
import org.example.stepwave.service.ProductColorImageService;
import org.example.stepwave.service.SneakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductColorImageController {

    @Autowired
    private ProductColorImageService productColorImageService;

    @Autowired
    private SneakerService sneakerService; // Сервис для получения продукта

    @GetMapping("/productDetails/{id}/colorImages")
    public String getProductColorImages(@PathVariable Long id, Model model) {
        // Получаем продукт по ID
        Sneaker sneaker = sneakerService.getSneakerById(id);
        if (sneaker != null) {
            // Получаем изображения по объекту Sneaker
            List<ProductColorImage> productColorImages = productColorImageService.getImagesBySneaker(sneaker);

            // Добавляем изображения в модель
            model.addAttribute("productColorImages", productColorImages);
            model.addAttribute("sneaker", sneaker); // Добавляем сам продукт
            return "productDetails"; // Название шаблона
        }
        return "error"; // Если продукт не найден
    }
}