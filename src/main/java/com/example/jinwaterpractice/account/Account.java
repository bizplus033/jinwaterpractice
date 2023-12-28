package com.example.jinwaterpractice.account;

import com.example.jinwaterpractice.order.Order;
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
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String code;

    private String type;

    private String name;

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "business_category")
    private String businessCategory;

    private String ceo;

    private String tel;

    private String fax;

    private String email;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    private String etc;

    @Column(name = "delete_state", columnDefinition = "tinyint(1) default 0")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();
}
