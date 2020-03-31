package com.dfy.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: DFY
 * @time: 2020/3/31 13:37
 */
@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping("/*")
    public String test() {
        return "/test";
    }


}
