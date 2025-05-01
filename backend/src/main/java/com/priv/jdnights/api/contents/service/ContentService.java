package com.priv.jdnights.api.contents.service;

import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.dto.HomeContentDto;
import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.dto.TestDto;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.YoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.priv.jdnights.api.contents.mapper.ContentMapper;
import com.priv.jdnights.api.contents.repository.ContentRepository;
import com.priv.jdnights.api.contents.repository.YoutubeContentRepository;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.dto.ResultMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final YoutubeContentRepository youtubeContentRepository;

    public ResultMap getHomeContents() {

//        List<HomeContentDto> youtubeContents = youtubeContentRepository.findByContentType(Constants.ContentType.YOUTUBE)
//                .stream()
//                .map(this::toYoutubeDto)
//                .toList();

//        TestDto testDto = new TestDto(youtubeContents);
        
        return new ResultMap();
    }

    private HomeContentDto toYoutubeDto(Content content) {
        return new HomeContentDto(
                content.getId(),
                content.getThumbnailUrl()
        );
    }

    public List<PopularYoutubeContentDto> getPopularYoutubeContents(VideoType videoType) {

        List<YoutubeContent> contents = youtubeContentRepository.findPopularYoutubeContents(videoType, Constants.LangCode.KO, Pageable.ofSize(8));

        System.out.println("contents = " + contents);

        return contentMapper.toPopularDtoList(contents);
    }
}
