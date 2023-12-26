package com.example.jinwaterpractice.userlog;

import com.example.jinwaterpractice.user.JpaUserRepository;
import com.example.jinwaterpractice.user.User;
import com.example.jinwaterpractice.userlog.custom.CustomUserLogMapper;
import com.example.jinwaterpractice.userlog.dto.ListUserLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
public class UserLogService {

     private final JpaUserLogRepository jpaUserLogRepository;
     private final DslUserLogRepository dslUserLogRepository;
     private final JpaUserRepository jpaUserRepository;
     private final UserLogMapper userLogMapper; // 굳이 필드로 만들 필요가 없다.

     /**
      * 키워드로 찾은 모든 유저 로그
      * */
     public Page<ListUserLogResponse> findUserLogAllByKeyword(String userId, String userName, Pageable pageable) {
          Page<UserLog> userLogPage = dslUserLogRepository.findUserLogAllByKeyword(userId, userName, pageable);
          return userLogPage.map(userLogMapper::toListUserLogResponse); // 메서드 참조
          // return userLogPage.map(userLog -> userLogMapper.toListUserLogResponse(userLog)) 람다 표현식
     }

     /**
      * V2
      * 스프링 빈 등록하지 않고 유틸 클래스 사용
      * */
     public Page<ListUserLogResponse> findUserLogAllByKeywordV2(String userId, String userName, Pageable pageable) {
          Page<UserLog> userLogPage = dslUserLogRepository.findUserLogAllByKeyword(userId, userName, pageable);
          return userLogPage.map(CustomUserLogMapper::toListUserLogResponse);
     }

     /**
      * 유저 로그 생성 - 로그인 시
      * */
     public void createUserLogin(HttpServletRequest request, User user) {
          // 세터를 정적 팩토리 메서드로 변환 하는 게 나아 보인다.
          UserLog userLog = new UserLog();
          userLog.setLogType("로그인");
          userLog.setIpAddress(findIpAddress(request));
          userLog.setUser(user);
          jpaUserLogRepository.save(userLog);
     }

     /**
      * 유저 로그 생성 - 로그인 시 - V2
      * */
     public void createUserLoginV2(HttpServletRequest request, User user) {
          UserLog userLog = UserLog.factory(request, user, "로그인");
          jpaUserLogRepository.save(userLog);
     }

     /**
      * 유저 로그 생성 - 로그아웃 시
      * */
     public void createUserLogout(HttpServletRequest request, String userId) {
          User userPS = jpaUserRepository.findByUserId(userId)
                  .orElseThrow(() -> new RuntimeException("User Not Found"));

          // 역시 세터를 정적 팩토리 메서드로 변환하는 게
          UserLog userLog = new UserLog();
          userLog.setLogType("로그아웃");
          userLog.setIpAddress(findIpAddress(request));
          userLog.setUser(userPS);
          jpaUserLogRepository.save(userLog);
     }
     /**
      * 유저 로그 생성 - 로그아웃 시 - V2
      * */
     public void createUserLogoutV2(HttpServletRequest request, String userId) {
          User userPS = jpaUserRepository.findByUserId(userId)
                  .orElseThrow(() -> new RuntimeException("User Not Found"));

          UserLog userLog = UserLog.factory(request, userPS, "로그아웃");
          jpaUserLogRepository.save(userLog);
     }
     /**
      * IP 주소 찾기
      * by UserLogUtil
      * */
     private String findIpAddress(HttpServletRequest request) {
          try {
               return UserLogUtil.getClientIp(request);
          } catch (UnknownHostException e) {
               throw new RuntimeException("Cannot Found IpAddress");
          }
     }
}
