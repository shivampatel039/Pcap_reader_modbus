package org.uvic.ece.pcap.reader;

public abstract class AbstractReader {
    private final boolean swapped;

    public AbstractReader(boolean swapped) {
        this.swapped = swapped;
    }

    public abstract int get() throws Exception;

    public abstract void get(byte[] data) throws Exception;

    public int readInt8() throws Exception {
        int result = read();
        return result > 0x7f ? result - 0x100 : result;
    }

    public int readUint8() throws Exception {
        return read();
    }

    public int readInt16() throws Exception {
        int int1 = read();
        int int2 = read();
        int result = swapped ? int1 << 8 | int2 : int2 << 8 | int1;
        return result > 0x7fff ? result - 0x10000 : result;
    }

    public int readUint16() throws Exception {
        int int1 = read();
        int int2 = read();
        return swapped ? int1 << 8 | int2 : int2 << 8 | int1;
    }

    public int readInt32() throws Exception {
        int int1 = read();
        int int2 = read();
        int int3 = read();
        int int4 = read();
        return swapped ?
                int1 << 24 | int2 << 16 | int3 << 8 | int4 :
                int4 << 24 | int3 << 16 | int2 << 8 | int1;
    }

    public long readUint32() throws Exception {
        int int1 = read();
        int int2 = read();
        int int3 = read();
        int int4 = read();
        return swapped ?
                (long) int1 << 24 | int2 << 16 | int3 << 8 | int4 :
                (long) int4 << 24 | int3 << 16 | int2 << 8 | int1;
    }

    public byte[] read(int length) throws Exception {
        byte[] data = new byte[length];
        get(data);
        return data;
    }

    private int read() throws Exception {
        int result = get();
        if (result < 0)
            result += 0x100;
        return result;
    }
}
