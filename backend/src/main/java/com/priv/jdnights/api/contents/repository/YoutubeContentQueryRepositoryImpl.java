package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.*;
import com.priv.jdnights.api.contents.entity.QContentLang;
import com.priv.jdnights.api.contents.entity.QYoutubeContent;
import com.priv.jdnights.api.contents.enums.ContentStatus;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
                    y.status.eq(ContentStatus.VISIBLE),
                    y.videoType.eq(videoType),
                    cl.langCode.eq(langCode)
                )
                .orderBy(y.viewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Page<YoutubeContentListDto> findContents(YoutubeContentSearchDto searchDto, Pageable pageable) {
        QYoutubeContent y = QYoutubeContent.youtubeContent;
        QContentLang cl = QContentLang.contentLang;

        List<YoutubeContentListDto> contentList = queryFactory
                .select(new QYoutubeContentListDto(
                        y.id,
                        cl.contentName,
                        y.thumbnailUrl,
                        y.likeCount,
                        y.viewCount
                ))
                .from(y)
                .join(y.contentLangList, cl)
                .where(
                    y.status.eq(ContentStatus.VISIBLE),
                    cl.langCode.eq(searchDto.getLangCode()),
                    keywordLikeCondition(cl, searchDto.getKeyword())
                )
                .orderBy(this.getOrder(searchDto.getOrderType(), y))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(y.count())
                .from(y)
                .join(y.contentLangList, cl)
                .where(
                    cl.langCode.eq(searchDto.getLangCode()),
                    keywordLikeCondition(cl, searchDto.getKeyword())
                )
                .fetchOne();

        return new PageImpl<>(contentList, pageable, total);
    }

    @Override
    public YoutubeContentDetailDto findDetailInfo(Long id, String langCode) {
        QYoutubeContent yc = QYoutubeContent.youtubeContent;
        QContentLang cl = QContentLang.contentLang;

        return queryFactory
                .select(new QYoutubeContentDetailDto(
                        yc.id,
                        cl.contentName,
                        yc.thumbnailUrl,
                        yc.likeCount,
                        yc.viewCount,
                        yc.commentCount,
                        yc.videoType,
                        cl.description,
                        yc.externalId
                ))
                .from(yc)
                .join(yc.contentLangList, cl)
                .where(
                    yc.id.eq(id),
                    cl.langCode.eq(langCode)
                )
                .fetchOne();
    }

    private OrderSpecifier<?> getOrder(String orderType, QYoutubeContent y) {
        if (orderType == null) return y.id.desc(); // 기본 정렬

        switch (orderType) {
            case "popular":
                return y.viewCount.desc();
            case "latest":
                return y.externalCreatedAt.desc();
            case "oldest":
                return y.externalCreatedAt.asc();
        }

        return y.viewCount.desc();
    }

    private BooleanExpression keywordLikeCondition(QContentLang cl, String keyword) {
        return StringUtils.hasText(keyword)
                ? cl.contentName.containsIgnoreCase(keyword).or(cl.description.containsIgnoreCase(keyword))
                : null;
    }

}
