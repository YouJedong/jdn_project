package com.priv.jdnights.api.contents.service;

import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.dto.*;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.YoutubeContent;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.priv.jdnights.api.contents.mapper.ContentMapper;
import com.priv.jdnights.api.contents.repository.*;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.config.LangContext;
import com.priv.jdnights.common.dto.PaginatedResponse;
import com.priv.jdnights.common.dto.ResultMap;
import com.priv.jdnights.common.exception.LogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final YoutubeContentRepository youtubeContentRepository;
    private final NextClassContentRepository nextClassContentRepository;

    private final FullScoreContentRepository fullScoreContentRepository;

    public List<PopularYoutubeContentDto> getPopularYoutubeContents(VideoType videoType) {
        List<PopularYoutubeContentDto> contents = youtubeContentRepository.findPopularYoutubeContents(videoType, LangContext.get(), Pageable.ofSize(8));
        return contents;
    }

    public List<PopularNextClassContentDto> getPopularNextClassContents() {
        List<PopularNextClassContentDto> contents = nextClassContentRepository.findPopularContents(LangContext.get(), Pageable.ofSize(8));
        return contents;
    }

    public List<PopularFullScoreContentDto> getPopularFullScoreContents() {
        List<PopularFullScoreContentDto> contents = fullScoreContentRepository.findPopularContents(LangContext.get(), Pageable.ofSize(8));
        return contents;
    }

    public PaginatedResponse<YoutubeContentListDto> getYoutubeContents(YoutubeContentSearchDto searchDto, Pageable pageable) {
        searchDto.setLangCode(LangContext.get());

        Page<YoutubeContentListDto> pageResult = youtubeContentRepository.findContents(searchDto, pageable);

        PaginatedResponse<YoutubeContentListDto> response = new PaginatedResponse<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );

        return response;
    }

    public YoutubeContentDetailDto getYoutubeContentDetail(Long id) {
        return youtubeContentRepository.findDetailInfo(id, LangContext.get());
    }
}
