package com.example.BAS.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
public class BlogController {

    @GetMapping("/blogHome")
    public String showBlogHome() {
        return "blog/blog-home";
    }
    @GetMapping("/blogCollection")
    public String showBlogCollection() {
        return "blog/blog-collection";
    }
    @GetMapping("/blogMembership")
    public String showMembership() {
        return "blog/blog-membership";
    }
    @GetMapping("/blogAbout")
    public String showBlogAbout() {
        return "blog/blog-about";
    }
}