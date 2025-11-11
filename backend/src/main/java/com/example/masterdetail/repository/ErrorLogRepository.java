package com.example.masterdetail.repository;

import com.example.masterdetail.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
