package com.example.jinwaterpractice.product;

import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import com.example.jinwaterpractice.product.dto.CreateProductRequest;
import com.example.jinwaterpractice.product.dto.ListProductResponse;
import com.example.jinwaterpractice.product.dto.ProductResponse;
import com.example.jinwaterpractice.product.dto.UpdateProductRequest;
import com.example.jinwaterpractice.product.stock.ProductStock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private List<String> navigationList;
    private final ProductService productService;
    // private final ProductBOMService productBOMService;
    // private final ProcessInspectionContentService processInspectionContentService;
    // private final ShipmentInspectionContentService shipmentInspectionContentService;

    @GetMapping("/list")
    public String productList(Model model, String code, String name,
                              @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC)Pageable pageable) {
        log.info("ProductController.productList");
        navigationList = CustomSetNavigationUtil.setNavigation("기준정보관리", "제품정보등록");
        model.addAttribute("navigation", navigationList);

        Page<ListProductResponse> productPage = productService.findProductAllBySearchKeyword(code, name, pageable);
        PagingUtil<ListProductResponse> pagingUtil = new PagingUtil<>(productPage);

        model.addAttribute("code", code);
        model.addAttribute("name", name);
        model.addAttribute("productPage", productPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "product/productList";

    }
    @GetMapping("/popupList")
    public String productPopupList(Model model, String code, String name,
                                   @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("ProductController.productPopupList");
        Page<ListProductResponse> productPage = productService.findProductAllBySearchKeyword(code, name, pageable);
        PagingUtil<ListProductResponse> pagingUtil = new PagingUtil<>(productPage);

        model.addAttribute("code", code);
        model.addAttribute("name", name);
        model.addAttribute("productPage", productPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "product/popupProductList";
    }

    @RequestMapping(value = {"/create", "/edit"})
    public String productForm(Model model, HttpServletRequest request, @RequestParam(required = false) Long id) {
        navigationList = CustomSetNavigationUtil.setNavigation("기준정보관리", "제품정보등록");
        if (request.getServletPath().contains("/edit") && id != null){
            log.info("ProductController.productForm /edit");
            navigationList.add("상세보기");
            // 제품 상세보기 데이터
            ProductResponse response = productService.findProductById(id);
            model.addAttribute("product", response);
            // todo 제품 BOM 목록데이터, 제품 공정 검사항목 리스트, // 제품 출하 검사항목 리스트
        } else {
            log.info("ProductController.productForm /create");
            navigationList.add("등록하기");
        }
        model.addAttribute("navigation", navigationList);
        return "product/productInputForm";
    }

    @PostMapping("/create")
    public String createProduct(CreateProductRequest request, RedirectAttributes rttr) {
        Product product = productService.createProduct(request);
        rttr.addFlashAttribute("message", "제품정보 등록이 완료되었습니다.");
        return "redirect:/product/edit?id=" + product.getId();
    }

    @PostMapping("/edit")
    public String updateAccount(UpdateProductRequest request, Model model, RedirectAttributes rttr) {
        productService.updateProduct(request);
        rttr.addFlashAttribute("message", "제품정보 수정에 성공하였습니다.");
        return "redirect:/product/list";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam Long[] selectedId) {
        productService.updateProductDeleteState(selectedId);
        return "redirect:/product/list";
    }

    @PostMapping("/codeCheck")
    @ResponseBody
    public boolean codeCheck(String code) {
        return productService.checkByProductCode(code);
    }
}
