package org.uvic.ece.pcap.obj.request;

public class WriteMultipleCoilsRequest {
    private int    startAddress;
    private int    quantityOfOutputs;
    private int    byteCount;
    private byte[] data;

    public int getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    public int getQuantityOfOutputs() {
        return quantityOfOutputs;
    }

    public void setQuantityOfOutputs(int quantityOfOutputs) {
        this.quantityOfOutputs = quantityOfOutputs;
    }

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
