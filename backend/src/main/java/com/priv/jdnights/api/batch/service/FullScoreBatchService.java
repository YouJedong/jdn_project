package com.priv.jdnights.api.batch.service;

import com.priv.jdnights.api.batch.dto.FullScoreContentDto;
import com.priv.jdnights.api.batch.entity.BatchHst;
import com.priv.jdnights.api.batch.repository.BatchHstRepository;
import com.priv.jdnights.api.contents.entity.Content;
import com.priv.jdnights.api.contents.entity.ContentLang;
import com.priv.jdnights.api.contents.repository.ContentRepository;
import com.priv.jdnights.common.Constants;
import com.priv.jdnights.common.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.priv.jdnights.common.Constants.*;

@Service
@Transactional
public class FullScoreBatchService {

    private static final Logger log = LogManager.getLogger(FullScoreBatchService.class);

    @Value("${external.apis.fullscore.login-url}")
    private String FS_LOGIN_URL;

    @Value("${external.apis.fullscore.stats-url}")
    private String FS_STATS_URL;

    @Value("${external.apis.fullscore.detail-url}")
    private String FS_DETAIL_URL;

    @Value("${external.apis.fullscore.admin-id}")
    private String FS_ADMIN_ID;

    @Value("${external.apis.fullscore.admin-pw}")
    private String FS_ADMIN_PW;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private BatchHstRepository batchHstRepository;


    public void getFullScoreContentsInfo() {

        Integer insertCnt = null;
        Integer updateCnt = null;
        Integer totalCnt = null;

        // 로그인 후 쿠키 값 가져오기
        try {
            Connection.Response loginRes = Jsoup.connect(FS_LOGIN_URL)
                    .data("admin_id", FS_ADMIN_ID)          // 아이디 입력
                    .data("admin_pw", FS_ADMIN_PW)    // 비밀번호 입력
                    .data("secure_login", "Y")            // 체크박스 포함
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0")              // 크롤링 우회용
                    .timeout(5000)
                    .execute();
            
            // 세션 쿠키 저장
            Map<String, String> loginCookies = loginRes.cookies();

            // 로그인된 상태로 관리자 페이지 접근
            Document doc = Jsoup.connect(FS_STATS_URL + "1")
                    .cookies(loginCookies)
                    .userAgent("Mozilla/5.0")
                    .get();

            Elements pageLinks = doc.select("ul.pagination li");
            Element lastLi = pageLinks.last();
            String href = lastLi.selectFirst("a").attr("href");
            String pageStr = href.replaceAll(".*page=(\\d+).*", "$1");
            int totalPage = Integer.parseInt(pageStr);

            System.out.println("마지막 페이지 번호: " + totalPage);

            // 목록(통계) 조회
            insertCnt = 0;
            updateCnt = 0;
            if (totalPage > 0) {
                List<FullScoreContentDto> contentDtoList = new ArrayList<>();

                // 1페이지 데이터 추출
                contentDtoList.addAll(this.getContentsStatsInfo(doc));

                // 나머지 페이지 데이터 추출
                if (totalPage > 1) {
                    for(int i = 1; i < totalPage; i++) {
                        int pageNo = i + 1;
                        doc = Jsoup.connect(FS_STATS_URL + pageNo)
                                .cookies(loginCookies)
                                .userAgent("Mozilla/5.0")
                                .get();

                        contentDtoList.addAll(this.getContentsStatsInfo(doc));
                    }
                }

                totalCnt = contentDtoList.size();

                if (contentDtoList.size() > 0) {

                    for (FullScoreContentDto dto : contentDtoList) {
                        Content findContent = contentRepository.findByExternalIdAndContentType(dto.getPrdcode(), ContentType.FULL_SCORE);

                        if (findContent == null) {
                            // 새로운 악보일 때 상세 정보 가져오기
                            doc = Jsoup.connect(FS_DETAIL_URL + dto.getPrdcode())
                                    .cookies(loginCookies)
                                    .userAgent("Mozilla/5.0")
                                    .get();

                            String viewYn = doc.select("input[name=showset][value=Y]").hasAttr("checked") ? "Y" : "N";

                            // 풀스코어 미노출 콘텐츠 저장 x
                            if ("N".equals(viewYn)) {
                                continue;
                            }

                            String artistName = doc.selectFirst("input[name=info_value1]").attr("value");
                            String price = doc.selectFirst("input[name=sellprice]").attr("value");

                            // 썸네일
                            String thumbnailSrc = doc.selectFirst("img[name=prdimg1]").attr("src");
                            String thumbnailUrl = "https://www.fullscore.co.kr/" + thumbnailSrc.replaceFirst("^\\.\\./\\.\\./", ""); // 상대경로 제거

                            dto.setTitle(artistName + " - " + dto.getTitle());
                            dto.setPrice(Integer.parseInt(price));
                            dto.setThumbnailUrl(thumbnailUrl);

                            ArrayList<ContentLang> contentLangList = new ArrayList<>();
                            for (String langCode : LangCode.LANG_ARR) {
                                // 한국어일때만 제목 넣기
                                String contentName = null;
                                if(langCode.equals(LangCode.KO)) {
                                    contentName = dto.getTitle();
                                }
                                contentLangList.add(ContentLang.createContentLang(langCode, contentName, null));
                            }
                            Content content = Content.createByFullScore(dto, contentLangList);
                            contentRepository.save(content);

                            insertCnt++;
                        } else {

                            findContent.updateByFullScore(dto);

                            updateCnt++;
                        }
                    }
                }

            }

            BatchHst batchHst = BatchHst.createBatchHst(ContentType.FULL_SCORE, totalCnt, insertCnt, updateCnt);
            batchHstRepository.save(batchHst);

        } catch (Exception e) {
            log.error("풀스코어 배치 실패: " + e.toString());
            e.printStackTrace();
        }

    }

    private List<FullScoreContentDto> getContentsStatsInfo(Document doc) {
        Element contentsDiv = doc.selectFirst("#contents");
        Element contentsTbody = contentsDiv.select("table").get(3).selectFirst("tbody");

        List<FullScoreContentDto> contentDtolist = new ArrayList<>();
        Elements allTrs = contentsTbody.select("tr");
        for (int i = 3; i < allTrs.size(); i += 2) {
            Element tr = allTrs.get(i);
            Elements tds = tr.select("td");

            String href = tds.get(2).selectFirst("a").attr("href");
            String prdcode = href.replaceAll(".*prdcode=([0-9]+).*", "$1");
            String title = tds.get(2).text();
            String viewCount = tds.get(3).text();
            String orderCount = tds.get(5).text();

            FullScoreContentDto contentDto = new FullScoreContentDto();
            contentDto.setPrdcode(prdcode);
            contentDto.setTitle(title);
            contentDto.setViewCount(Long.parseLong(viewCount));
            contentDto.setOrderCount(Integer.parseInt(orderCount));
            contentDtolist.add(contentDto);
        }

        return contentDtolist;
    }
}
