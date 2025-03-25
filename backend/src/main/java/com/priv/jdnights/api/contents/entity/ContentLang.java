package com.priv.jdnights.api.contents.entity;

import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ContentLang extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_lang_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(nullable = false)
    private String langCode;

    private String contentName;

    @Column(length = 2000)
    private String description;


    public static ContentLang createContentLang(String langCode, String contentName, String description) {
        ContentLang contentLang = new ContentLang();
        contentLang.setLangCode(langCode);
        contentLang.setContentName(contentName);
        contentLang.setDescription(description);

        return contentLang;
    }

    public void updateByNextClass(NextClassContentDto dto) {
        this.contentName = dto.getContentName();
    }
}
