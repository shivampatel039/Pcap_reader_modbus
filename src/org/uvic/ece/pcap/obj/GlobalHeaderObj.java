package org.uvic.ece.pcap.obj;

public class GlobalHeaderObj {
    public static final int NETWORK_ETHERNET = 0x1;

    private boolean swapped;
    private int     majorVersion;
    private int     minorVersion;
    private long    snaplen;
    private long    network;

    public boolean isSwapped() {
        return swapped;
    }

    public void setSwapped(boolean swapped) {
        this.swapped = swapped;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public long getSnaplen() {
        return snaplen;
    }

    public void setSnaplen(long snaplen) {
        this.snaplen = snaplen;
    }

    public long getNetwork() {
        return network;
    }

    public void setNetwork(long network) {
        this.network = network;
    }
}
