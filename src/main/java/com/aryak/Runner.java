package com.aryak;

public class Runner {

    static final int MAX_THREADS = 50_000;

    public static void main(String[] args) {

        for ( int i = 0; i < MAX_THREADS; i++ ) {
            int finalI = i;
            // pthread_create is a unix command to create OS thread
            // -Xss to set stack size. Linux : Default 1 mb, MacOS : 2mb
            Thread t = new Thread(() -> Task.ioIntensive(finalI));
            t.start();
        }
    }
}
