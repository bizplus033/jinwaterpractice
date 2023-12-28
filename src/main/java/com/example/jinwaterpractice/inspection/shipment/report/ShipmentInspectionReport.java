package com.example.jinwaterpractice.inspection.shipment.report;

import com.example.jinwaterpractice.inspection.shipment.ShipmentInspection;
import com.example.jinwaterpractice.inspection.shipment.content.ShipmentInspectionContent;
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
@Table(name = "shipment_inspection_report")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ShipmentInspectionReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipment_inspection_id")
    private ShipmentInspection shipmentInspection;

    @ManyToOne
    @JoinColumn(name = "shipment_inspection_content_id")
    private ShipmentInspectionContent shipmentInspectionContent;

    private String checkedResult; // 판정 선택 값

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
