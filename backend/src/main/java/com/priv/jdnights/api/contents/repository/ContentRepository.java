package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.common.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Content findByExternalIdAndContentType(String externalId, String contentType);

    List<Content> findByContentType(String contentType);
}
