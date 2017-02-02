package org.uvic.ece.pcap.obj;

public class PacketHeaderObj {
    private long sec;
    private long usec;
    private long inclLen;
    private long origLen;

    public long getSec() {
        return sec;
    }

    public void setSec(long sec) {
        this.sec = sec;
    }

    public long getUsec() {
        return usec;
    }

    public void setUsec(long usec) {
        this.usec = usec;
    }

    public long getInclLen() {
        return inclLen;
    }

    public void setInclLen(long inclLen) {
        this.inclLen = inclLen;
    }

    public long getOrigLen() {
        return origLen;
    }

    public void setOrigLen(long origLen) {
        this.origLen = origLen;
    }
}
