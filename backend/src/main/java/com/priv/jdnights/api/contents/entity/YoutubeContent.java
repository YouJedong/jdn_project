package com.priv.jdnights.api.contents.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("YT")
public class YoutubeContent extends Content {

    private Long commentCount;

    private Long likeCount;

    private Long viewCount;

    private String externalCreatedAt;
}
