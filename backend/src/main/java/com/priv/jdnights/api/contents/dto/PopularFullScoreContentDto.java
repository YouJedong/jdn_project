package com.priv.jdnights.api.contents.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PopularFullScoreContentDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private Long viewCount;

    @QueryProjection
    public PopularFullScoreContentDto(Long id, String contentName, String thumbnailUrl, Long viewCount) {
        this.id = id;
        this.contentName = contentName;
        this.thumbnailUrl = thumbnailUrl;
        this.viewCount = viewCount;
    }
}
