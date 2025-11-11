package com.example.masterdetail.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterDto {
	private Long id;
	private String number;
	private LocalDate date;
	private BigDecimal sum;
	private String note;
	private List<DetailDto> details;
}
