package com.priv.jdnights.api.batch.executor;

import com.priv.jdnights.api.batch.service.ContentsBatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BatchJobExecutorTest {

    @Autowired
    private ContentsBatchService contentsBatchService;


}