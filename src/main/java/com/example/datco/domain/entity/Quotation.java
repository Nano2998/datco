package com.example.datco.domain.entity;

import com.example.datco.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Quotation extends IdAndCreatedEntity{

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Order request;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    private Long estimatedCost;

    private LocalDateTime productionTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void updateStatus(Status status) {
        this.status = status;
    }
}
