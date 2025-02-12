package com.priv.jdnights.api.batch.service;

import com.priv.jdnights.common.utils.WebClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContentsBatchService {

    private final WebClientUtil webClientUtil;

    public ContentsBatchService(WebClientUtil webClientUtil) {
        this.webClientUtil = webClientUtil;
    }

    public void getNextClassContentsInfo() {

        //https://nextclass.co.kr/_next/data/Ji3TML8Lk1FtZ6kasPUNf/creator/90.json?index=90&coursePage=3
        String result = webClientUtil.get("https://nextclass.co.kr/_next/data/Ji3TML8Lk1FtZ6kasPUNf/creator/90.json?index=90&coursePage=3", String.class);

        System.out.println("result = " + result);


    }
}
