package com.priv.jdnights.api.contents.controller;

import com.priv.jdnights.api.contents.dto.PopularYoutubeContentDto;
import com.priv.jdnights.api.contents.enums.VideoType;
import com.priv.jdnights.api.contents.service.ContentService;
import com.priv.jdnights.common.dto.ApiResponse;
import com.priv.jdnights.common.dto.ResultMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentRestController {

    private final ContentService contentService;

    @GetMapping("/home")
    public ResultMap getHomeContents() {
        System.out.println("왔니");

        return contentService.getHomeContents();
    }
    @GetMapping("/yt/popular/{videoType}")
    public ApiResponse<List<PopularYoutubeContentDto>> getPopularYoutubeContents(@PathVariable VideoType videoType) {
        return new ApiResponse<>(contentService.getPopularYoutubeContents(videoType));
    }

}

