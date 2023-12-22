package com.example.jinwaterpractice.user;

import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.user.dto.ListUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private List<String> navigationList; // 화면 상단 네비게이션 목록
    private final UserService userService;

    // 여기는 private이 맞는 듯 캡슐화
    public List<String> setNavigation() {
        List<String> navigation = new ArrayList<>();
        navigation.add("기준정보관리");
        navigation.add("사용자관리");
        return navigation;
    }

    @GetMapping("/list")
    public String userList(Model model, String userId, String name,
                           @PageableDefault(size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        // 화면 상단 네비게이션 목록
        navigationList = setNavigation();
        model.addAttribute("navigation", navigationList);

        Page<ListUserResponse> userPage = userService.findUserAllBySearchKeyword(userId, name, pageable);
        PagingUtil<ListUserResponse> pagingUtil = new PagingUtil<>(userPage);

        model.addAttribute("userId", userId);
        model.addAttribute("name", name);
        model.addAttribute("userPage", userPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "user/userList";
    }
}
