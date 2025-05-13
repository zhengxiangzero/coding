package club.zhengxiang.coding.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Thread1 {

    static AtomicInteger count = new AtomicInteger(1);
    static volatile boolean flag = true;

    static final Object lock = new Object();

    static final Integer MAX_CONCURRENT_THREAD = 1;
    static int num = 1;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // method1();
        // method2();
        // method3();
        method32();
    }

    /**
     * 方法一：使用 volatile + flag 的方式
     * 使用忙等待，浪费CPU资源
     */
    private static void method1() {
        new Thread(() -> {
            while (count.get() <= 200) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    flag = false;
                }
            }
        }).start();
        new Thread(() -> {
            while (count.get() <= 200) {
                if (!flag) {
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    flag = true;
                }
            }
        }).start();
    }


    /**
     * 方法二：使用 synchronized 对象锁的方式
     * 比方法一忙等待更高效，比 Semaphore 实现更简单直接
     */
    private static void method2() {
        new Thread(() -> {
            while (count.get() <= 200) {
                // System.out.println("----等待获取锁 " + Thread.currentThread().getName() + ": " + count.get());
                synchronized(lock) {
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + ": 退出");
            synchronized (lock) {
                lock.notifyAll();
            }
        }).start();

        new Thread(() -> {
            while (count.get() <= 200) {
                // System.out.println("----等待获取锁 " + Thread.currentThread().getName() + ": " + count.get());
                synchronized(lock) {
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + ": 退出");
            synchronized (lock) {
                lock.notifyAll();
            }
        }).start();
    }


    /**
     * 方法三：使用信号量 Semaphore，有点问题，有时不会完全交替
     * 一个 semaphore 不能严格交替打印，因为 semaphore 只能控制同时执行的线程数量，但不能控制具体的执行顺序
     * 即使使用公平模式的 semaphore，如果线程A释放许可时，线程B还没来得及调用 acquire 进入等待队列，那么线程A会再次获得许可
     */
    private static void method3() {
        // 公平模式
        Semaphore semaphore = new Semaphore(MAX_CONCURRENT_THREAD, true);
        new Thread(new Worker(semaphore)).start();
        new Thread(new Worker(semaphore)).start();

    }

    static class Worker implements Runnable {

        private final Semaphore semaphore;

        Worker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            // 注意⚠️：不能使用num，会产生线程竟态问题，一个线程连续打印
            while (true) {
                try {
                    semaphore.acquire();
                    // 获取许可后，检查退出条件
                    if (count.get() > 200) {
                        // 最后释放许可，防止程序无法退出
                        semaphore.release();
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    // 注意⚠️：这里 sleep 必须在 release 之前，否则另一个线程难以进入到等待队列，而是直接获得许可，很难交替打印
                    Thread.sleep(1);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 方法三-2：使用两个 Semaphore 严格交替打印
     */
    private static void method32() {
        // 两个信号量，总共有1个许可，保证只有一个线程执行
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);

        new Thread(() -> {
            while (true) {
                try {
                    semaphore1.acquire();
                    if (count.get() > 200) {
                        // 处理边界竞态条件，打印 201
                        semaphore2.release();
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    semaphore2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    semaphore2.acquire();
                    if (count.get() > 200) {
                        semaphore1.release();
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + count.getAndIncrement());
                    semaphore1.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}