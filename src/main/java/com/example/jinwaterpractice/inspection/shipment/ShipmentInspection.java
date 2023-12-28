package com.example.jinwaterpractice.inspection.shipment;

import com.example.jinwaterpractice.production.Production;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shipment_inspection")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ShipmentInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "production_id")
    private Production production;

    @Column(name = "inspection_date")
    private LocalDate inspectionDate; // 검사일자

    @Column(name = "shipment_amount")
    private Integer shipmentAmount; // 출하 수량

    @Column(name = "completed_status", columnDefinition = "tinyint(1) default 0")
    private Integer completedStatus; // 0: 출하검사 전, 1: 출하검사 완료

    private String etc;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
