package com.example.library20.controller;

import com.example.library20.entity.Order;
import com.example.library20.entity.OrderKey;
import com.example.library20.entity.User;
import com.example.library20.service.BookService;
import com.example.library20.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final BookService bookService;

    @PostMapping("/save")
    public String createOrder(@RequestParam Long bookId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String error = orderService.createOrder(bookId, user.getId());
        if(error.isEmpty()){
            redirectAttributes.addFlashAttribute("status", "success");
            return "redirect:" + request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("status", "failed");
        redirectAttributes.addFlashAttribute("errorMessage", error);
        return "redirect:" + request.getHeader("Referer");
    }
    @GetMapping("/all")
    public String getAllByUserId(Model model, @PageableDefault(sort = {"id"}, direction = DESC, size = 15) Pageable pageable) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Order> page = orderService.getAllByUserId(pageable, user.getId());
        model.addAttribute("page", page);
        model.addAttribute("url", "/orders/all");
        return "/html/myOrders";
    }
    @PostMapping("/cancelOrder")
    public String cancelOrder(@RequestParam Long userId, @RequestParam Long bookId, HttpServletRequest request) {
        orderService.deleteByUserIdAndBookId(userId, bookId);
        return "redirect:" + request.getHeader("Referer");
    }
    @GetMapping("/edit/all")
    public String allOrders(Model model, @PageableDefault(sort = {"id"}, direction = DESC, size = 15) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
        Page<Order> page = orderService.getAll(pageable);

        if(!search.equals(""))
            page = orderService.getAllByLoginOrName(search, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/orders/edit/all");
        return "/html/allOrders";
    }
    @PostMapping("/edit/returnBook")
    public String returnBook(@RequestParam Long userId, @RequestParam Long bookId, HttpServletRequest request) {
        orderService.deleteByUserIdAndBookId(userId, bookId);
        bookService.returnBook(bookId);
        return "redirect:" + request.getHeader("Referer");
    }
    @PostMapping("/edit/giveBook")
    public String giveBook(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam String dateToReturn, HttpServletRequest request) {
        orderService.saveOrder(bookId, userId, dateToReturn);
        bookService.giveBook(bookId);
        return "redirect:" + request.getHeader("Referer");
    }
}
