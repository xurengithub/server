package com.mgame.lockstep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LockStep {

    // 帧长度
    private int tickLength = 50;
    // 当前帧
    private int currStep = 0;

    // 保存当前待发送帧
    private ConcurrentLinkedQueue<Command> queue = new ConcurrentLinkedQueue<>();

    // 保存帧全量


}
