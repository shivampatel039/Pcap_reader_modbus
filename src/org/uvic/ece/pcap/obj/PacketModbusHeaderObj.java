package org.uvic.ece.pcap.obj;

public class PacketModbusHeaderObj {
    public static final int PROTOCOL_IDENTIFIER = 0x0;
    public static final int UNIT_IDENTIFIER = 0xff;

    private int transactionIdentifier;
    private int protocolIdentifier;
    private int length;
    private int unitIdentifier;

    public int getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(int transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    public int getProtocolIdentifier() {
        return protocolIdentifier;
    }

    public void setProtocolIdentifier(int protocolIdentifier) {
        this.protocolIdentifier = protocolIdentifier;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getUnitIdentifier() {
        return unitIdentifier;
    }

    public void setUnitIdentifier(int unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }
}
