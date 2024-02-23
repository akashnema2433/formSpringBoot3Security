package com.orgtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class FrontController {

    @GetMapping("/public-page1")
    public String publicPage1(){
        return "public-page1";
    }


}
