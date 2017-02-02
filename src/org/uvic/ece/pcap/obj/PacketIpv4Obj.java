package org.uvic.ece.pcap.obj;

public class PacketIpv4Obj {
    public static final int HEADER_LENGTH = 0x5;
    public static final int TYPE_IPV4     = 0x4;
    public static final int PROTOCOL_TCP  = 0x6;

    private int   type;
    private int   headerLength;
    private int   totalLength;
    private int   identification;
    private int   timeToLive;
    private int   protocol;
    private int   headerCheckSum;
    private int[] source;
    private int[] dest;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public int getIdentification() {
        return identification;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getHeaderCheckSum() {
        return headerCheckSum;
    }

    public void setHeaderCheckSum(int headerCheckSum) {
        this.headerCheckSum = headerCheckSum;
    }

    public int[] getSource() {
        return source;
    }

    public void setSource(int[] source) {
        this.source = source;
    }

    public int[] getDest() {
        return dest;
    }

    public void setDest(int[] dest) {
        this.dest = dest;
    }
}
