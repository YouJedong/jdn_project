package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.dto.YoutubeContentDetailDto;
import com.priv.jdnights.api.contents.dto.YoutubeContentListDto;
import com.priv.jdnights.api.contents.enums.VideoType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface YoutubeContentQueryRepository {
    List<PopularYoutubeContentDto> findPopularYoutubeContents(VideoType videoType, String langCode, Pageable pageable);

    Page<YoutubeContentListDto> findContents(YoutubeContentSearchDto searchDto, Pageable pageable);

    YoutubeContentDetailDto findDetailInfo(Long id, String langCode);
}
