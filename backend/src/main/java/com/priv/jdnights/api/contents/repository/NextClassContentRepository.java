package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.NextClassContent;
import com.priv.jdnights.api.contents.entity.YoutubeContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NextClassContentRepository extends JpaRepository<NextClassContent, Long>, NextClassContentQueryRepository {

}
