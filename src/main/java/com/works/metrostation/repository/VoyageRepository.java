package com.works.metrostation.repository;

import com.works.metrostation.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {

    Optional<Voyage> findFirstByDateArrivalGreaterThanAllIgnoreCaseOrderByDateArrivalAsc(Date dateArrival);

    @Query(value = "Select * From voyage WHERE date_arrival >= CURRENT_TIME ORDER BY date_arrival LIMIT 1;", nativeQuery = true)
    Optional<Voyage> getNearestVoyage();

}
