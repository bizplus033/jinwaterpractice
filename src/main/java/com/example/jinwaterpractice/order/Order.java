package com.example.jinwaterpractice.order;

import com.example.jinwaterpractice.account.Account;
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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

     @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
     private List<OrderDetail> listOrderDetail = new ArrayList<>();

    private String code;
    private String manager;

    @Column(name = "order_amount")
    private Integer orderAmount;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Integer state; // 0: 수주 접수, 1: 출하

    private String etc;

    // @Temporal(TemporalType.TIMESTAMP) // Java 8 이상부터는 안 쓰는 것 권장
    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState; // 0: 유지, 1: 삭제

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Transient
    private Integer totalOrderAmount;

    public void updateOrderState(int status) {
        this.state = status;
    }

}
