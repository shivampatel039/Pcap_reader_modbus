package org.uvic.ece.pcap.obj.request;

public class ReadInputRegistersRequest {
    private int startAddress;
    private int quantityOfInputRegisters;

    public int getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    public int getQuantityOfInputRegisters() {
        return quantityOfInputRegisters;
    }

    public void setQuantityOfInputRegisters(int quantityOfInputRegisters) {
        this.quantityOfInputRegisters = quantityOfInputRegisters;
    }
}
