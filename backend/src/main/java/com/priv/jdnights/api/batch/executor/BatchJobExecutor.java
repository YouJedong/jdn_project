package com.priv.jdnights.api.batch.executor;

import com.priv.jdnights.api.batch.service.FullScoreBatchService;
import com.priv.jdnights.api.batch.service.NextClassBatchService;
import com.priv.jdnights.api.batch.service.YouTubeBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobExecutor {

    @Autowired
    private NextClassBatchService nextClassBatchService;

    @Autowired
    private YouTubeBatchService youTubeBatchService;

    @Autowired
    private FullScoreBatchService fullScoreBatchService;

    @Scheduled(cron = "0 0 1 * * *")
    public void getYouTubeContentsInfo() {
        youTubeBatchService.getYouTubeContentsInfo();
    }

    // 넥스트 클래스
    @Scheduled(cron = "0 10 1 * * *")
    public void getNextClassContentsInfo() {
        nextClassBatchService.getNextClassContentsInfo();
    }

    @Scheduled(cron = "0 20 1 * * *")
    // 풀스코어
    public void getFullScoreContentsInfo() {
        fullScoreBatchService.getFullScoreContentsInfo();
    }


}
