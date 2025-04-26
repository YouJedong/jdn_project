package com.priv.jdnights.api.contents.repository;

import com.priv.jdnights.api.contents.entity.YoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YoutubeContentRepository extends JpaRepository<YoutubeContent, Long> {

    YoutubeContent findByContentType(String contentType);


    List<YoutubeContent> findTop8ByVideoTypeOrderByViewCountDesc(VideoType videoType);
}
