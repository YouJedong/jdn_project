package com.priv.jdnights.api.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priv.jdnights.api.batch.vo.NextClassContentVO;
import com.priv.jdnights.common.exception.LogicException;
import com.priv.jdnights.common.utils.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NextClassBatchService {

    private final WebClientUtil webClientUtil;

    public NextClassBatchService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    @Value("${external.apis.nextclass.base-url}")
    private String NC_BASE_URL;

    public void getNextClassContentsInfo() {

        // 페이지 수 조회
        String url = UriComponentsBuilder.fromUriString(NC_BASE_URL)
                .queryParam("coursePage", 1)
                .toUriString();
        String result = webClientUtil.get(url, String.class);
        int totalPage = this.getTotalPageByJson(result);

        // insert or update
        if (totalPage > 0) {
            List<NextClassContentVO> contentList = new ArrayList<>();
            for (int i = 0; i < totalPage; i++) {
                int reqPage = i + 1;

                url = UriComponentsBuilder.fromUriString(NC_BASE_URL)
                        .queryParam("coursePage", reqPage)
                        .toUriString();
                result = webClientUtil.get(url, String.class);

                contentList.addAll(this.getContentsByJson(result));
            }

            for (NextClassContentVO vo : contentList) {
                System.out.println("vo = " + vo);
            }
        }

        // 이력 insert




    }

    public int getTotalPageByJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            return rootNode.path("pageProps").path("coursesPagination").path("totalPage").asInt();
        } catch (Exception e) {
            throw new LogicException("400", "TotalPage JSON 파싱 오류 :" + e.toString());
        }
    }

    private List<NextClassContentVO> getContentsByJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode items = rootNode.path("pageProps").path("courses").path("items");

            if (items.isArray()) {
                ArrayList<NextClassContentVO> contentList = new ArrayList<>();
                for (JsonNode item : items) {
                    NextClassContentVO content = new NextClassContentVO();
                    content.setNcId(item.path("id").asLong());
                    content.setContentName(item.path("name").asText());
                    content.setThumbnailUrl(item.path("thumbnailUrl").asText());
                    content.setRating(item.path("rating").asInt(0)); // 기본값 0
                    content.setPrice(item.path("price").asInt(0)); // 기본값 0
                    content.setStudentCount(item.path("studentCount").asInt(0));
                    content.setCreatedAt(item.path("createdAt").asText());
                    content.setUpdatedAt(item.path("updatedAt").asText());

                    contentList.add(content);
                }
                return contentList;
            } else {
                return new ArrayList<>();
            }

        } catch (Exception e) {
            throw new LogicException("400", "Contents JSON 파싱 오류 :" + e.toString());
        }
    }
}
