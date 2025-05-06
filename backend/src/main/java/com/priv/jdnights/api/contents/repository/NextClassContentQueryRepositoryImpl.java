package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.PopularNextClassContentDto;
import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.dto.QPopularNextClassContentDto;
import com.priv.jdnights.api.contents.dto.QPopularYoutubeContentDto;
import com.priv.jdnights.api.contents.entity.QContentLang;
import com.priv.jdnights.api.contents.entity.QNextClassContent;
import com.priv.jdnights.api.contents.entity.QYoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class NextClassContentQueryRepositoryImpl implements NextClassContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PopularNextClassContentDto> findPopularContents(String langCode, Pageable pageable) {
        QNextClassContent n = QNextClassContent.nextClassContent;
        QContentLang cl = QContentLang.contentLang;

        return queryFactory
                .select(new QPopularNextClassContentDto(
                    n.id,
                    cl.contentName,
                    n.thumbnailUrl,
                    n.rating,
                    n.orderCount
                ))
                .from(n)
                .join(n.contentLangList, cl)
                .where(
                    cl.langCode.eq(langCode)
                )
                .orderBy(n.orderCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
