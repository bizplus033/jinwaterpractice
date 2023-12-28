package com.example.jinwaterpractice.company;

import com.example.jinwaterpractice.company.dto.InfoCompanyRequest;
import com.example.jinwaterpractice.company.dto.InfoCompanyResponse;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private List<String> navigationList;
    private final CompanyService companyService;

    @GetMapping("/info")
    public String companyInfo(Model model) {
        log.info("CompanyController.companyInfo");
        navigationList = CustomSetNavigationUtil.setNavigation("회사정보등록");
        model.addAttribute("navigation", navigationList);

        try{
            InfoCompanyResponse response = companyService.findCompanyById(1L);
            model.addAttribute("company", response);
        } catch(RuntimeException e) {
            model.addAttribute("message", "회사정보를 등록해주세요.");
        }
        return "/company/popupCompanyInputForm";
    }

    @PostMapping("/create")
    public String createCompany(InfoCompanyRequest request, RedirectAttributes rttr) {
        log.info("CompanyController.createCompany");
        companyService.createCompany(request);
        rttr.addFlashAttribute("message", "회사정보 등록이 완료되었습니다.");
        return "redirect:/company/info";
    }

    @PostMapping("/edit")
    public String updateCompany(InfoCompanyRequest request, RedirectAttributes redirectAttributes) {
        log.info("CompanyController.updateCompany");
        try {
            companyService.updateCompany(request);
            redirectAttributes.addFlashAttribute("message", "회사정보 수정에 성공하였습니다.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "회사정보 수정에 실패하였습니다.");
            log.error(e.getMessage());
        }
        return "redirect:/company/info";
    }
}
