package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.enums.VideoType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface YoutubeContentQueryRepository {
    List<PopularYoutubeContentDto> findPopularYoutubeContents(VideoType videoType, String langCode, Pageable pageable);

}
