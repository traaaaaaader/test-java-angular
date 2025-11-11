package com.example.masterdetail.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum = BigDecimal.ZERO;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Detail> details = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void recalcSum() {
        this.sum = details.stream()
                .map(Detail::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addDetail(Detail detail) {
        detail.setMaster(this);
        details.add(detail);
        recalcSum();
    }

    public void removeDetail(Detail detail) {
        details.remove(detail);
        detail.setMaster(null);
        recalcSum();
    }
}
