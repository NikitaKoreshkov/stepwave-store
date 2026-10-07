package org.example.stepwave.repository;

import org.example.stepwave.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Метод для поиска продуктов по brandId
    List<Product> findByBrandId(Long brandId);

    // Метод для подсчета продуктов по brandId
    long countByBrandId(Long brandId);

    // Метод для поиска продуктов по categoryId (для одного categoryId)
    List<Product> findByCategoryId(Long categoryId);

    // Метод для подсчета продуктов по categoryId (для одного categoryId)
    long countByCategoryId(Long categoryId);

    // Метод для поиска продуктов по brandId и categoryId (для одного значения categoryId)
    List<Product> findByBrandIdAndCategoryId(Long brandId, Long categoryId);

    // Метод для подсчета продуктов по brandId и categoryId
    long countByBrandIdAndCategoryId(Long brandId, Long categoryId);

    // Метод для поиска продуктов по списку categoryId (для нескольких категорий)
    List<Product> findByCategoryIdIn(List<Long> categoryIds);

    // Метод для подсчета продуктов по списку categoryId (для нескольких категорий)
    long countByCategoryIdIn(List<Long> categoryIds);

    // Метод для поиска продуктов по brandId и нескольким categoryId
    List<Product> findByBrandIdAndCategoryIdIn(Long brandId, List<Long> categoryIds);

    // Метод для подсчета продуктов по brandId и нескольким categoryId
    long countByBrandIdAndCategoryIdIn(Long brandId, List<Long> categoryIds);

    // Поиск продуктов по имени, цене и бренду
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "p.price = :price OR " +
            "p.brandId = :brandId")
    List<Product> searchProducts(@Param("search") String search,
                                 @Param("price") Double price,
                                 @Param("brandId") Long brandId);

}
