package com.works.metrostation.repository;

import com.works.metrostation.model.Metro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetroRepository extends JpaRepository<Metro, Long> {
}
