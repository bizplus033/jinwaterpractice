package com.example.jinwaterpractice.bom;

import com.example.jinwaterpractice.material.Material;
import com.example.jinwaterpractice.product.Product;
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
@Table(name = "product_bom")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProductBOM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // OneToOne으로 하려고 했다가 ManyToOne으로 했다고 함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    private String etc;

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
