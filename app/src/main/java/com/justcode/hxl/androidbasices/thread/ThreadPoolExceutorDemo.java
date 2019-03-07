package com.justcode.hxl.androidbasices.thread;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExceutorDemo {
    /**
     * 线程池的优点：
     * 降低系统资源消耗，通过重用已存在的线程，降低线程创建和销毁造成的消耗；
     * 提高系统响应速度，当有任务到达时，无需等待新线程的创建便能立即执行；
     * 方便线程并发数的管控，线程若是无限制的创建，不仅会额外消耗大量系统资源，更是占用过多资源而阻塞系统或oom等状况，从而降低系统的稳定性。线程池能有效管控线程，统一分配、调优，提供资源使用率；
     * 更强大的功能，线程池提供了定时、定期以及可控线程数等功能的线程池，使用方便简单。
     */


    /**
     * CachedThreadPool
     * 创建一个可缓存的无限程池，该方法无参数。
     * 当线程池中的线程空闲时间超过60s则会自动回收该线程，
     * 当任务超过线程池的线程数则创建新线程。
     * 线程池的大小上限为Integer.MAX_VALUE，可看做是无限大。
     */
    public void newCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        /**
         * 下面没例子分别使用，测试结果
         */
//        check1(executorService);

        check2(executorService);

//        check3(executorService);

//        check4(executorService);

//        check5(executorService);
    }

    private void check5(ExecutorService executorService) {
        /**
         *  executorService 打印结果是 乱序的 线程条数是大于1小于5的
         */
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("logThread",Thread.currentThread().getName() + ", index=" + index);
                }
            });

        }
    }

    private void check4(ExecutorService executorService) {
        /**
         *  executorService 打印结果是 乱序的 线程条数5条
         */
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("logThread",Thread.currentThread().getName() + ", index=" + index);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void check3(ExecutorService executorService) {
        /**
         *  executorService 打印结果是 乱序的 线程条数5条
         */
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("logThread",Thread.currentThread().getName() + ", index=" + index);
                }
            });

        }
    }

    /**
     *  executorService 打印结果是 顺序的 线程条数5条
     */
    private void check2(ExecutorService executorService) {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("logThread", Thread.currentThread().getName() + ", index=" + index);
                }
            });

        }
    }
    /**
     *  executorService 打印结果是 顺序的 线程条数5条
     */
    private void check1(ExecutorService executorService) {
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("logThread", Thread.currentThread().getName() + ", index=" + index);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
