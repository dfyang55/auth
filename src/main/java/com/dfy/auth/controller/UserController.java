package com.dfy.auth.controller;

import com.dfy.auth.annotation.DecryptAndVerify;
import com.dfy.auth.enums.ResponseStatusEnum;
import com.dfy.auth.vo.EncryptedReq;
import com.dfy.auth.vo.ResponseVo;
import com.dfy.auth.vo.UserLoginReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户控制层
 * @author: DFY
 * @time: 2020/3/30 10:58
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @PostMapping("/login")
    @DecryptAndVerify(decryptedClass = UserLoginReq.class)
    public ResponseVo login(@RequestBody @Validated EncryptedReq<UserLoginReq> encryptedReq) {
        UserLoginReq userLoginReq = encryptedReq.getData();
        // TODO 从数据库核实用户登录信息，这里懒得查数据库了
        if (userLoginReq.getUsername().equals("admin") && userLoginReq.getPassword().equals("admin")) {
            return ResponseVo.getSuccess();
        } else {
            return ResponseVo.getFailure(ResponseStatusEnum.USER_AUTH_FAILURE);
        }
    }

}
