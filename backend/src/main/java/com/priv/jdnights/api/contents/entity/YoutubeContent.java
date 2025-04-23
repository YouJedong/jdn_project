package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.enums.ContentStatus;
import com.priv.jdnights.common.Constants;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Entity
@Getter
@Setter
@DiscriminatorValue("YT")
public class YoutubeContent extends Content {

    private Long commentCount;

    private Long likeCount;

    private Long viewCount;

    private String externalCreatedAt;

    public static YoutubeContent createByYoutube(YoutubeContentDto dto, ArrayList<ContentLang> langList) {
        YoutubeContent youtubeContent = new YoutubeContent();
        youtubeContent.setExternalId(dto.getVideoId());
        youtubeContent.setThumbnailUrl(dto.getThumbnailUrl());
        youtubeContent.setViewCount(dto.getViewCount());
        youtubeContent.setLikeCount(dto.getLikeCount());
        youtubeContent.setCommentCount(dto.getCommentCount());
        youtubeContent.setExternalCreatedAt(dto.getPublishedAt());
        youtubeContent.setStatus(ContentStatus.HIDDEN);
        youtubeContent.addLangList(langList);

        return youtubeContent;
    }

    public void updateByYoutube(YoutubeContentDto dto) {
        if (!Objects.equals(this.getViewCount(), dto.getViewCount())) {
            this.setViewCount(dto.getViewCount());
        }
        if (!Objects.equals(this.getLikeCount(), dto.getLikeCount())) {
            this.setLikeCount(dto.getLikeCount());
        }
        if (!Objects.equals(this.getCommentCount(), dto.getCommentCount())) {
            this.setCommentCount(dto.getCommentCount());
        }
    }
}
