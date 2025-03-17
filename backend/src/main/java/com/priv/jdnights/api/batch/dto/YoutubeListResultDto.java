package com.priv.jdnights.api.batch.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YoutubeListResultDto {

    private String nextPageToken;

    private List<YoutubeContentDto> youtubeContentDtoList = new ArrayList<>();
}
