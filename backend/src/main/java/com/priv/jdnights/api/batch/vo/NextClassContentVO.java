package com.priv.jdnights.api.batch.vo;

import lombok.Data;

@Data
public class NextClassContentVO {

    private Long ncId;
    private String contentName;
    private String thumbnailUrl;
    private Integer rating;
    private Integer price;
    private Integer studentCount;
    private String createdAt;
    private String updatedAt;
}
