package org.example.stepwave.repository;

import org.example.stepwave.model.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
    // Дополнительные методы для поиска можно добавлять сюда, если нужно
}