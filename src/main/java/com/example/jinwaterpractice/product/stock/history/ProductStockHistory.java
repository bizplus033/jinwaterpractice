package com.example.jinwaterpractice.product.stock.history;

import com.example.jinwaterpractice.product.stock.ProductStock;
import com.example.jinwaterpractice.production.Production;
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
@Table(name = "product_stock_history")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProductStockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // 생산, 출하, ...

    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "product_stock_id")
    private ProductStock productStock;

    @OneToOne // ProductStockHistory - '제품생산'으로 생성 시, Production과 1:1이어야함
    @JoinColumn(name = "production_id")
    private Production production;

    @OneToOne // ProductStockHistory - '제품출하'으로 생성 시,  ShipmentInspection과 1:1이어야함
    @JoinColumn(name = "shipment_inspection_id")
    private ShipmentInspection shipmentInspection;

    @Column(name = "registered_at")
    private LocalDate registeredAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
