package com.example.jinwaterpractice.claim;

import com.example.jinwaterpractice.claim.dto.CreateClaimRequest;
import com.example.jinwaterpractice.claim.dto.ListClaimResponse;
import com.example.jinwaterpractice.claim.dto.UpdateClaimRequest;
import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/claim")
@RequiredArgsConstructor
@Slf4j
public class ClaimController {
    private List<String> navigationList;
    private final ClaimService claimService;

    @GetMapping("/list")
    public String claimList(Model model, String accountName,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        navigationList = CustomSetNavigationUtil.setNavigation("품질관리", "클레임관리");
        model.addAttribute("navigation", navigationList);

        Page<ListClaimResponse> claimPage = claimService.findClaimAllByKeyword(accountName, startDate, endDate, pageable);
        PagingUtil<ListClaimResponse> pagingUtil = new PagingUtil<>(claimPage);

        model.addAttribute("accountName", accountName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("claimPage", claimPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "claim/claimList";
    }
    /**
     * 생성, 수정이 여기서는 팝업 형태로 출력되나봄
     * */
    @GetMapping(value = {"/create", "/edit"})
    public String popupClaimInputForm(Model model, HttpServletRequest request, @RequestParam(required = false) Long id) {
        if (request.getServletPath().contains("/edit")) {
            model.addAttribute("claim", claimService.findClaimById(id));
        }

        return "claim/popupClaimInputForm";
    }

    @PostMapping("/create")
    public String createClaim(CreateClaimRequest request, Model model) {
        try {
            claimService.createClaim(request);
            model.addAttribute("message", "클레임이 접수되었습니다.");
        } catch (Exception e) {
            model.addAttribute("message", "클레임을 접수할 수 없습니다.");
        }
        return "popup/popupClose";
    }

    @PostMapping("/edit")
    public String updateClaim(UpdateClaimRequest request, Model model) {
        try {
            claimService.updateClaim(request);
            model.addAttribute("message", "클레임 정보를 수정하였습니다.");
        } catch (RuntimeException e) {
            model.addAttribute("message", "클레임 정보를 수정할 수 없습니다.");
        }
        return "popup/popupClose";
    }
}
