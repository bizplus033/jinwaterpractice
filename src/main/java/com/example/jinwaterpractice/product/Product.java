package com.example.jinwaterpractice.product;

import com.example.jinwaterpractice.bom.ProductBOM;
import com.example.jinwaterpractice.orderdetail.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @Column(name = "unit_price")
    private Integer unitPrice; // 단가

    private String etc;

    //@OneToMany(mappedBy = "product")
//    private List<ProductBOM> listProductBOM;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> listOrderDetail = new ArrayList<>();

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

//    @OneToOne(mappedBy = "product")
//    private ProductStock productStock;
}
