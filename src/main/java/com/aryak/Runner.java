package com.aryak;

import java.util.concurrent.CountDownLatch;

public class Runner {

    static final int MAX_THREADS = 10;

    public static void main(String[] args) throws InterruptedException {
        //platformThreadDemo1();
        //platformThreadDemo2();
        platformThreadDemo4();
    }

    /**
     * Create platform threads using low level Thread API
     * The low level API is a thin wrapper on OS thread
     * 1 Java Thread = 1 OS thread
     * Uses the pthread_create unix command to create OS thread
     */
    private static void platformThreadDemo1() {
        for ( int i = 0; i < MAX_THREADS; i++ ) {
            int finalI = i;
            // -Xss to set stack size. Linux : Default 1 mb, MacOS : 2mb
            Thread t = new Thread(() -> Task.ioIntensive(finalI));
            t.start();
        }
    }

    /**
     * create platform threads using factory builder
     */
    private static void platformThreadDemo2() {

        var builder = Thread.ofPlatform().name("aryak-thread-", 1);
        for ( int i = 0; i < MAX_THREADS; i++ ) {
            int finalI = i;
            Thread t = builder.unstarted(() -> Task.ioIntensive(finalI));
            t.start();
        }
    }

    /**
     * create platform daemon threads. Main starts the daemon threads & terminates
     * Does not wait for background threads to join main thread
     */
    private static void platformThreadDemo3() {

        var builder = Thread.ofPlatform().daemon().name("daemon-thread-", 1);
        for ( int i = 0; i < MAX_THREADS; i++ ) {
            int finalI = i;
            Thread t = builder.unstarted(() -> Task.ioIntensive(finalI));
            t.start();
        }
    }

    /**
     * create platform daemon threads and make main to wait
     * for them to finish by using count down latch.
     * Another method is to use thread.join()
     */
    private static void platformThreadDemo4() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);
        var builder = Thread.ofPlatform().daemon().name("daemon-thread-", 1);
        for ( int i = 0; i < MAX_THREADS; i++ ) {

            int finalI = i;
            Thread t = builder.unstarted(() -> {
                Task.ioIntensive(finalI);

                // work done, so decrease counter
                countDownLatch.countDown();
            });
            t.start();
        }
        countDownLatch.await();
    }
}
