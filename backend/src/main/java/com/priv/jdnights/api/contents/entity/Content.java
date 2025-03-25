package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.enums.ContentStatus;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.priv.jdnights.common.Constants.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    private String contentType;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentLang> contentLangList = new ArrayList<>();

    private String thumbnailUrl;

    private Integer rating;

    private Integer price;

    private Integer studentCount;

    private Long viewCount;

    private Long likeCount;

    private Long commentCount;

    private String externalCreatedAt;

    private String externalUpdateAt;

    @Enumerated(EnumType.STRING)
    private ContentStatus status;

    private void addLangList(List<ContentLang> langList) {
        for (ContentLang contentLang : langList) {
            contentLangList.add(contentLang);
            contentLang.setContent(this);
        }
    }

    public static Content createByNextClass(NextClassContentDto dto, List<ContentLang> langList) {
        Content nextClassContent = new Content();
        nextClassContent.setExternalId(dto.getNcId().toString());
        nextClassContent.setContentType(ContentType.NEXT_CLASS);
        nextClassContent.setThumbnailUrl(dto.getThumbnailUrl());
        nextClassContent.setRating(dto.getRating());
        nextClassContent.setPrice(dto.getPrice());
        nextClassContent.setStudentCount(dto.getStudentCount());
        nextClassContent.setExternalCreatedAt(dto.getCreatedAt());
        nextClassContent.setExternalUpdateAt(dto.getUpdatedAt());
        nextClassContent.setStatus(ContentStatus.HIDDEN);
        nextClassContent.addLangList(langList);

        return nextClassContent;
    }

    public static Content createByYoutube(YoutubeContentDto dto, ArrayList<ContentLang> langList) {
        Content youtubeContent = new Content();
        youtubeContent.setExternalId(dto.getVideoId());
        youtubeContent.setContentType(ContentType.YOUTUBE);
        youtubeContent.setThumbnailUrl(dto.getThumbnailUrl());
        youtubeContent.setViewCount(dto.getViewCount());
        youtubeContent.setLikeCount(dto.getLikeCount());
        youtubeContent.setCommentCount(dto.getCommentCount());
        youtubeContent.setExternalCreatedAt(dto.getPublishedAt());
        youtubeContent.setStatus(ContentStatus.HIDDEN);
        youtubeContent.addLangList(langList);

        return youtubeContent;
    }


    public void updateByNextClass(NextClassContentDto dto) {
        if (!this.price.equals(dto.getPrice())) {
            this.price = dto.getPrice();
        }
        if (!this.rating.equals(dto.getRating())) {
            this.rating = dto.getRating();
        }
        if (!this.studentCount.equals(dto.getStudentCount())) {
            this.studentCount = dto.getStudentCount();
        }
        if (!this.externalUpdateAt.equals(dto.getUpdatedAt())) {
            this.externalUpdateAt = dto.getUpdatedAt();
        }
        if (!this.thumbnailUrl.equals(dto.getThumbnailUrl())) {
            this.thumbnailUrl = dto.getThumbnailUrl();
        }
    }

    public void updateByYoutube(YoutubeContentDto dto) {
        if (!this.viewCount.equals(dto.getViewCount())) {
            this.viewCount = dto.getViewCount();
        }
        if (!this.likeCount.equals(dto.getLikeCount())) {
            this.likeCount = dto.getLikeCount();
        }
        if (!this.commentCount.equals(dto.getCommentCount())) {
            this.commentCount = dto.getCommentCount();
        }
    }
}
