package com.example.jinwaterpractice.user;

import com.example.jinwaterpractice.user.dto.CreateUserRequest;
import com.example.jinwaterpractice.user.dto.ListUserResponse;
import com.example.jinwaterpractice.user.dto.UpdateUserRequest;
import com.example.jinwaterpractice.user.dto.UserResponse;
import com.example.jinwaterpractice.userlog.JpaUserLogRepository;
import com.example.jinwaterpractice.userlog.UserLog;
import com.example.jinwaterpractice.userlog.custom.CustomUserLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    /**
     * UserDetailsService를 구현하면 무조건 구현해야 하는 메소드
     * */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> result = jpaUserRepository.findByUserId(userId);
        if (result.isPresent()) {
            return new UserAccount(result.get());
        }
        log.warn("{} 로 아이디 정보를 찾을 수 없습니다.", userId);
        throw new UsernameNotFoundException(userId);
    }

    /**
     * 회원가입 - 어드민용?
     * */
    @Transactional
    public void signup(String userId, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUserId(userId);
        user.setPassword(encodedPassword);
        user.setDeleteState(0); // 0 이 false 즉, 삭제 되지 않은 상태
        User saved = jpaUserRepository.save(user);
        log.info("{} : 회원가입이 완료 되었습니다.", saved.getUserId());
    }

    /**
     * 특정 키워드로 모든 유저 찾기
     */
    public Page<ListUserResponse> findUserAllBySearchKeyword(String userId, String name, Pageable pageable) {
        Page<User> userPage = jpaUserRepository.findByUserAllBySearchKeyword(userId, name, pageable);
        return userPage.map(user -> modelMapper.map(user, ListUserResponse.class));

    }
    /**
     * ID로 회원 조회
     * */
    public UserResponse findUserById(Long id) {
        jpaUserRepository.findById(id).map(user -> modelMapper.map(user, UserResponse.class))
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return null;
    }

    /**
     * 회원 생성(가입)
     * */
//     modelMapper 라이브러리가 id랑 userId랑 매핑 시키려고해서 타입미스매치 애러발생해서 커스텀 매퍼 사용
    @Transactional
    public void createUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        User entity = userMapper.toEntity(new User(), request);
        jpaUserRepository.save(entity);
    }
    /**
     * userId가 있는지 없는지
     * */
    public boolean checkByUserId(String userId) {
        return jpaUserRepository.findByUserId(userId).isPresent();
    }
    /**
     * 회원 업데이트
     * */
    @Transactional
    public void updateUser(UpdateUserRequest request) {
        String encoded = passwordEncoder.encode(request.getPassword());
        request.setPassword(encoded);
        User userPS = jpaUserRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        userMapper.toEntity(userPS, request);
    }
    /**
     * 회원 삭제 - 삭제 상태 업데이트
     * 1이 삭제 상태
     * 0이 삭제 되지 않은 상태
     * */
    @Transactional
    public void updateDeleteStateOfUser(Long[] ids) {
        jpaUserRepository.updateDeleteStateOfUser(ids);
    }

    //    public User existsByUserIdAndPassword(String userId, String password){
    //        return jpaUserRepositoryImpl.existsByUserIdAndPassword(userId, passwordEncoder.encode(password)).get();
    //    }
}
