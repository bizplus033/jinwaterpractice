package com.example.jinwaterpractice.product.stock;

import com.example.jinwaterpractice.product.Product;
import com.example.jinwaterpractice.product.stock.history.ProductStockHistory;
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
import java.util.List;

@Entity
@Table(name = "product_stock")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer stock;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "productStock")
    private List<ProductStockHistory> listProductStockHistory;
}
