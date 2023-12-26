package com.example.jinwaterpractice.userlog.custom;

import com.example.jinwaterpractice.userlog.UserLog;
import com.example.jinwaterpractice.userlog.dto.ListUserLogResponse;

public abstract class CustomUserLogMapper {
    public static ListUserLogResponse toListUserLogResponse(UserLog userLog) {
        return new ListUserLogResponse(
                userLog.getCreatedAt(),
                userLog.getUser().getUserId(),
                userLog.getUser().getName(),
                userLog.getLogType(),
                userLog.getIpAddress()
        );
    }
}
