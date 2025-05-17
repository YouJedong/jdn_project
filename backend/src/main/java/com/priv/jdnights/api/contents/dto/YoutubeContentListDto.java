package com.priv.jdnights.api.contents.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class YoutubeContentListDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private Long likeCount;
    private Long viewCount;

    @QueryProjection
    public YoutubeContentListDto(Long id, String contentName, String thumbnailUrl, Long likeCount, Long viewCount) {
        this.id = id;
        this.contentName = contentName;
        this.thumbnailUrl = thumbnailUrl;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }
}

