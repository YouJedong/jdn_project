package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.FullScoreContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FullScoreContentRepository extends JpaRepository<FullScoreContent, Long>, FullScoreContentQueryRepository  {
}
