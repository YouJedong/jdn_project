package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.FullScoreContentDto;
import com.priv.jdnights.api.contents.enums.ContentStatus;
import com.priv.jdnights.common.Constants;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Entity
@Getter @Setter
@DiscriminatorValue("FS")
public class FullScoreContent extends Content {

    private Integer orderCount;

    private Integer price;

    private Long viewCount;

    public static FullScoreContent createByFullScore(FullScoreContentDto dto, ArrayList<ContentLang> langList) {
        FullScoreContent fullScoreContent = new FullScoreContent();
        fullScoreContent.setExternalId(dto.getPrdcode());
        fullScoreContent.setThumbnailUrl(dto.getThumbnailUrl());
        fullScoreContent.setPrice(dto.getPrice());
        fullScoreContent.setViewCount(dto.getViewCount());
        fullScoreContent.setOrderCount(dto.getOrderCount());
        fullScoreContent.setStatus(ContentStatus.HIDDEN);
        fullScoreContent.addLangList(langList);

        return fullScoreContent;
    }

    public void updateByFullScore(FullScoreContentDto dto) {
        if (!Objects.equals(this.getViewCount(), dto.getViewCount())) {
            this.setViewCount(dto.getViewCount());
        }
        if (!Objects.equals(this.getOrderCount(), dto.getOrderCount())) {
            this.setOrderCount(dto.getOrderCount());
        }
    }
}
