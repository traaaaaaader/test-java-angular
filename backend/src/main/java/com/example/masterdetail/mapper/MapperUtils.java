package com.example.masterdetail.mapper;

import com.example.masterdetail.dto.*;
import com.example.masterdetail.entity.*;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

public class MapperUtils {

	public static MasterDto toMasterDto(Master entity) {
		if (entity == null)
			return null;

		return MasterDto.builder()
				.id(entity.getId())
				.number(entity.getNumber())
				.date(entity.getDate())
				.sum(entity.getSum())
				.note(entity.getNote())
				.details(entity.getDetails() != null
						? entity.getDetails().stream()
								.map(MapperUtils::toDetailDto)
								.collect(Collectors.toList())
						: List.of())
				.build();
	}

	public static Master toMasterEntity(MasterDto dto) {
		if (dto == null)
			return null;

		Master master = new Master();
		master.setId(dto.getId());
		master.setNumber(dto.getNumber());
		master.setDate(dto.getDate());
		master.setSum(dto.getSum() != null ? dto.getSum() : BigDecimal.ZERO);
		master.setNote(dto.getNote());
		return master;
	}

	public static DetailDto toDetailDto(Detail entity) {
		if (entity == null)
			return null;

		return DetailDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.amount(entity.getAmount())
				.build();
	}

	public static Detail toDetailEntity(DetailDto dto) {
		if (dto == null)
			return null;

		Detail detail = new Detail();
		detail.setId(dto.getId());
		detail.setName(dto.getName());
		detail.setAmount(dto.getAmount() != null ? dto.getAmount() : BigDecimal.ZERO);
		return detail;
	}
}
