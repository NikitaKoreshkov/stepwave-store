package org.example.stepwave.controller;

import org.example.stepwave.model.Shoe;
import org.example.stepwave.repository.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShoeDetailsController {

    @Autowired
    private ShoeRepository shoeRepository;

    @GetMapping("/shoe/{modelId}")
    public String getShoeDetails(@PathVariable Integer modelId, Model model) {
        // Получаем список всех товаров с данным modelId
        List<Shoe> shoes = shoeRepository.findAllByModelId(modelId);
        model.addAttribute("shoes", shoes);
        return "shoeDetails"; // Возвращаем имя шаблона для отображения
    }
}
