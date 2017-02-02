package org.uvic.ece.pcap.obj.request;

public class ReadDiscreteInputsRequest {
    private int startAddress;
    private int quantityOfInputs;

    public int getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    public int getQuantityOfInputs() {
        return quantityOfInputs;
    }

    public void setQuantityOfInputs(int quantityOfInputs) {
        this.quantityOfInputs = quantityOfInputs;
    }
}
