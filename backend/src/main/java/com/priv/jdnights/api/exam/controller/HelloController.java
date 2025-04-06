package com.priv.jdnights.api.exam.controller;

import com.priv.jdnights.api.batch.executor.BatchJobExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private BatchJobExecutor batchJobExecutor;

    @GetMapping(value = "/api/hello")
    public String hello() {
        return "hello!";
    }

    @GetMapping(value = "/nc")
    public void ncTest() {
        batchJobExecutor.getNextClassContentsInfo();
    }

    @GetMapping(value = "/yt")
    public void ytTest() {
        batchJobExecutor.getYouTubeContentsInfo();
    }

    @GetMapping(value = "/fs")
    public void fsTest() {
        batchJobExecutor.getFullScoreContentsInfo();
    }

}
