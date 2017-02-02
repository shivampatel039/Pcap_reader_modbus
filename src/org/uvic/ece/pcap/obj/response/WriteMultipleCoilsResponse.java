package org.uvic.ece.pcap.obj.response;

public class WriteMultipleCoilsResponse {
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
