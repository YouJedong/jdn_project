package com.priv.jdnights.api.contents.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PopularNextClassContentDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private Integer rating;
    private Integer orderCount;

    @QueryProjection
    public PopularNextClassContentDto(Long id, String contentName, String thumbnailUrl, Integer rating, Integer orderCount) {
        this.id = id;
        this.contentName = contentName;
        this.thumbnailUrl = thumbnailUrl;
        this.rating = rating;
        this.orderCount = orderCount;
    }

}

