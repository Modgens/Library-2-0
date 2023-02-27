package com.example.library20.controller;

import com.example.library20.entity.Order;
import com.example.library20.entity.OrderKey;
import com.example.library20.entity.User;
import com.example.library20.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public String createOrder(@RequestParam Long bookId, @RequestParam String url) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(orderService.createOrder(bookId, user.getId()))
            return "redirect:" + url + "?status=success";
        return "redirect:" + url + "?status=failed";
    }
    @GetMapping("/orders")
    public String getAllByUserId(Model model, @PageableDefault(sort = {"id"}, direction = DESC, size = 15) Pageable pageable) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Order> page = orderService.getAllByUserId(pageable, user.getId());
        model.addAttribute("page", page);
        model.addAttribute("url", "/orders");
        return "/html/myOrders";
    }
    @PostMapping("/cancelOrder")
    public String cancelOrder(@RequestParam Long userId, @RequestParam Long bookId) {
        orderService.deleteByUserIdAndBookId(userId, bookId);
        return "redirect:/orders";
    }
}
