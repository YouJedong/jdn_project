package com.priv.jdnights.api.batch.service;

import com.priv.jdnights.common.utils.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional
public class YouTubeBatchService {

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

        String url = UriComponentsBuilder.fromUriString(YT_BASE_URL)
                .queryParam("playlistId", PLAYLIST_ID)
                .queryParam("key", KEY)
                .toUriString();
        String result = webClientUtil.get(url, String.class);
        System.out.println("result = " + result);




        // nextpagetoken으로 가져올지 정하기

        // 상세 api 호출
    }
}
