package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.contents.enums.ContentStatus;
import com.priv.jdnights.common.Constants;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter@Setter
@DiscriminatorValue("NC")
public class NextClassContent extends Content {

    private Integer orderCount;

    private Integer rating;

    private Integer price;

    private String externalCreatedAt;

    private String externalUpdateAt;

    public static NextClassContent createByNextClass(NextClassContentDto dto, List<ContentLang> langList) {
        NextClassContent nextClassContent = new NextClassContent();
        nextClassContent.setExternalId(dto.getNcId().toString());
        nextClassContent.setThumbnailUrl(dto.getThumbnailUrl());
        nextClassContent.setRating(dto.getRating());
        nextClassContent.setPrice(dto.getPrice());
        nextClassContent.setOrderCount(dto.getStudentCount());
        nextClassContent.setExternalCreatedAt(dto.getCreatedAt());
        nextClassContent.setExternalUpdateAt(dto.getUpdatedAt());
        nextClassContent.setStatus(ContentStatus.HIDDEN);
        nextClassContent.addLangList(langList);

        return nextClassContent;
    }

    public void updateByNextClass(NextClassContentDto dto) {
        if (!Objects.equals(this.getPrice(), dto.getPrice())) {
            this.setPrice(dto.getPrice());
        }
        if (!Objects.equals(this.getRating(), dto.getRating())) {
            this.setRating(dto.getRating());
        }
        if (!Objects.equals(this.getOrderCount(), dto.getStudentCount())) {
            this.setOrderCount(dto.getStudentCount());
        }
        if (!Objects.equals(this.getExternalUpdateAt(), dto.getUpdatedAt())) {
            this.setExternalUpdateAt(dto.getUpdatedAt());
        }
        if (!Objects.equals(this.getThumbnailUrl(), dto.getThumbnailUrl())) {
            this.setThumbnailUrl(dto.getThumbnailUrl());
        }
    }
}
