package com.priv.jdnights.api.contents.dto;

import lombok.Data;

@Data
public class PopularYoutubeContentDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private Long likeCount;
    private Long viewCount;
}

