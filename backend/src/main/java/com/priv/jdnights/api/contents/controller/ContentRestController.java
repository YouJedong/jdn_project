package com.priv.jdnights.api.contents.controller;

import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.contents.dto.PopularFullScoreContentDto;
import com.priv.jdnights.api.contents.dto.PopularNextClassContentDto;
import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.dto.YoutubeContentListDto;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.priv.jdnights.api.contents.repository.YoutubeContentSearchDto;
import com.priv.jdnights.api.contents.service.ContentService;
import com.priv.jdnights.common.dto.ApiResponse;
import com.priv.jdnights.common.dto.PaginatedResponse;
import com.priv.jdnights.common.dto.ResultMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentRestController {

    private final ContentService contentService;

    @GetMapping("/yt/popular/{videoType}")
    public ApiResponse<List<PopularYoutubeContentDto>> getPopularYoutubeContents(@PathVariable VideoType videoType) {
        return new ApiResponse<>(contentService.getPopularYoutubeContents(videoType));
    }

    @GetMapping("/nc/popular")
    public ApiResponse<List<PopularNextClassContentDto>> getPopularNextClassContents() {
        return new ApiResponse<>(contentService.getPopularNextClassContents());
    }

    @GetMapping("/fs/popular")
    public ApiResponse<List<PopularFullScoreContentDto>> getPopularFullScoreContents() {
        return new ApiResponse<>(contentService.getPopularFullScoreContents());
    }

    @GetMapping("/yt")
    public ApiResponse<PaginatedResponse<YoutubeContentListDto>> getYoutubeContents(YoutubeContentSearchDto searchDto, Pageable pageable) {
        return new ApiResponse<>(contentService.getYoutubeContents(searchDto, pageable));
    }
}

