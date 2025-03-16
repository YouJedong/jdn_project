package com.priv.jdnights.api.batch.executor;

import com.priv.jdnights.api.batch.service.NextClassBatchService;
import com.priv.jdnights.api.batch.service.YouTubeBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobExecutor {

    @Autowired
    private NextClassBatchService nextClassBatchService;

    @Autowired
    private YouTubeBatchService youTubeBatchService;

    // 유튜브
    public void getYouTubeContentsInfo() {
        youTubeBatchService.getYouTubeContentsInfo();
    }

    // 넥스트 클래스
//    @Scheduled(cron = "* 27 23 * * ?")
    public void getNextClassContentsInfo() {
        nextClassBatchService.getNextClassContentsInfo();
    }

    // 풀스코어


}
