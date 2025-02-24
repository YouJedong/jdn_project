package com.priv.jdnights.api.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.ContentLang;
import com.priv.jdnights.api.contents.repository.ContentRepository;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.exception.LogicException;
import com.priv.jdnights.common.utils.WebClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.priv.jdnights.common.Constants.*;


@Service
@Transactional
public class NextClassBatchService {

    private final WebClientUtil webClientUtil;

    public NextClassBatchService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    @Autowired
    private ContentRepository contentRepository;

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
            List<NextClassContentDto> contentDtoList = new ArrayList<>();
            for (int i = 0; i < totalPage; i++) {
                int reqPage = i + 1;

                url = UriComponentsBuilder.fromUriString(NC_BASE_URL)
                        .queryParam("coursePage", reqPage)
                        .toUriString();
                result = webClientUtil.get(url, String.class);

                contentDtoList.addAll(this.getContentsByJson(result));
            }

            if (!contentDtoList.isEmpty()) {
//                List<Content> contentList = contentDtoList.stream()
//                        .map(Content::generateByNextClass) // 변환 로직 필요
//                        .collect(Collectors.toList());
//                contentRepository.saveAll(contentList);
                for (NextClassContentDto dto : contentDtoList) {
                    Content findContent = contentRepository.findByExternalId(dto.getNcId());

                    if (findContent == null) { // insert
                        ArrayList<ContentLang> contentLangList = new ArrayList<>();
                        for (String langCode : LangCode.LANG_ARR) {
                            // 한국어일때만 제목 넣기
                            String contentName = null;
                            if (langCode.equals(LangCode.KO)) {
                                contentName = dto.getContentName();
                            }
                            contentLangList.add(ContentLang.createContentLang(langCode, contentName));
                        }
                        Content content = ; 애매.. 여기부터 다시

                    } else { // update

                    }

                }


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

    private List<NextClassContentDto> getContentsByJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode items = rootNode.path("pageProps").path("courses").path("items");

            if (items.isArray()) {
                ArrayList<NextClassContentDto> contentList = new ArrayList<>();
                for (JsonNode item : items) {
                    NextClassContentDto content = new NextClassContentDto();
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
