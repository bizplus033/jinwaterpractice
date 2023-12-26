package com.example.jinwaterpractice.userlog.dto;

import com.example.jinwaterpractice.userlog.UserLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class ListUserLogResponse {
    private LocalDate logTime; // Date -> LocalDate
    private String userId;
    private String userName;
    private String logType;
    private String ipAddress;


}
