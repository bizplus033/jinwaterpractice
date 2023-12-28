package com.example.jinwaterpractice.material.stock.history;

import com.example.jinwaterpractice.inspection.inbound.InboundInspection;
import com.example.jinwaterpractice.material.stock.MaterialStock;
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
@Table(name = "material_stock_history")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class MaterialStockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Integer amount;

    private String etc;

    @ManyToOne
    @JoinColumn(name = "material_stock_id")
    private MaterialStock materialStock;

    @OneToOne(mappedBy = "materialStockHistory")
    private InboundInspection inboundInspection;

    @Column(name = "inbound_inspection_state", columnDefinition = "tinyint(1) default 0")
    private Integer inboundInspectionState;

    @Column(name = "registered_at")
    private LocalDate registeredAt;

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
