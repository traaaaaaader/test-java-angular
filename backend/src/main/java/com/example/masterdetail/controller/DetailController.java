package com.example.masterdetail.controller;

import com.example.masterdetail.dto.DetailDto;
import com.example.masterdetail.entity.Detail;
import com.example.masterdetail.mapper.MapperUtils;
import com.example.masterdetail.service.DetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/details")
@Tag(name = "Detail", description = "Управление деталями (Detail)")
public class DetailController {
	private final DetailService detailService;

	public DetailController(DetailService detailService) {
		this.detailService = detailService;
	}

	@Operation(summary = "Добавить новую деталь к Master")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Деталь успешно добавлена"),
			@ApiResponse(responseCode = "404", description = "Master не найден"),
			@ApiResponse(responseCode = "409", description = "Нарушение уникальности"),
			@ApiResponse(responseCode = "500", description = "Ошибка сервера")
	})
	@PostMapping()
	public ResponseEntity<?> addDetail(@RequestParam("masterId") Long masterId, @RequestBody DetailDto dto) {
		Detail created = detailService.addDetail(masterId, MapperUtils.toDetailEntity(dto));
		return ResponseEntity.ok(MapperUtils.toDetailDto(created));
	}

	@Operation(summary = "Обновить существующую деталь")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Деталь успешно обновлена"),
			@ApiResponse(responseCode = "400", description = "Detail не принадлежит указанному Master"),
			@ApiResponse(responseCode = "404", description = "Detail не найден"),
			@ApiResponse(responseCode = "409", description = "Нарушение уникальности"),
			@ApiResponse(responseCode = "500", description = "Ошибка при обновлении")
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDetail(@RequestParam("masterId") Long masterId, @PathVariable Long id,
			@RequestBody DetailDto dto) {
		Detail updated = detailService.updateDetail(masterId, id, MapperUtils.toDetailEntity(dto));
		return ResponseEntity.ok(MapperUtils.toDetailDto(updated));
	}

	@Operation(summary = "Удалить деталь")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Деталь успешно удалена"),
			@ApiResponse(responseCode = "400", description = "Detail не принадлежит указанному Master"),
			@ApiResponse(responseCode = "404", description = "Detail не найден"),
			@ApiResponse(responseCode = "500", description = "Ошибка при удалении")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDetail(@RequestParam("masterId") Long masterId, @PathVariable Long id) {
		detailService.deleteDetail(masterId, id);
		return ResponseEntity.ok().build();
	}
}
