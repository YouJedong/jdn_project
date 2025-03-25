package com.priv.jdnights.api.batch.dto;

import lombok.Data;

@Data
public class YoutubeContentDto {

    private String videoId;

    private String title;

    private String description;

    private String thumbnailUrl;

    private String publishedAt;

    private Long viewCount;

    private Long likeCount;

    private Long commentCount;

}


