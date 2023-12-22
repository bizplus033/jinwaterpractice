package com.example.jinwaterpractice.user;

import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.user.dto.CreateUserRequest;
import com.example.jinwaterpractice.user.dto.ListUserResponse;
import com.example.jinwaterpractice.user.dto.UpdateUserRequest;
import com.example.jinwaterpractice.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping({"/create", "/edit"})
    public String userForm(Model model, HttpServletRequest request, @RequestParam(required = false) Long id) {
        navigationList = setNavigation();

        if (request.getServletPath().contains("/edit") && id != null) {
            navigationList.add("상세보기");
            try{
                UserResponse response = userService.findUserById(id);
                model.addAttribute("user", response);
            } catch (Exception e) {
                model.addAttribute("message", "사용자 정보를 찾을 수 없습니다.");
                return "redirect:/user/list";
            }
        } else {
            navigationList.add("등록하기");
        }
        model.addAttribute("navigation", navigationList);
        return "user/userInputForm";
    }

    @PostMapping("/create")
    public String createUser(CreateUserRequest request, RedirectAttributes redirectAttributes) {
        userService.createUser(request);
        redirectAttributes.addFlashAttribute("message", "사용자가 등록되었습니다.");
        return "redirect:/user/list";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(String userId) {
        return userService.checkByUserId(userId);
    }

    @PostMapping("/edit")
    public String updateUser(UpdateUserRequest request, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(request);
            redirectAttributes.addFlashAttribute("message", "사용자 등록 수정에 성공했습니다.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "사용자 등록 수정에 실패했습니다.");
        }
        return "redirect:/user/list";
    }

    @PostMapping("/delete")
    public String deleteUser(Long[] selectedId) {
        userService.updateDeleteStateOfUser(selectedId);
        return "redirect:/user/list";
    }
}
