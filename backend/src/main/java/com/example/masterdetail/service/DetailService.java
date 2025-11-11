package com.example.masterdetail.service;

import com.example.masterdetail.entity.Master;
import com.example.masterdetail.entity.Detail;
import com.example.masterdetail.repository.MasterRepository;
import com.example.masterdetail.repository.DetailRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailService {
	private final MasterRepository masterRepo;
	private final DetailRepository detailRepo;
	private final ErrorLogService errorLogService;

	public DetailService(MasterRepository masterRepo, DetailRepository detailRepo, ErrorLogService errorLogService) {
		this.masterRepo = masterRepo;
		this.detailRepo = detailRepo;
		this.errorLogService = errorLogService;
	}

	@Transactional
	public Detail addDetail(Long masterId, Detail detail) {
		try {
			Detail saved = masterRepo.findById(masterId).map(master -> {
				detail.setMaster(master);
				return detailRepo.save(detail);
			}).orElseThrow(() -> new RuntimeException("Master not found"));

			saved.getMaster().recalcSum();
			return saved;
		} catch (DataIntegrityViolationException ex) {
			errorLogService.logError("Detail", "Unique constraint violation on addDetail: " + ex.getMessage());
			throw ex;
		}
	}

	@Transactional
	public Detail updateDetail(Long masterId, Long detailId, Detail payload) {
		try {
			Detail detail = detailRepo.findById(detailId).orElseThrow(() -> new RuntimeException("Detail not found"));

			if (!detail.getMaster().getId().equals(masterId)) {
				throw new RuntimeException("detail does not belong to Master");
			}

			detail.setName(payload.getName());
			detail.setAmount(payload.getAmount());
			detail.getMaster().recalcSum();
			return detailRepo.save(detail);
		} catch (DataIntegrityViolationException ex) {
			errorLogService.logError("Detail", "Unique constraint violation on updateDetail: " + ex.getMessage());
			throw ex;
		}
	}

	@Transactional
	public void deleteDetail(Long masterId, Long detailId) {
		Detail detail = detailRepo.findById(detailId).orElseThrow(() -> new RuntimeException("detail not found"));
		if (!detail.getMaster().getId().equals(masterId))
			throw new RuntimeException("detail does not belong to Master");
		Master master = detail.getMaster();
		master.removeDetail(detail);
		masterRepo.save(master);
	}
}
