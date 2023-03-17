package com.example.library20.controller;

import com.example.library20.entity.Author;
import com.example.library20.entity.Publisher;
import com.example.library20.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/all")
    public String allPublishers(Model model, @PageableDefault(sort = {"id"}, direction = ASC, size = 15) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
        Page<Publisher> page = publisherService.getAll(pageable);

        if(!search.equals(""))
            page = publisherService.getAllByName(search, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/publishers/all");
        return "/html/publishers";
    }
    @GetMapping("/delete/{id}")
    public String deletePublisher(@PathVariable Long id) {
        try {
            publisherService.deletePublisher(id);
        } catch (Exception ex){
            return "redirect:/publishers/all?status=failed";
        }
        return "redirect:/publishers/all";
    }
    @GetMapping("/edit/{id}")
    public String editPublisher(@PathVariable Long id, Model model) {
        model.addAttribute("publisher", publisherService.getById(id));
        model.addAttribute("url", "/publishers/edit");
        return "/html/publisherEditor";
    }
    @GetMapping("/create")
    public String createPublisher(Model model) {
        model.addAttribute("url", "/publishers/create");
        return "/html/publisherEditor";
    }
    @PostMapping("/save")
    public String savePublisher(@RequestParam String name, @RequestParam(required = false) Long id){
        Publisher publisher = new Publisher();
        publisher.setName(name);
        if(id!=null)
            publisher.setId(id);
        publisherService.save(publisher);
        return "redirect:/publishers/all";
    }
}
