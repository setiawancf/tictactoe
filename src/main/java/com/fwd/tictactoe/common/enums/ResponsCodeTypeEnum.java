package com.fwd.tictactoe.common.enums;


import com.fwd.tictactoe.common.Response;

public enum ResponsCodeTypeEnum {


    SUCCESS(200, "OK"),
    FAILURE(999, "Kesalahan tidak dikenal"),
    ;

    private Integer code;
    private String message;

    private ResponsCodeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    static {
        ResponsCodeTypeEnum[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            ResponsCodeTypeEnum responsCodeTypeEnum = var0[var2];
            Response.RESPONSE_MAP.put(responsCodeTypeEnum.getCode(), responsCodeTypeEnum.getMessage());
        }

    }
}
