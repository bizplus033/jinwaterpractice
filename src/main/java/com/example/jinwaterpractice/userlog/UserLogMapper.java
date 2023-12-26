package com.example.jinwaterpractice.userlog;

import com.example.jinwaterpractice.userlog.dto.ListUserLogResponse;
import org.springframework.stereotype.Component;
/**
 * 이걸 스프링 빈으로 등록할 필요가 있을까?
 * 그냥 추상 클래스로 만들어서 필요할 때 정적 메서드로 호출해서 만들어주면 될 것 같은데
 * */
@Component
public class UserLogMapper {
    // todo 이건 리팩토링 하는게 좋아 보인다.
    public ListUserLogResponse toListUserLogResponse(UserLog userLog) {
        return new ListUserLogResponse(
                userLog.getCreatedAt(),
                userLog.getUser().getUserId(),
                userLog.getUser().getName(),
                userLog.getLogType(),
                userLog.getIpAddress()
        );
    }
}
