package com.priv.jdnights.api.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.batch.dto.YoutubeListResultDto;
import com.priv.jdnights.common.exception.LogicException;
import com.priv.jdnights.common.utils.WebClientUtil;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Service
@Transactional
public class YouTubeBatchService {

    private static final Logger log = LogManager.getLogger(YouTubeBatchService.class);


    private final WebClientUtil webClientUtil;
    public YouTubeBatchService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }


    @Value("${external.apis.youtube.base-url}")
    private String YT_BASE_URL;

    @Value("${external.apis.youtube.playlist-id}")
    private String PLAYLIST_ID;

    @Value("${external.apis.youtube.key}")
    private String KEY;


    public void getYouTubeContentsInfo() {


        try {
            String url = UriComponentsBuilder.fromUriString(YT_BASE_URL)
                    .queryParam("playlistId", PLAYLIST_ID)
                    .queryParam("key", KEY)
                    .toUriString();
            String result = webClientUtil.get(url, String.class);
            System.out.println("result = " + result);

            YoutubeListResultDto listResultDto = this.getContentsByJson(result);

            System.out.println("listResultDto = " + listResultDto);
            for (YoutubeContentDto vo : listResultDto.getYoutubeContentDtoList()) {
                System.out.println("vo = " + vo);
            }

            // nextpagetoken으로 가져올지 정하기
            if (result != null) {

            }

            // 상세 api 호출

        } catch (LogicException e) {
            log.error("유튜브 배치 실패", e.toString());
        }
    }

    private YoutubeListResultDto getContentsByJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode errorNode = rootNode.path("error");
            if (!errorNode.isEmpty()) {
                String code = errorNode.path("code").asText();
                String msg = errorNode.path("message").asText();
                throw new LogicException(code, msg);
            }

            YoutubeListResultDto resultDto = new YoutubeListResultDto();
            String nextPageToken = rootNode.path("nextPageToken").asText();
            if (StringUtils.hasText(nextPageToken)) {
                resultDto.setNextPageToken(nextPageToken);
            }

            JsonNode items = rootNode.path("items");
            if (items.isArray()) {
                YoutubeContentDto contentDto = new YoutubeContentDto();
                for (JsonNode item : items) {
                    JsonNode itemInfo = item.path("snippet");
                    YoutubeContentDto content = new YoutubeContentDto();
                    content.setVideoId(itemInfo.path("resourceId").path("videoId").asText());
                    content.setTitle(itemInfo.path("title").asText());
                    content.setThumbnailUrl(itemInfo.path("thumbnails").path("maxres").path("url").asText());
                    content.setPublishedAt(itemInfo.path("publishedAt").asText());
                    resultDto.getYoutubeContentDtoList().add(content);
                }
            }

            return resultDto;
        } catch (Exception e) {
            throw new LogicException("400", "Contents JSON 파싱 오류 :" + e.toString());
        }

    }
}
