package com.justcode.hxl.androidbasices.thread;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public ExecutorService newCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        /**
         * 下面没例子分别使用，测试结果
         */
//        check1(executorService);

        check2(executorService);

//        check3(executorService);

//        check4(executorService);

//        check5(executorService);
        return executorService;
    }

    /**
     * 创建一个固定大小的线程池，该方法可指定线程池的固定大小，对于超出的线程会在LinkedBlockingQueue队列中等待。
     */
    public ExecutorService newFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        check1(executorService);
        return executorService;
    }

    /**
     * 创建一个只有一条线程的线程池，该方法无参数，所有任务都保存队列LinkedBlockingQueue中，等待唯一的单线程来执行任务，并保证所有任务按照指定顺序(FIFO或优先级)执行。
     */
    public ExecutorService newSingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        check1(executorService);
        return executorService;
    }

    /**
     * 创建一个可定时执行或周期执行任务的线程池，该方法可指定线程池的核心线程个数。
     */
    public ExecutorService newScheduledThreadPool() {
        ExecutorService executorService = Executors.newScheduledThreadPool(3);
        //定时执行一次的任务，延迟1s后执行
        ((ScheduledExecutorService) executorService).schedule(new Runnable() {

            @Override
            public void run() {
                Log.d("logThread", Thread.currentThread().getName() + ", delay 1s");
            }
        }, 1, TimeUnit.SECONDS);

        //周期性地执行任务，延迟2s后，每3s一次地周期性执行任务
        ((ScheduledExecutorService) executorService).scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                Log.d("logThread", Thread.currentThread().getName() + ", every 3s");
            }
        }, 2, 3, TimeUnit.SECONDS);

        return executorService;

    }

    /**
     * 关闭线程池
     */
    public void shutDown(ExecutorService service) {
        service.shutdown();
    }
    public void shutDownNow(ExecutorService service){
        service.shutdownNow();
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
                    Log.d("logThread", Thread.currentThread().getName() + ", index=" + index);
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
                    Log.d("logThread", Thread.currentThread().getName() + ", index=" + index);
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
                    Log.d("logThread", Thread.currentThread().getName() + ", index=" + index);
                }
            });

        }
    }

    /**
     * executorService 打印结果是 顺序的 线程条数5条
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
     * executorService 打印结果是 顺序的 线程条数5条
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
