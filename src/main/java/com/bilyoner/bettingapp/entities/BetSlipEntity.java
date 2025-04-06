package com.bilyoner.bettingapp.entities;

import com.bilyoner.bettingapp.enums.BetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bet_slips")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetSlipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Enumerated(EnumType.STRING)
    @Column(name = "bet_type", nullable = false)
    private BetType betType;

    @Column(name = "bet_rate", nullable = false)
    private BigDecimal betRate;

    @Column(name = "bet_amount", nullable = false)
    private BigDecimal betAmount;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

}
