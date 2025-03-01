package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.NextClassContentDto;
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
    private Long externalId;

    @Column(nullable = false)
    private String contentType;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentLang> contentLangList = new ArrayList<>();

    private String thumbnailUrl;

    private Integer rating;

    private Integer price;

    private Integer studentCount;

    private String externalCreatedAt;

    private String externalUpdateAt;

    private void addLangList(List<ContentLang> langList) {
        for (ContentLang contentLang : langList) {
            contentLangList.add(contentLang);
            contentLang.setContent(this);
        }
    }

    public static Content createByNextClass(NextClassContentDto dto, List<ContentLang> langList) {
        Content nextClassContent = new Content();
        nextClassContent.setExternalId(dto.getNcId());
        nextClassContent.setContentType(ContentType.NEXT_CLASS);
        nextClassContent.setThumbnailUrl(dto.getThumbnailUrl());
        nextClassContent.setRating(dto.getRating());
        nextClassContent.setPrice(dto.getPrice());
        nextClassContent.setStudentCount(dto.getStudentCount());
        nextClassContent.setExternalCreatedAt(dto.getCreatedAt());
        nextClassContent.setExternalUpdateAt(dto.getUpdatedAt());
        nextClassContent.addLangList(langList);

        return nextClassContent;
    }


}
