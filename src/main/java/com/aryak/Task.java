package com.aryak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {

    private Task() {
        // nothing here
    }

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void ioIntensive(int i) {

        try {

            log.info("Started task : {}", i);
            Thread.sleep(Duration.ofSeconds(10));
            log.info("Finished task : {}", i);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
