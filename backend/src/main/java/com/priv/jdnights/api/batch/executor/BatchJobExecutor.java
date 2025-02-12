package com.priv.jdnights.api.batch.executor;

import com.priv.jdnights.api.batch.service.ContentsBatchService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobExecutor {

    @Autowired
    private ContentsBatchService contentsBatchService;

    // 유튜브

    // 넥스트 클래스
    @Scheduled(cron = "* 27 23 * * ?")
    public void getNextClassContentsInfo() {
        contentsBatchService.getNextClassContentsInfo();

    }

    // 풀스코어


}
