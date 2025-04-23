package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.FullScoreContentDto;
import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.dto.HomeContentDto;
import com.priv.jdnights.api.contents.enums.ContentStatus;
import com.priv.jdnights.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.priv.jdnights.common.Constants.*;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "content_type")
@NoArgsConstructor
public abstract class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @Column(name = "content_type", insertable = false, updatable = false)
    private String contentType;

    @Column(nullable = false)
    private String externalId;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentLang> contentLangList = new ArrayList<>();

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private ContentStatus status;

    public void addLangList(List<ContentLang> langList) {
        for (ContentLang contentLang : langList) {
            contentLangList.add(contentLang);
            contentLang.setContent(this);
        }
    }

}
