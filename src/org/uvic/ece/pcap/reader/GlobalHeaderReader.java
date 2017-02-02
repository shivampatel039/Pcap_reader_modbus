package org.uvic.ece.pcap.reader;

import java.io.FileInputStream;

import org.uvic.ece.pcap.obj.GlobalHeaderObj;
import org.uvic.ece.pcap.reader.file.FileReader;

public class GlobalHeaderReader {
    private static final int MAGIC0 = 0xd4;
    private static final int MAGIC1 = 0xc3;
    private static final int MAGIC2 = 0xb2;
    private static final int MAGIC3 = 0xa1;

    private final FileInputStream fileInputStream;

    public GlobalHeaderReader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public GlobalHeaderObj read() throws Exception {
        GlobalHeaderObj globalHeaderObj = new GlobalHeaderObj();

        int magic0 = FileReader.readUint8(fileInputStream);
        int magic1 = FileReader.readUint8(fileInputStream);
        int magic2 = FileReader.readUint8(fileInputStream);
        int magic3 = FileReader.readUint8(fileInputStream);

        if (MAGIC0 == magic0 && MAGIC1 == magic1 && MAGIC2 == magic2 && MAGIC3 == magic3)
            globalHeaderObj.setSwapped(false);
        else if (MAGIC0 == magic3 && MAGIC1 == magic2 && MAGIC2 == magic1 && MAGIC3 == magic0)
            globalHeaderObj.setSwapped(true);
        else
            throw new Exception("the file format is not correct");

        FileReader fileReader = new FileReader(fileInputStream, globalHeaderObj.isSwapped());

        globalHeaderObj.setMajorVersion(fileReader.readUint16());
        globalHeaderObj.setMinorVersion(fileReader.readUint16());
        fileReader.readInt32();
        fileReader.readUint32();
        globalHeaderObj.setSnaplen(fileReader.readUint32());
        globalHeaderObj.setNetwork(fileReader.readUint32());

        return globalHeaderObj;
    }
}
