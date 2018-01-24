package com.concurrency;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static void sleep(Long second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
