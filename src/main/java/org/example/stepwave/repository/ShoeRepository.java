package org.example.stepwave.repository;

import org.example.stepwave.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShoeRepository extends JpaRepository<Shoe, Integer> {

    // Метод для получения всех кроссовок по modelId
    List<Shoe> findAllByModelId(Integer modelId);
}
