package com.example.jinwaterpractice.bom;

import com.example.jinwaterpractice.bom.dto.ProductBOMResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/productBOM")
@RequiredArgsConstructor
public class ProductBOMController {
    private final ProductBOMService productBOMService;

    @GetMapping(value = {"/create", "/edit"})
    public String productBOMForm(Model model, HttpServletRequest request, @RequestParam(required = false) Long id) {
        if (request.getServletPath().contains("/edit")) {
            ProductBOMResponse response = productBOMService.findProductBOMById(id);
            model.addAttribute("productBOM", response);
        }
        return "productBOM/popupProductBOMInputForm";
    }
}
