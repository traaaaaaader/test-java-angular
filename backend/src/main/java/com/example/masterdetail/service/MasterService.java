package com.example.masterdetail.service;

import com.example.masterdetail.entity.Master;
import com.example.masterdetail.repository.MasterRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MasterService {
    private final MasterRepository masterRepo;
    private final ErrorLogService errorLogService;

    public MasterService(MasterRepository masterRepo, ErrorLogService errorLogService) {
        this.masterRepo = masterRepo;
        this.errorLogService = errorLogService;
    }

    @Transactional
    public Master createMaster(Master master) {
        try {
            if (master.getDate() == null)
                master.setDate(LocalDate.now());
            if (master.getSum() == null)
                master.setSum(BigDecimal.ZERO);
            master.setSum(BigDecimal.ZERO);
            return masterRepo.save(master);
        } catch (DataIntegrityViolationException ex) {
            errorLogService.logError("Master", "Unique constraint violation on createMaster: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            errorLogService.logError("Master", "Error on createMaster: " + ex.getMessage());
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<Master> findAll() {
        return masterRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Master> findById(Long id) {
        return masterRepo.findById(id);
    }

    @Transactional
    public Master updateMaster(Long id, Master payload) {
        Master master = masterRepo.findById(id).orElseThrow(() -> new RuntimeException("Master not found"));
        master.setNote(payload.getNote());
        if (payload.getNumber() != null && !payload.getNumber().equals(master.getNumber())) {
            master.setNumber(payload.getNumber());
        }
        if (payload.getDate() != null)
            master.setDate(payload.getDate());
        try {
            return masterRepo.save(master);
        } catch (DataIntegrityViolationException ex) {
            errorLogService.logError("Master", "Unique constraint violation on updateMaster: " + ex.getMessage());
            throw ex;
        }
    }

    @Transactional
    public void deleteMaster(Long id) {
        masterRepo.deleteById(id);
    }
}
