package com.example.masterdetail.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "error_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_at")
	private OffsetDateTime createdAt;

	private String entity;

	@Column(columnDefinition = "text")
	private String message;

	@PrePersist
	protected void onCreate() {
		createdAt = OffsetDateTime.now();
	}
}
