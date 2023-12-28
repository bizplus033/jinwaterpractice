package com.example.jinwaterpractice.production;

import com.example.jinwaterpractice.orderdetail.OrderDetail;
import com.example.jinwaterpractice.product.stock.history.ProductStockHistory;
import com.example.jinwaterpractice.inspection.process.ProcessInspection;
import com.example.jinwaterpractice.inspection.shipment.ShipmentInspection;
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
@Table(name = "production")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    @Column(name = "manufacture_amount")
    private Integer manufactureAmount; // 제조수량

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate; // 제조일

    @Column(name = "process_inspection_state", columnDefinition = "tinyint(1) default 0")
    private Integer processInspectionState; // 0: 공정검사 전, 1: 공정검사 후

    @Column(name = "shipment_inspection_state", columnDefinition = "tinyint(1) default 0")
    private Integer shipmentInspectionState; // 0: 출하검사 전, 1: 출하검사 후(출하)

    private String etc;

    @OneToOne(mappedBy = "production")
    private ProcessInspection ProcessInspection;

    @OneToOne(mappedBy = "production")
    private ShipmentInspection ShipmentInspection;

    @OneToOne(mappedBy = "production")
    private ProductStockHistory productStockHistory;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
