package org.uvic.ece.pcap.obj;

import org.uvic.ece.pcap.obj.request.ReadCoilsRequest;
import org.uvic.ece.pcap.obj.request.ReadDiscreteInputsRequest;
import org.uvic.ece.pcap.obj.request.ReadHoldRegsRequest;
import org.uvic.ece.pcap.obj.request.ReadInputRegistersRequest;
import org.uvic.ece.pcap.obj.request.WriteMultipleCoilsRequest;
import org.uvic.ece.pcap.obj.request.WriteMultipleRegistersRequest;
import org.uvic.ece.pcap.obj.response.ReadCoilsResponse;
import org.uvic.ece.pcap.obj.response.ReadDiscreteInputsResponse;
import org.uvic.ece.pcap.obj.response.ReadHoldRegsResponse;
import org.uvic.ece.pcap.obj.response.ReadInputRegistersResponse;
import org.uvic.ece.pcap.obj.response.WriteMultipleCoilsResponse;
import org.uvic.ece.pcap.obj.response.WriteMultipleRegistersResponse;

public class PacketModbusBodyObj {
    public static final int FUNC_READ_COILS           = 0x01;
    public static final int FUNC_READ_DISCRETE_INPUTS = 0x02;
    public static final int FUNC_READ_INPUT_REGISTERS = 0x04;
    public static final int FUNC_WRITE_MULTI_COILS    = 0x0f;
    public static final int FUNC_WRITE_MULTI_REGS	  = 0X10;
    public static final int FUNC_READ_HOLD_REGS	  	  = 0X03;

    private int    functionCode;
    private byte[] data;

    private ReadCoilsRequest  readCoilsRequest;
    private ReadCoilsResponse readCoilsResponse;

    private ReadDiscreteInputsRequest  readDiscreteInputsRequest;
    private ReadDiscreteInputsResponse readDiscreteInputsResponse;

    private ReadInputRegistersRequest  readInputRegistersRequest;
    private ReadInputRegistersResponse readInputRegistersResponse;

    private WriteMultipleCoilsRequest  writeMultipleCoilsRequest;
    private WriteMultipleCoilsResponse writeMultipleCoilsResponse;
    
    private WriteMultipleRegistersRequest writeMultipleRegistersRequest;
    private WriteMultipleRegistersResponse writeMultipleRegistersResponse;
    
    private ReadHoldRegsRequest readHoldRegsRequest;
    private ReadHoldRegsResponse readHoldRegsResponse;
    
	public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ReadCoilsRequest getReadCoilsRequest() {
        return readCoilsRequest;
    }

    public void setReadCoilsRequest(ReadCoilsRequest readCoilsRequest) {
        this.readCoilsRequest = readCoilsRequest;
    }

    public ReadCoilsResponse getReadCoilsResponse() {
        return readCoilsResponse;
    }

    public void setReadCoilsResponse(ReadCoilsResponse readCoilsResponse) {
        this.readCoilsResponse = readCoilsResponse;
    }

    public ReadDiscreteInputsRequest getReadDiscreteInputsRequest() {
        return readDiscreteInputsRequest;
    }

    public void setReadDiscreteInputsRequest(ReadDiscreteInputsRequest readDiscreteInputsRequest) {
        this.readDiscreteInputsRequest = readDiscreteInputsRequest;
    }

    public ReadDiscreteInputsResponse getReadDiscreteInputsResponse() {
        return readDiscreteInputsResponse;
    }

    public void setReadDiscreteInputsResponse(ReadDiscreteInputsResponse readDiscreteInputsResponse) {
        this.readDiscreteInputsResponse = readDiscreteInputsResponse;
    }

    public ReadInputRegistersRequest getReadInputRegistersRequest() {
        return readInputRegistersRequest;
    }

    public void setReadInputRegistersRequest(ReadInputRegistersRequest readInputRegistersRequest) {
        this.readInputRegistersRequest = readInputRegistersRequest;
    }

    public ReadInputRegistersResponse getReadInputRegistersResponse() {
        return readInputRegistersResponse;
    }

    public void setReadInputRegistersResponse(ReadInputRegistersResponse readInputRegistersResponse) {
        this.readInputRegistersResponse = readInputRegistersResponse;
    }

    public WriteMultipleCoilsRequest getWriteMultipleCoilsRequest() {
        return writeMultipleCoilsRequest;
    }

    public void setWriteMultipleCoilsRequest(WriteMultipleCoilsRequest writeMultipleCoilsRequest) {
        this.writeMultipleCoilsRequest = writeMultipleCoilsRequest;
    }

    public WriteMultipleCoilsResponse getWriteMultipleCoilsResponse() {
        return writeMultipleCoilsResponse;
    }

    public void setWriteMultipleCoilsResponse(WriteMultipleCoilsResponse writeMultipleCoilsResponse) {
        this.writeMultipleCoilsResponse = writeMultipleCoilsResponse;
    }
    
    public WriteMultipleRegistersRequest getWriteMultipleRegistersRequest() {
		return writeMultipleRegistersRequest;
	}

	public void setWriteMultipleRegistersRequest(WriteMultipleRegistersRequest writeMultipleRegistersRequest) {
		this.writeMultipleRegistersRequest = writeMultipleRegistersRequest;
	}
	
	public WriteMultipleRegistersResponse getWriteMultipleRegistersResponse() {
		return writeMultipleRegistersResponse;
	}

	public void setWriteMultipleRegistersResponse(WriteMultipleRegistersResponse writeMultipleRegistersResponse) {
		this.writeMultipleRegistersResponse = writeMultipleRegistersResponse;
	}

	public ReadHoldRegsRequest getReadHoldRegsRequest() {
		return readHoldRegsRequest;
	}

	public void setReadHoldRegsRequest(ReadHoldRegsRequest readHoldRegsRequest) {
		this.readHoldRegsRequest = readHoldRegsRequest;
	}

	public ReadHoldRegsResponse getReadHoldRegsResponse() {
		return readHoldRegsResponse;
	}

	public void setReadHoldRegsResponse(ReadHoldRegsResponse readHoldRegsResponse) {
		this.readHoldRegsResponse = readHoldRegsResponse;
	}
	
	
}
