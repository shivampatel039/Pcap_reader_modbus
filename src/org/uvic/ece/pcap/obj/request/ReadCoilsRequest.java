package org.uvic.ece.pcap.obj.request;

public class ReadCoilsRequest {
    private int startAddress;
    private int quantityOfOutputs;

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
}
