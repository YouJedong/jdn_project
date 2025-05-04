package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.dto.QPopularYoutubeContentDto;
import com.priv.jdnights.api.contents.entity.QContentLang;
import com.priv.jdnights.api.contents.entity.QYoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class YoutubeContentQueryRepositoryImpl implements YoutubeContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PopularYoutubeContentDto> findPopularYoutubeContents(VideoType videoType, String langCode, Pageable pageable) {
        QYoutubeContent y = QYoutubeContent.youtubeContent;
        QContentLang cl = QContentLang.contentLang;

        return queryFactory
                .select(new QPopularYoutubeContentDto(
                    y.id,
                    cl.contentName,
                    y.thumbnailUrl,
                    y.likeCount,
                    y.viewCount
                ))
                .from(y)
                .join(y.contentLangList, cl)
                .where(
                    y.videoType.eq(videoType),
                    cl.langCode.eq(langCode)
                )
                .orderBy(y.viewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
