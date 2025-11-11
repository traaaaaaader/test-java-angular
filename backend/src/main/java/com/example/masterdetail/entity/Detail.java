package com.example.masterdetail.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detail", uniqueConstraints = { @UniqueConstraint(columnNames = { "master_id", "name" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    private Master master;
}
