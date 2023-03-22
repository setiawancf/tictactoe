package com.fwd.tictactoe.common.enums;

/**
 * @author Setiawan Candrafu
 * @date 3/22/2023
 */
public enum StatusEnums {


    WIN(1, "WIN"),
    LOSE(2, "LOSE"),
    DRAW(3, "DRAW"),
    ;

    private Integer code;
    private String message;

    private StatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static StatusEnums getById(Integer id) {
        for (StatusEnums e : values()) {
            if (e.code.equals(id)) return e;
        }
        return null;
    }
}
