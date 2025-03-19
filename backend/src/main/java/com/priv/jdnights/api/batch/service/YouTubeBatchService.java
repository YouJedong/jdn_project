package com.priv.jdnights.api.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.batch.dto.YoutubeContentDto;
import com.priv.jdnights.api.batch.dto.YoutubeDetailDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Value("${external.apis.youtube.detail-url}")
    private String YT_DETAIL_URL;

    @Value("${external.apis.youtube.playlist-id}")
    private String PLAYLIST_ID;

    @Value("${external.apis.youtube.key}")
    private String KEY;




    public void getYouTubeContentsInfo() {

        String nextPageToken = null;
        try {
            int test = 0;
            List<YoutubeContentDto> contentDtoList = new ArrayList<>();
            do {
                YoutubeListResultDto listResultDto = fetchYouTubeList(nextPageToken);

                if (listResultDto == null || listResultDto.getYoutubeContentDtoList().isEmpty()) {
                    break;
                }

                nextPageToken = listResultDto.getNextPageToken();
                contentDtoList.addAll(listResultDto.getYoutubeContentDtoList());

                test++;
                if (test > 5) {
                    break;
                }

            } while (nextPageToken != null && !nextPageToken.isEmpty());


            if (!contentDtoList.isEmpty()) {

                List<String> videoIds = contentDtoList.stream()
                        .map(YoutubeContentDto::getVideoId)
                        .collect(Collectors.toList());

                int batchSize = 50;
                for (int i = 0; i < videoIds.size(); i += batchSize) {
                    List<String> batch = videoIds.subList(i, Math.min(i + batchSize, videoIds.size()));
                    Map<String, YoutubeDetailDto> detailMap = fetchYouTubeDetails(batch);

                    // 조회된 통계 정보를 contentDtoList에 매핑
                    for (YoutubeContentDto dto : contentDtoList) {
                        if (detailMap.containsKey(dto.getVideoId())) {
                            YoutubeDetailDto detailDto = detailMap.get(dto.getVideoId());
                            dto.setViewCount(detailDto.getViewCount());
                            dto.setLikeCount(detailDto.getLikeCount());
                            dto.setCommentCount(detailDto.getCommentCount());
                        }
                    }
                }

                for (YoutubeContentDto contentDto : contentDtoList) {
                    System.out.println("contentDto = " + contentDto);
                }
            }

            // 이력 쌓기

        } catch (LogicException e) {
            log.error("유튜브 배치 실패", e.toString());
        }
    }

    private YoutubeListResultDto fetchYouTubeList(String nextPageToken) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(YT_BASE_URL)
                .queryParam("playlistId", PLAYLIST_ID)
                .queryParam("key", KEY);

        if (nextPageToken != null && !nextPageToken.isEmpty()) {
            uriBuilder.queryParam("pageToken", nextPageToken);
        }

        String url = uriBuilder.toUriString();
        String result = webClientUtil.get(url, String.class);

        return this.parseContentsByJson(result);
    }

    private YoutubeListResultDto parseContentsByJson(String json) {
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

    /**
     * 유튜브 비디오 통계 정보 가져오기
     */
    private Map<String, YoutubeDetailDto> fetchYouTubeDetails(List<String> videoIds) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(YT_DETAIL_URL)
                .queryParam("id", String.join(",", videoIds))
                .queryParam("part", "statistics")
                .queryParam("key", KEY);

        String url = uriBuilder.toUriString();
        String result = webClientUtil.get(url, String.class);

        return parseDetailJson(result);
    }

    /**
     * JSON 결과를 파싱하여 통계 정보 매핑
     */
    private Map<String, YoutubeDetailDto> parseDetailJson(String json) {
        Map<String, YoutubeDetailDto> detailMap = new HashMap<>();

        try {
            JsonNode root = new ObjectMapper().readTree(json);
            JsonNode items = root.get("items");

            if (items != null) {
                for (JsonNode item : items) {
                    String videoId = item.get("id").asText();
                    JsonNode statistics = item.get("statistics");

                    YoutubeDetailDto detailDto = new YoutubeDetailDto();
                    detailDto.setViewCount(statistics.has("viewCount") ? statistics.get("viewCount").asLong() : 0);
                    detailDto.setLikeCount(statistics.has("likeCount") ? statistics.get("likeCount").asLong() : 0);
                    detailDto.setCommentCount(statistics.has("commentCount") ? statistics.get("commentCount").asLong() : 0);
                    detailMap.put(videoId,detailDto);
                }
            }
        } catch (Exception e) {
            log.error("유튜브 통계 JSON 파싱 실패", e);
        }

        return detailMap;
    }

}
