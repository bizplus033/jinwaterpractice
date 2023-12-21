package com.example.jinwaterpractice.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @Column(name = "tel")
    private String tel;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "etc")
    private String etc;

    @Column(name = "delete_state", columnDefinition = " tinyint(1) default 0 ")
    private Integer deleteState;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false) // update 시 null 로 수정되서 updatable = false 설정 추가
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "fail_count", columnDefinition = "tinyint(1) default 0")
    private Integer failCount;

    @Column(name = "failed_at")
    private Date failedAt;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "employment_date")
    private Date employmentDate;
}
