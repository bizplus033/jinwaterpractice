package com.example.jinwaterpractice.inspection.inbound;

import com.example.jinwaterpractice.material.stock.history.MaterialStockHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "inbound_inspection")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class InboundInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_date")
    private LocalDate inspectionDate; // 검사일자

    private String result; // 판정결과

    private String etc;

    @OneToOne
    @JoinColumn(name = "materialStockHistory_id")
    private MaterialStockHistory materialStockHistory;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
