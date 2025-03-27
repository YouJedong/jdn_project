package com.priv.jdnights.api.batch.service;

import com.priv.jdnights.common.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class FullScoreBatchService {

    private static final Logger log = LogManager.getLogger(FullScoreBatchService.class);

    @Value("${external.apis.fullscore.login-url}")
    private String FS_LOGIN_URL;

    @Value("${external.apis.fullscore.admin-id}")
    private String FS_ADMIN_ID;

    @Value("${external.apis.fullscore.admin-pw}")
    private String FS_ADMIN_PW;


    public void getFullScoreContentsInfo() {

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
            System.out.println("응답 코드: " + loginRes.statusCode());
            System.out.println("응답 URL: " + loginRes.url());
            System.out.println("응답 쿠키: " + loginRes.cookies());
            System.out.println("응답 헤더: " + loginRes.headers());
            System.out.println("본문 내용:\n" + loginRes.body());
            // 세션 쿠키 저장
            Map<String, String> loginCookies = loginRes.cookies();

            // 로그인된 상태로 관리자 페이지 접근
            Document doc = Jsoup.connect("https://www.fullscore.co.kr/mall/marketing/analy_prd.php?page=1")
                    .cookies(loginCookies)
                    .userAgent("Mozilla/5.0")
                    .get();

            System.out.println(doc.title());
            System.out.println(doc.html());

            // 목록(통계) 조회

            // 상세 조회

        } catch (Exception e) {
            log.error("풀스코어 배치 실패: " + e.toString());
        }

    }
}
