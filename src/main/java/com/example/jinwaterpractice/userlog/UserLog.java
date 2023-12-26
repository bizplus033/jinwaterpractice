package com.example.jinwaterpractice.userlog;

import com.example.jinwaterpractice.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_log")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "log_type")
    private String logType; // 사용 구분

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt; // 로그 일시로 사용

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
