package com.example.masterdetail.repository;

import com.example.masterdetail.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByNumber(String number);

    boolean existsByNumber(String number);
}
