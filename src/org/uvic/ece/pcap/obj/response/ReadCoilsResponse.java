package org.uvic.ece.pcap.obj.response;

public class ReadCoilsResponse {
    private int    byteCount;
    private byte[] data;

    public int getByteCount() {
        return byteCount;
    }

    public void setByteCount(int byteCount) {
        this.byteCount = byteCount;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
