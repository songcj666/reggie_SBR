package com.scj.reggie_take_out.common;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Long total;
    private Object data;


    public static Result success(Object data, Long total) {
        Result res = new Result();
        res.setData(data);
        res.setCode(200);
        res.setMsg("成功");
        res.setTotal(total);
        return res;
    }

    public static Result fail() {
        Result res = new Result();
        res.setCode(400);
        res.setMsg("失败");
        return res;
    }

    public static Result respon(int code, String msg, Long total, Object data) {
        Result res = new Result();
        res.setData(data);
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        return res;
    }

}
