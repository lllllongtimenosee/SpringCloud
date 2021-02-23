package com.taryn._08executionFramework;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ItemVo<T> implements Delayed {

    private long activeTime;
    private T date;

    public ItemVo(long activeTime, T date) {
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime,
                TimeUnit.MILLISECONDS)+System.nanoTime();
        this.date = date;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getDate() {
        return date;
    }

    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
        return (d==0)?0:((d>0)?1:-1);

    }

    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.activeTime-System.nanoTime(),
                TimeUnit.NANOSECONDS);
        return d;
    }
}
