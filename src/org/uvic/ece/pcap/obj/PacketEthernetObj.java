package org.uvic.ece.pcap.obj;

public class PacketEthernetObj {
    public static final int PACKET_LENGTH = 14;
    public static final int TYPE_IPV4     = 0x0800;

    private byte[] source;
    private byte[] dest;
    private int    type;

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }

    public byte[] getDest() {
        return dest;
    }

    public void setDest(byte[] dest) {
        this.dest = dest;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
