package com.dfy.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: 段辉
 * @time: 2020/3/30 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginReq {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
