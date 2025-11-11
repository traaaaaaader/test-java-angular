package com.example.masterdetail.repository;

import com.example.masterdetail.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail, Long> {
}
