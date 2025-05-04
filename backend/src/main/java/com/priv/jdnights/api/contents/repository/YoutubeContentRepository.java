package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.YoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.priv.jdnights.common.Constants;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YoutubeContentRepository extends JpaRepository<YoutubeContent, Long>, YoutubeContentQueryRepository {

}
