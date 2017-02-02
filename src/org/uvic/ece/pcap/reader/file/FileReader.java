package org.uvic.ece.pcap.reader.file;

import java.io.FileInputStream;

import org.uvic.ece.pcap.reader.AbstractReader;

public class FileReader extends AbstractReader {
    private final FileInputStream fileInputStream;

    public static int readUint8(FileInputStream fileInputStream) throws Exception {
        int result = fileInputStream.read();
        if (result < 0)
            throw new Exception("the end of the file is reached");
        return result;
    }

    public FileReader(FileInputStream fileInputStream, boolean swapped) {
        super(swapped);

        this.fileInputStream = fileInputStream;
    }

    @Override
    public int get() throws Exception {
        int result = fileInputStream.read();
        if (result < 0)
            throw new Exception("the end of the file is reached");
        return result;
    }

    @Override
    public void get(byte[] data) throws Exception {
        if (data.length != fileInputStream.read(data))
            throw new Exception("can not read such length data");
    }
}
