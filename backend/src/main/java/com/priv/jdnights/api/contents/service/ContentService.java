package com.priv.jdnights.api.contents.service;

import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.dto.HomeContentDto;
import com.priv.jdnights.api.contents.dto.TestDto;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.repository.ContentRepository;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.dto.ResultMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public ResultMap getHomeContents() {

        List<HomeContentDto> youtubeContents = contentRepository.findByContentType(Constants.ContentType.YOUTUBE)
                .stream()
                .map(this::toYoutubeDto)
                .toList();

        TestDto testDto = new TestDto(youtubeContents);
        
        return new ResultMap(testDto);
    }

    private HomeContentDto toYoutubeDto(Content content) {
        return new HomeContentDto(
                content.getId(),
                content.getThumbnailUrl()
        );
    }
}
