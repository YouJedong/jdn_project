package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.YoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.priv.jdnights.common.Constants;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YoutubeContentRepository extends JpaRepository<YoutubeContent, Long> {

    YoutubeContent findByContentType(String contentType);

    @Query("""
            SELECT      y
            FROM        YoutubeContent y
            JOIN FETCH  y.contentLangList cl
            WHERE       y.videoType = :videoType
            AND         cl.langCode = :langCode
            ORDER BY    y.viewCount DESC
            """)
    List<YoutubeContent> findPopularYoutubeContents(VideoType videoType, String langCode, Pageable pageable);
}
