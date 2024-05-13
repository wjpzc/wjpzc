package cn.xypt.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static Result success() {
        return new Result(20000, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(20000, "success", data);
    }

    public static <T> Result<T> success(T data,String message) {
        return new Result<>(20000, message, data);
    }

    public static Result fail() {
        return new Result(201, "fail", null);
    }

    public static Result fail(String message) {
        return new Result(201, message, null);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(201, message, null);
    }




}
