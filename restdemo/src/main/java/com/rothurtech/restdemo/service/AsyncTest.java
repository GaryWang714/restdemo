package com.rothurtech.restdemo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTest {

    @Async
    public void asyncTest(Long id) {
        try {
            Thread.sleep(5000);
            System.out.println("async id: " + id);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
