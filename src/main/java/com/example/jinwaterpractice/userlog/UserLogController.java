package com.example.jinwaterpractice.userlog;

import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import com.example.jinwaterpractice.userlog.dto.ListUserLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userLog") // 하이픈(user-log)가 가장 권장되는 방식
@RequiredArgsConstructor
public class UserLogController {
    private List<String> navigationList;
    private final UserLogService userLogService;

    // todo 코드는 복사되는 게 아니라 재사용 되어야 한다.
    private List<String> setNavigation() {
        List<String> navigation = new ArrayList<>();
        navigation.add("기준정보관리");
        navigation.add("로그인 기록");
        return navigation;
    }

    @GetMapping("/list")
    public String userLogList(Model model, String userId, String userName,
                              @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // navigationList = setNavigation();
        navigationList = CustomSetNavigationUtil.setNavigation("기준정보관리", "로그인 기록");
        model.addAttribute("navigation", navigationList);

        Page<ListUserLogResponse> userLogPage = userLogService.findUserLogAllByKeywordV2(userId, userName, pageable);
        PagingUtil<ListUserLogResponse> pagingUtil = new PagingUtil<>(userLogPage);

        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userLogPage", userLogPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "/userLog/userLogList";
    }

    // 로그아웃 버튼을 누르면
    // todo Authentication 객체를 가져오려면 @Authentication을 사용하는 것이 나아 보이는데
    @GetMapping("/logout")
    public String createUserLogOfLogout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 로그인이 되어 있다면
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            userLogService.createUserLogout(request, principal.getUsername());
        }
        return "redirect:/logout";
    }


}
