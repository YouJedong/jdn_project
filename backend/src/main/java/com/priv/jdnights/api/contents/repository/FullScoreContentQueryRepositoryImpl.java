package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.PopularFullScoreContentDto;
import com.priv.jdnights.api.contents.dto.QPopularFullScoreContentDto;
import com.priv.jdnights.api.contents.entity.QContentLang;
import com.priv.jdnights.api.contents.entity.QFullScoreContent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FullScoreContentQueryRepositoryImpl implements FullScoreContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PopularFullScoreContentDto> findPopularContents(String langCode, Pageable pageable) {
        QFullScoreContent f = QFullScoreContent.fullScoreContent;
        QContentLang cl = QContentLang.contentLang;

        return queryFactory
                .select(new QPopularFullScoreContentDto(
                    f.id,
                    cl.contentName,
                    f.thumbnailUrl,
                    f.viewCount
                ))
                .from(f)
                .join(f.contentLangList, cl)
                .where(
                    cl.langCode.eq(langCode)
                )
                .orderBy(f.orderCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
