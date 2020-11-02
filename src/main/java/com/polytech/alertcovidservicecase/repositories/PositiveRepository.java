package com.polytech.alertcovidservicecase.repositories;

import com.polytech.alertcovidservicecase.models.Positive;
import com.polytech.alertcovidservicecase.models.PositiveId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositiveRepository extends JpaRepository<Positive, PositiveId> {

    @Query(value = "SELECT * FROM positif Where id_user = ?1",nativeQuery = true)
    List<Positive> findAllByIdUser(long id_user);

}
