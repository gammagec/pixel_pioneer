package com.pixel_pioneer.clock;

import com.pixel_pioneer.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private boolean paused = false;
    private int time = 0;
    private List<TickHandler> tickHandlers = new ArrayList<>();

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void addTickHandler(TickHandler tickHandler) {
        this.tickHandlers.add(tickHandler);
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    time++;
                    if (time > Const.MAX_TIME) {
                        time = 0;
                    }
                    System.out.println((time % 2 == 0 ? "tick" : "tock") + " " + time);
                    for (TickHandler handler : tickHandlers) {
                        handler.onTick(time);
                    }
                }
            }
        }, 500, 500);
    }
}
