package com.priv.jdnights.api.contents.dto;

import lombok.Data;

@Data
public class YoutubeContentDetailDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private int likeCount;
    private int viewCount;
    private String videoId;

}

