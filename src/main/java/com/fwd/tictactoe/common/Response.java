package com.fwd.tictactoe.common;



import com.fwd.tictactoe.common.enums.ResponsCodeTypeEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T result;
    public static final Map<Integer, String> RESPONSE_MAP = new HashMap();

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getResult() {
        return this.result;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Response() {
        this.code = ResponsCodeTypeEnum.SUCCESS.getCode();
        this.message = ResponsCodeTypeEnum.SUCCESS.getMessage();
    }
}
