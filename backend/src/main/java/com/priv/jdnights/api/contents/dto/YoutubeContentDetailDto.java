package com.priv.jdnights.api.contents.dto;

import com.priv.jdnights.api.contents.enums.VideoType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeContentDetailDto {

    private Long id;
    private String contentName;
    private String thumbnailUrl;
    private Long likeCount;
    private Long viewCount;
    private Long commentCount;
    private VideoType videoType;
    private String description;

    @QueryProjection
    public YoutubeContentDetailDto(Long id, String contentName, String thumbnailUrl, Long likeCount, Long viewCount
        , Long commentCount, VideoType videoType, String description
    ) {
        this.id = id;
        this.contentName = contentName;
        this.thumbnailUrl = thumbnailUrl;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.videoType = videoType;
        this.description = description;
    }
}

