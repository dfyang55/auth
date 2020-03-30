package com.dfy.auth.vo;

import com.dfy.auth.enums.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 响应
 * @author: DFY
 * @time: 2020/3/30 11:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo {

    private int status;

    private String msg;

    private Object data;

    public static ResponseVo getSuccess() {
        return new ResponseVo(ResponseStatusEnum.SUCCESS.getStatus(), ResponseStatusEnum.SUCCESS.getMsg(), null);
    }

    public static ResponseVo getFailure(ResponseStatusEnum responseStatusEnum) {
        return new ResponseVo(responseStatusEnum.getStatus(), responseStatusEnum.getMsg(), null);
    }
}
