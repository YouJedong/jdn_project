package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.YoutubeContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface YoutubeContentRepository extends JpaRepository<YoutubeContent, Long>, YoutubeContentQueryRepository {

}
