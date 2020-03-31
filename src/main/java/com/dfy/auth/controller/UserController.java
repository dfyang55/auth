package com.dfy.auth.controller;

import com.dfy.auth.annotation.DecryptAndVerify;
import com.dfy.auth.enums.ResponseStatusEnum;
import com.dfy.auth.utils.UserLoginUtil;
import com.dfy.auth.vo.EncryptedReq;
import com.dfy.auth.vo.ResponseVo;
import com.dfy.auth.vo.UserLoginReq;
import com.dfy.auth.vo.UserLoginRes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 用户控制层
 * @author: DFY
 * @time: 2020/3/30 10:58
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    @DecryptAndVerify(decryptedClass = UserLoginReq.class)
    public ResponseVo login(@RequestBody @Validated EncryptedReq<UserLoginReq> encryptedReq, HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        UserLoginReq userLoginReq = encryptedReq.getData();
        // TODO 从数据库核实用户登录信息，这里懒得查数据库了
        if (userLoginReq.getUsername().equals("admin") && userLoginReq.getPassword().equals("admin")) {
            request.getSession().setAttribute("username", userLoginReq.getUsername());
            String authToken = UserLoginUtil.generate(userLoginReq.getUsername());
            return ResponseVo.getSuccess(new UserLoginRes(authToken));
        } else {
            return ResponseVo.getFailure(ResponseStatusEnum.USER_AUTH_FAILURE);
        }
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseVo info() {
        return ResponseVo.getSuccess("用户信息");
    }

}
