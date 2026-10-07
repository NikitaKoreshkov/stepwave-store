package org.example.stepwave.controller;

import org.example.stepwave.model.Product;
import org.example.stepwave.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Получение всех продуктов (все товары)
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll(); // Получаем все товары
        model.addAttribute("products", products); // Добавляем товары в модель
        return "products"; // Название шаблона
    }

    // Получение продуктов по brandId (для определённого бренда)
    @GetMapping("/products/byBrand")
    public String getProductsByBrand(@RequestParam Long brandId, Model model) {
        List<Product> products = productRepository.findByBrandId(brandId); // Используем метод для поиска по brandId
        long productCount = productRepository.countByBrandId(brandId); // Количество продуктов для бренда
        model.addAttribute("products", products); // Добавляем товары в модель
        model.addAttribute("productCount", productCount); // Добавляем количество товаров
        return "products"; // Название шаблона
    }

    // Получение продуктов по categoryId (например, только женские кроссовки) — для всех брендов
    @GetMapping("/products/filter")
    public String getProductsByCategory(@RequestParam(required = false) List<Long> categoryId, Model model) {
        List<Product> products = new ArrayList<>();
        if (categoryId != null && !categoryId.isEmpty()) {
            // Поиск товаров по нескольким категориям
            products = productRepository.findByCategoryIdIn(categoryId); // Используем метод для поиска по списку категорий
        }
        long productCount = products.size(); // Количество продуктов для категории
        model.addAttribute("products", products); // Добавляем товары в модель
        model.addAttribute("productCount", productCount); // Добавляем количество товаров
        return "products"; // Название шаблона
    }

    // Получение продуктов по brandId и categoryId (фильтрация по обоим параметрам)
    @GetMapping("/products/filterByBrandAndCategory")
    public String getProductsByBrandAndCategory(@RequestParam Long brandId,
                                                @RequestParam List<Long> categoryId,
                                                Model model) {
        List<Product> products = productRepository.findByBrandIdAndCategoryIdIn(brandId, categoryId); // Поиск по brandId и множеству categoryId
        long productCount = productRepository.countByBrandIdAndCategoryIdIn(brandId, categoryId); // Количество продуктов для бренда и нескольких категорий
        model.addAttribute("products", products); // Добавляем товары в модель
        model.addAttribute("productCount", productCount); // Добавляем количество товаров
        return "products"; // Название шаблона
    }

    // Новый метод поиска продуктов по имени, цене и бренду
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(value = "search", required = false) String search,
                                 @RequestParam(value = "price", required = false) Double price,
                                 @RequestParam(value = "brandId", required = false) Long brandId,
                                 Model model) {
        List<Product> products = productRepository.searchProducts(search, price, brandId); // Поиск с учётом параметров
        long productCount = products.size(); // Количество найденных продуктов
        model.addAttribute("products", products); // Добавляем товары в модель
        model.addAttribute("productCount", productCount); // Добавляем количество товаров
        return "products"; // Название шаблона
    }
}
