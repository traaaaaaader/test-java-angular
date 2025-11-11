package com.example.masterdetail.controller;

import com.example.masterdetail.dto.MasterDto;
import com.example.masterdetail.mapper.MapperUtils;
import com.example.masterdetail.service.MasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/masters")
@Tag(name = "Master", description = "Управление главными сущностями (Master)")
public class MasterController {
    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @Operation(summary = "Получить все записи Master", description = "Возвращает список всех Master-записей.")
    @ApiResponse(responseCode = "200", description = "Успешное получение списка")
    @GetMapping
    public List<MasterDto> all() {
        return masterService.findAll()
                .stream()
                .map(MapperUtils::toMasterDto)
                .toList();
    }

    @Operation(summary = "Получить Master по ID")
    @ApiResponse(responseCode = "200", description = "Master найден")
    @ApiResponse(responseCode = "404", description = "Master не найден")
    @GetMapping("/{id}")
    public ResponseEntity<MasterDto> getOne(@PathVariable Long id) {
        return masterService.findById(id)
                .map(MapperUtils::toMasterDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новую запись Master")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Master успешно создан"),
            @ApiResponse(responseCode = "409", description = "Нарушение уникальности (например, номер уже существует)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<MasterDto> create(@RequestBody MasterDto dto) {
        var created = masterService.createMaster(MapperUtils.toMasterEntity(dto));
        return ResponseEntity.ok(MapperUtils.toMasterDto(created));
    }

    @Operation(summary = "Обновить Master по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Master успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Master не найден"),
            @ApiResponse(responseCode = "409", description = "Нарушение уникальности"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обновлении")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MasterDto> update(@PathVariable Long id, @RequestBody MasterDto dto) {
        var updated = masterService.updateMaster(id, MapperUtils.toMasterEntity(dto));
        return ResponseEntity.ok(MapperUtils.toMasterDto(updated));
    }

    @Operation(summary = "Удалить Master по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Master успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Master не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка при удалении")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        masterService.deleteMaster(id);
        return ResponseEntity.ok().build();
    }
}
