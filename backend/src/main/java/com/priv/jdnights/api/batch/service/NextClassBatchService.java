package com.priv.jdnights.api.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priv.jdnights.api.batch.dto.NextClassContentDto;
import com.priv.jdnights.api.batch.entity.BatchHst;
import com.priv.jdnights.api.batch.repository.BatchHstRepository;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.ContentLang;
import com.priv.jdnights.api.contents.repository.ContentLangRepository;
import com.priv.jdnights.api.contents.repository.ContentRepository;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.exception.LogicException;
import com.priv.jdnights.common.utils.WebClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(NextClassBatchService.class);

    private final WebClientUtil webClientUtil;

    public NextClassBatchService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentLangRepository contentLangRepository;

    @Autowired
    private BatchHstRepository batchHstRepository;

    @Value("${external.apis.nextclass.base-url}")
    private String NC_BASE_URL;


    public void getNextClassContentsInfo() {

        // 페이지 수 조회
        String url = UriComponentsBuilder.fromUriString(NC_BASE_URL)
                .queryParam("coursePage", 1)
                .toUriString();
        String result = webClientUtil.get(url, String.class);
        int totalPage = this.getTotalPageByJson(result);

        log.info("넥스트 클래스 총 페이지 수 : {}", totalPage);

        // insert or update
        Integer insertCnt = null;
        Integer updateCnt = null;
        Integer totalCnt = null;
        if (totalPage > 0) {
            List<NextClassContentDto> contentDtoList = new ArrayList<>();
            for (int i = 0; i < totalPage; i++) {
                int reqPage = totalPage - i;

                url = UriComponentsBuilder.fromUriString(NC_BASE_URL)
                        .queryParam("coursePage", reqPage)
                        .toUriString();
                result = webClientUtil.get(url, String.class);

                contentDtoList.addAll(this.getContentsByJson(result));
            }
            log.info("넥스트 클래스 총 콘텐츠 수 : {}", contentDtoList.size());
            totalCnt = contentDtoList.size();

            insertCnt = 0;
            updateCnt = 0;
            if (!contentDtoList.isEmpty()) {
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
                        Content content = Content.createByNextClass(dto, contentLangList);
                        contentRepository.save(content);

                        // 등록 카운트
                        insertCnt++;

                    } else { // update
                        ContentLang contentLang = contentLangRepository.findByContentAndLangCode(findContent, LangCode.KO)
                                .orElse(null);

                        if (contentLang != null) {
                            contentLang.updateByNextClass(dto);
                            findContent.updateByNextClass(dto);

                            updateCnt++;
                        }

                    }

                }
            }
        }

        // 이력 insert
        BatchHst batchHst = BatchHst.createBatchHst(ContentType.NEXT_CLASS, totalCnt, insertCnt, updateCnt);
        batchHstRepository.save(batchHst);


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
