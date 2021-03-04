package com.mgame.biz;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    private Object lock = new Object();
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {

        new Timer("room update").schedule(new TimerTask() {
            @Override
            public void run() {
                BattleManager.getInstance().update();
            }
        }, 1000, 50);

        Test test = new Test();
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        Thread currThread = Thread.currentThread();

        unsafe.unpark(currThread);
        unsafe.unpark(currThread);
        unsafe.unpark(currThread);

        unsafe.park(false, 3000000000l);

        System.out.println("SUCCESS!!!");


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1执行");
                synchronized (test.lock) {
                    System.out.println("t1获取锁");
                    System.out.println("t1 sleep1s");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.println("t1wait1s");
                        test.lock.wait(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t1再次锁");
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2执行");
                synchronized (test.lock) {
                    System.out.println("t2获取锁");
                    try {
                        System.out.println("t2sleep10s");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    test.lock.notify();
                    System.out.println("t2释放锁");

                }
            }
        }).start();


    }
}
