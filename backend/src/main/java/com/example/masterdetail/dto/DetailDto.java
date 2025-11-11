package com.example.masterdetail.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailDto {
	private Long id;
	private String name;
	private BigDecimal amount;
}
