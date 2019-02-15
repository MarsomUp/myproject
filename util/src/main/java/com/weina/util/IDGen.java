package com.weina.util;

/**
 * @Description:
 * @Author: this code is copy from net
 * @Date: 2019/02/12 09:46
 */
public class IDGen {

    private long workerId;
    private long datacenterId;
    private long sequence;
    private long twepoch;
    private long workerIdBits;
    private long datacenterIdBits;
    private long maxWorkerId;
    private long maxDatacenterId;
    private long sequenceBits;
    private long workerIdShift;
    private long datacenterIdShift;
    private long timestampLeftShift;
    private long sequenceMask;
    private long lastTimestamp;

    public static IDGen getInstance() {
        return IdGenHolder.instance;
    }

    public IDGen() {
        this(0L, 0L);
    }

    public IDGen(final long workerId, final long datacenterId) {
        this.sequence = 0L;
        this.twepoch = 1288834974657L;
        this.workerIdBits = 5L;
        this.datacenterIdBits = 5L;
        this.maxWorkerId = (-1L ^ -1L << (int)this.workerIdBits);
        this.maxDatacenterId = (-1L ^ -1L << (int)this.datacenterIdBits);
        this.sequenceBits = 12L;
        this.workerIdShift = this.sequenceBits;
        this.datacenterIdShift = this.sequenceBits + this.workerIdBits;
        this.timestampLeftShift = this.sequenceBits + this.workerIdBits + this.datacenterIdBits;
        this.sequenceMask = (-1L ^ -1L << (int)this.sequenceBits);
        this.lastTimestamp = -1L;
        if (workerId > this.maxWorkerId || workerId < 0L) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        if (datacenterId > this.maxDatacenterId || datacenterId < 0L) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", this.maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long Id() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1L & this.sequenceMask);
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        }
        else {
            this.sequence = 0L;
        }
        this.lastTimestamp = timestamp;
        return timestamp - this.twepoch << (int)this.timestampLeftShift | this.datacenterId << (int)this.datacenterIdShift | this.workerId << (int)this.workerIdShift | this.sequence;
    }

    protected long tilNextMillis(final long lastTimestamp) {
        long timestamp;
        for (timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {}
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static long getId() {
        return getInstance().Id();
    }

    private static class IdGenHolder {
        private static final IDGen instance;

        static {
            instance = new IDGen();
        }
    }
}
