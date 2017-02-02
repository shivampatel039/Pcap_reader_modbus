package org.uvic.ece.pcap.reader;

import org.uvic.ece.pcap.obj.PacketHeaderObj;
import org.uvic.ece.pcap.reader.file.FileReader;

public class PacketHeaderReader {
    private final FileReader fileReader;

    public PacketHeaderReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public PacketHeaderObj read() throws Exception {
        PacketHeaderObj packetHeaderObj = new PacketHeaderObj();

        packetHeaderObj.setSec(fileReader.readUint32());
        packetHeaderObj.setUsec(fileReader.readUint32());
        packetHeaderObj.setInclLen(fileReader.readUint32());
        packetHeaderObj.setOrigLen(fileReader.readUint32());

        return packetHeaderObj;
    }
}
