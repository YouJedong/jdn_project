package com.priv.jdnights.common.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    public ApiResponse() {
        this.code = "200";
        this.message = "success";
    }

    public ApiResponse(T data) {
        this.code = "200";
        this.message = "success";
        this.data = data;
    }
}
