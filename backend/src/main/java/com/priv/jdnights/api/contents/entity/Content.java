package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentLang> contentLangList = new ArrayList<>();

    private String thumbnailUrl;

    private String externalCreatedAt;

    private String externalUpdateAt;

    private Content(Long externalId, String contentName, String thumbnailUrl, String externalCreatedAt, String externalUpdateAt) {
        this.externalId = externalId;
        this.thumbnailUrl = thumbnailUrl;
        this.externalCreatedAt = externalCreatedAt;
        this.externalUpdateAt = externalUpdateAt;
        this.contentLangList = ContentLang.createAllLang(this);
        //this.contentName = contentName;
    }

    public static Content generateByNextClass(NextClassContentDto nextClass) {
        return new Content(nextClass.getNcId(), nextClass.getContentName(),
                nextClass.getThumbnailUrl(), nextClass.getCreatedAt(), nextClass.getUpdatedAt());
    }


    // 따로 넥클 정보는 fk로 관리
}
