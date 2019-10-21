package com.antsdouble;

/**
 * @author lyy
 * @description
 * @date 2019/4/30
 */
public class LiftOff implements Runnable {
    //default
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id+"("+(countDown>0?countDown:"LiftOff!")+").";
    }

    @Override
    public void run() {

        while (countDown-->0){
            System.out.println(status());
            Thread.yield();
        }
    }
}
