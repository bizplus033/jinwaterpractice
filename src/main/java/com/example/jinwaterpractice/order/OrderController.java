package com.example.jinwaterpractice.order;

import com.example.jinwaterpractice.main.PagingUtil;
import com.example.jinwaterpractice.main.custom.CustomSetNavigationUtil;
import com.example.jinwaterpractice.order.dto.CreateOrderRequest;
import com.example.jinwaterpractice.order.dto.ListOrderResponse;
import com.example.jinwaterpractice.order.dto.UpdateOrderRequest;
import com.example.jinwaterpractice.orderdetail.OrderDetailService;
import com.example.jinwaterpractice.orderdetail.dto.ListOrderDetailResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private List<String> navigationList;

    @GetMapping("/list")
    public String orderList(Model model, String orderCode, String accountName, Integer orderState,
                            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
                            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate,
                            @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("OrderController.orderList, /list");
        navigationList = CustomSetNavigationUtil.setNavigation("제품관리", "제품수주등록");
        model.addAttribute("navigation", navigationList);

        Page<ListOrderResponse> orderPage = orderService.findOrderAllBySearchKeyword(orderCode, accountName, orderState, startDate, endDate, pageable);
        PagingUtil<ListOrderResponse> pagingUtil = new PagingUtil<>(orderPage);

        model.addAttribute("orderCode", orderCode);
        model.addAttribute("accountName", accountName);
        model.addAttribute("orderState", orderState);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("pagingUtil", pagingUtil);
        return "order/orderList";
    }

    @GetMapping(value = {"/create", "/edit"})
    public String orderForm(Model model, HttpServletRequest request, @RequestParam(required = false) Long id) {
        navigationList = CustomSetNavigationUtil.setNavigation("제품관리", "제품수주등록");

        if (request.getServletPath().contains("/edit") && id != null) {
            log.info("OrderController.orderForm, /edit");
            navigationList.add("상세보기");
            try {
                // 수주 상세보기
                model.addAttribute("order", orderService.findOrderById(id));

                // 수주 제품 목록 리스트
                List<ListOrderDetailResponse> listOrderDetailResponse = orderDetailService.findOrderDetailAllByOrderId(id);
                model.addAttribute("listOrderDetail", listOrderDetailResponse);

            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }
        } else {
            log.info("OrderController.orderForm, /create");
            navigationList.add("등록하기");
            // 수주 코드 생성
            String code = orderService.findOrderCodeOneByCreatedAtToday();
            String newOrderCode = orderService.createOrderCode(code);
            model.addAttribute("newOrderCode", newOrderCode);
        }
        model.addAttribute("navigation", navigationList);
        return "order/orderInputForm";
    }

    @PostMapping("/create")
    public String createOrder(CreateOrderRequest request, RedirectAttributes rttr) {
        log.info("OrderController.createOrder, /create");
        // 접수입 또는 출고일 미입력 시 접수일: 오늘, 출고일: 일주일 뒤
        if (request.getOrderDate() == null || request.getReleaseDate() == null) {
            LocalDate today = LocalDate.now();
            if (request.getOrderDate() == null) {
                request.setOrderDate(today);
            }
            LocalDate oneWeekLater = today.plusWeeks(1);
            if (request.getReleaseDate() == null) {
                request.setReleaseDate(oneWeekLater);
            }
        }
        Order order = orderService.createOrder(request);
        rttr.addAttribute("id", order.getId()); // 쿼리 스트링에 추가
        rttr.addFlashAttribute("message", "수주 등록이 완료되었습니다.");
        return "redirect:/order/edit";
    }

    @PostMapping("/edit")
    public String updateOrder(UpdateOrderRequest request, RedirectAttributes rttr){
        log.info("OrderController.updateOrder, /edit");
        orderService.updateOrder(request);
        rttr.addFlashAttribute("message", "수주정보 수정에 성공하였습니다.");
        return "redirect:/order/list";
    }

    @PostMapping("/delete")
    public String deleteOrder(@RequestParam Long[] selectedId) {
        log.info("OrderController.deleteOrder, /delete");
        orderService.updateDeleteStateOfOrder(selectedId);
        return "redirect:/order/list";
    }
}
