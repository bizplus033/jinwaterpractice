package com.example.jinwaterpractice.product;

import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import com.example.jinwaterpractice.product.dto.ListProductResponse;
import com.example.jinwaterpractice.product.stock.ProductStock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
