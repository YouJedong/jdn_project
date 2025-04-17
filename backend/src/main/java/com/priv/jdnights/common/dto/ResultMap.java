package com.priv.jdnights.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class ResultMap {
    private String code = "200";
    private Object data;

    public ResultMap(Object data) {
        this.code = "200";
        this.data = data;
    }
}
