package com.example.jinwaterpractice.userlog;

import com.example.jinwaterpractice.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Date;

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

    /**
     * 정적 팩토리 메서드
     * */
    public static UserLog factory(HttpServletRequest request, User user, String logType) {
        UserLog userLog = new UserLog();
        userLog.setUser(user);
        userLog.setLogType(logType);
        userLog.setIpAddress(findIpAddress(request));
        return  userLog;
    }

    private static String findIpAddress(HttpServletRequest request) {
        try {
            return UserLogUtil.getClientIp(request);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Cannot Found IpAddress");
        }
    }
}
