package com.mgame.biz;

import java.util.Timer;
import java.util.TimerTask;

public class Test {
    public static void main(String[] args) {
        new Timer("room update").schedule(new TimerTask() {
            @Override
            public void run() {
                BattleManager.getInstance().update();
            }
        }, 1000, 50);
    }
}
