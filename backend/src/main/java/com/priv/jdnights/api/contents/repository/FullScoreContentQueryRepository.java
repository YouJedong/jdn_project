package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.dto.PopularFullScoreContentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FullScoreContentQueryRepository {

    List<PopularFullScoreContentDto> findPopularContents(String langCode, Pageable pageable);
}
