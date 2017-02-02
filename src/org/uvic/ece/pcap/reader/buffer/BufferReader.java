package org.uvic.ece.pcap.reader.buffer;

import org.uvic.ece.pcap.reader.AbstractReader;

public class BufferReader extends AbstractReader {
    private final byte[] data;
    private int cap;
    private int pos;

    public BufferReader(byte[] data, boolean swapped) {
        super(swapped);

        this.data = new byte[data.length];
        this.cap = data.length;
        this.pos = 0;

        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    @Override
    public int get() throws Exception {
        if (pos >= cap)
            throw new Exception("the end of the buffer is reached");

        int result = data[pos++];
        if (result < 0)
            result += 0x100;
        return result;
    }

    @Override
    public void get(byte[] data) throws Exception {
        int length = data.length;

        if (pos >= cap || pos + length > cap)
            throw new Exception("the end of the buffer is reached");

        System.arraycopy(this.data, pos, data, 0, length);
        pos += length;
    }

    public boolean hasRemaining() {
        return pos < cap;
    }

    public int remaining() {
        return cap - pos;
    }
}
