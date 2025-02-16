package com.priv.jdnights.api.batch.executor;

import com.priv.jdnights.api.batch.service.NextClassBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BatchJobExecutorTest {

    @Autowired
    private NextClassBatchService nextClassBatchService;


}