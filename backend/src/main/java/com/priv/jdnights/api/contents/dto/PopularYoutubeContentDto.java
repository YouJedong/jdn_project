package com.priv.jdnights.api.contents.dto;

import com.priv.jdnights.api.contents.entity.ContentLang;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class PopularYoutubeContentDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private Long likeCount;
    private Long viewCount;

    @QueryProjection
    public PopularYoutubeContentDto(Long id, String contentName, String thumbnailUrl, Long likeCount, Long viewCount) {
        this.id = id;
        this.contentName = contentName;
        this.thumbnailUrl = thumbnailUrl;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }

}

