package com.priv.jdnights.api.batch.dto;

import lombok.Data;

@Data
public class FullScoreContentDto {

    private String prdcode;
    private String title;
    private Long viewCount;
    private Integer orderCount;
    private Integer price;
    private String thumbnailUrl;

}
