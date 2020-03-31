package com.dfy.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: DFY
 * @time: 2020/3/31 13:26
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

}
