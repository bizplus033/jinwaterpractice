package com.example.jinwaterpractice.account;

import com.example.jinwaterpractice.account.dto.AccountResponse;
import com.example.jinwaterpractice.account.dto.CreateAccountRequest;
import com.example.jinwaterpractice.account.dto.ListAccountResponse;
import com.example.jinwaterpractice.account.dto.UpdateAccountRequest;
import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private List<String> navigationList = new ArrayList<>();
    private final AccountService accountService;

    @GetMapping("/list")
    public String accountList(Model model, String type, String code, String name,
                              @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC)Pageable pageable) {
        navigationList = CustomSetNavigationUtil.setNavigation("기준정보관리", "거래처정보등록");
        model.addAttribute("navigation", navigationList);

        Page<ListAccountResponse> accountPage = accountService.findAccountAllBySearchKeyword(type, code, name, pageable);
        PagingUtil<ListAccountResponse> pagingUtil = new PagingUtil<>(accountPage);

        model.addAttribute("type", type);
        model.addAttribute("code", code);
        model.addAttribute("name", name);
        model.addAttribute("accountPage", accountPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "account/accountList";
    }
    @GetMapping("/popupList")
    public String accountPopupList(Model model, String type, String code, String name,
                                   @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ListAccountResponse> accountPage = accountService.findAccountAllBySearchKeyword(type, code, name, pageable);
        PagingUtil<ListAccountResponse> pagingUtil = new PagingUtil<>(accountPage);

        model.addAttribute("type", type);
        model.addAttribute("code", code);
        model.addAttribute("name", name);
        model.addAttribute("accountPage", accountPage);
        model.addAttribute("pagingUtil", pagingUtil);

        return "account/popupAccountList";
    }

    @RequestMapping(value = {"/create", "/edit"})
    public String accountForm(Model model, HttpServletRequest request, @RequestParam(required = false) Long id) {
        navigationList = CustomSetNavigationUtil.setNavigation("기준정보관리", "거래처정보등록");

        if (request.getServletPath().contains("/edit") && id != null) {
            navigationList.add("상세보기");
            try {
                AccountResponse response = accountService.findAccountById(id);
                model.addAttribute("account", response);
            } catch (RuntimeException e) {
                model.addAttribute("message", "거래처 정보를 찾을 수 없습니다.");
                return "redirect:/account/list";
            }
        } else {
            navigationList.add("등록하기");
        }
        // 거래처 코드 A_*****
        String accountCode = accountService.createAccountCode();
        model.addAttribute("accountCode", accountCode);

        model.addAttribute("navigation", navigationList);
        return "account/accountInputForm";
    }

    @PostMapping("/create")
    public String createAccount(CreateAccountRequest request, RedirectAttributes rttr) {
        accountService.createAccount(request);
        rttr.addFlashAttribute("message", "거래처 등록이 완료되었습니다.");

        return "redirect:/account/list";
    }

    @PostMapping("/edit")
    public String updateAccount(UpdateAccountRequest request, Model model, RedirectAttributes rttr) {
        try {
            accountService.updateAccount(request);
            rttr.addFlashAttribute("message", "거래처 등록 수정에 성공하였습니다.");
        } catch(RuntimeException e) {
            rttr.addFlashAttribute("message", "거래처 등록 수정에 실패하였습니다.");
            log.error(e.getMessage(), e);
        }
        return "redirect:/account/list";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam Long[] selectedId) {
        accountService.updateDeleteStateOfAccount(selectedId);
        return "redirect:/account/list";
    }
}
