package club.zhengxiang.coding.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread1 {

    static AtomicInteger count = new AtomicInteger(1);
    static volatile boolean flag = true;

    static final Object lock = new Object();

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // method1();
        method2();
    }

    /**
     * 方法一：使用 volatile + flag 的方式
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
     */
    private static void method2() {
        new Thread(() -> {
            while (count.get() <= 200) {
                synchronized(lock) {
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + "0: " + count.getAndIncrement());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + ": 退出");
            synchronized (lock) {
                lock.notify();
            }
        }).start();

        new Thread(() -> {
            while (count.get() <= 200) {
                synchronized(lock) {
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + "1: " + count.getAndIncrement());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + ": 退出");
            synchronized (lock) {
                lock.notify();
            }
        }).start();
    }

}