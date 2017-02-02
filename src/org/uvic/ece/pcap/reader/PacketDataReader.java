package org.uvic.ece.pcap.reader;

import org.uvic.ece.pcap.obj.*;
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
import org.uvic.ece.pcap.reader.buffer.BufferReader;

public class PacketDataReader {
    private final BufferReader bufferReader;

    public PacketDataReader(BufferReader bufferReader) {
        this.bufferReader = bufferReader;
    }

    public PacketEthernetObj parseEthernet() throws Exception {
        PacketEthernetObj packetEthernetObj = new PacketEthernetObj();

        packetEthernetObj.setSource(bufferReader.read(6));
        packetEthernetObj.setDest(bufferReader.read(6));
        packetEthernetObj.setType(bufferReader.readUint16());

        return packetEthernetObj;
    }

    public PacketIpv4Obj parseIpv4() throws Exception {
        PacketIpv4Obj packetIpv4Obj = new PacketIpv4Obj();

        int data = bufferReader.readUint8();
        packetIpv4Obj.setType(data >> 4 & 0xf);
        packetIpv4Obj.setHeaderLength(data & 0xf);
        bufferReader.readInt8();
        packetIpv4Obj.setTotalLength(bufferReader.readUint16());
        packetIpv4Obj.setIdentification(bufferReader.readUint16());
        bufferReader.readInt16();
        packetIpv4Obj.setTimeToLive(bufferReader.readUint8());
        packetIpv4Obj.setProtocol(bufferReader.readUint8());
        packetIpv4Obj.setHeaderCheckSum(bufferReader.readUint16());
        int[] source = new int[4];
        for (int i = 0; i < 4; i++)
            source[i] = bufferReader.readUint8();
        packetIpv4Obj.setSource(source);
        int[] dest = new int[4];
        for (int i = 0; i < 4; i++)
            dest[i] = bufferReader.readUint8();
        packetIpv4Obj.setDest(dest);

        return packetIpv4Obj;
    }

    public PacketTcpObj parseTcp() throws Exception {
        PacketTcpObj packetTcpObj = new PacketTcpObj();

        packetTcpObj.setSourcePort(bufferReader.readUint16());
        packetTcpObj.setDestPort(bufferReader.readUint16());
        packetTcpObj.setSequenceNum(bufferReader.readUint32());
        packetTcpObj.setAckNum(bufferReader.readUint32());
        packetTcpObj.setHeaderLength(bufferReader.readUint8() / 4);
        bufferReader.readUint8();
        packetTcpObj.setWindowSize(bufferReader.readUint16());
        packetTcpObj.setCheckSum(bufferReader.readUint16());
        packetTcpObj.setUrgentPointer(bufferReader.readUint16());

        int optionLength = packetTcpObj.getHeaderLength() - PacketTcpObj.HEADER_LENGTH;
        if (optionLength > 0) {
            byte[] options = new byte[optionLength];
            bufferReader.get(options);
            packetTcpObj.setOptions(options);
        }

        return packetTcpObj;
    }

    public PacketModbusHeaderObj parseModbusHeader() throws Exception {
        PacketModbusHeaderObj packetModbusHeaderObj = new PacketModbusHeaderObj();

        packetModbusHeaderObj.setTransactionIdentifier(bufferReader.readUint16());
        packetModbusHeaderObj.setProtocolIdentifier(bufferReader.readUint16());
        packetModbusHeaderObj.setLength(bufferReader.readUint16());
        packetModbusHeaderObj.setUnitIdentifier(bufferReader.readUint8());

        return packetModbusHeaderObj;
    }

    public PacketModbusBodyObj parseModbusBody(int length) throws Exception {
        PacketModbusBodyObj packetModbusBodyObj = new PacketModbusBodyObj();

        int functionCode = this.bufferReader.readUint8();
        packetModbusBodyObj.setFunctionCode(functionCode);
        packetModbusBodyObj.setData(bufferReader.read(length - 2));

        switch (functionCode) {
            case 0:
                break;
            case PacketModbusBodyObj.FUNC_READ_COILS:
                parseReadCoils(packetModbusBodyObj);
                break;
            case PacketModbusBodyObj.FUNC_READ_DISCRETE_INPUTS:
                parseReadDiscreteInputs(packetModbusBodyObj);
                break;
            case PacketModbusBodyObj.FUNC_READ_INPUT_REGISTERS:
                parseReadInputRegisters(packetModbusBodyObj);
                break;
            case PacketModbusBodyObj.FUNC_WRITE_MULTI_COILS:
                parseWriteMultiCoils(packetModbusBodyObj);
                break;
            case PacketModbusBodyObj.FUNC_WRITE_MULTI_REGS:
            	parseWriteMultiRegs(packetModbusBodyObj);
            	break;
            case PacketModbusBodyObj.FUNC_READ_HOLD_REGS:
            	parseReadHoldRegs(packetModbusBodyObj);
            	break;
            default:
                throw new Exception();
        }

        return packetModbusBodyObj;
    }
    
    private void parseWriteMultiRegs(PacketModbusBodyObj packetModbusBodyObj) throws Exception {
    	BufferReader bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
    	int Refno = bufferReader.readUint16();
    	int WordCnt = bufferReader.readUint16();
    	if(bufferReader.hasRemaining()){
    		WriteMultipleRegistersRequest writeMultipleRegReq = new WriteMultipleRegistersRequest();
    		
    		writeMultipleRegReq.setRefno(Refno);
    		writeMultipleRegReq.setWordCnt(WordCnt);
    		writeMultipleRegReq.setByteCnt(bufferReader.readUint8());
    		writeMultipleRegReq.setWriteData(bufferReader.readUint16());
    		packetModbusBodyObj.setWriteMultipleRegistersRequest(writeMultipleRegReq);
    	} else {
    		WriteMultipleRegistersResponse writeMultipleRegResp = new WriteMultipleRegistersResponse();
    		
    		writeMultipleRegResp.setRefno(Refno);
    		writeMultipleRegResp.setWordCnt(WordCnt);
    		packetModbusBodyObj.setWriteMultipleRegistersResponse(writeMultipleRegResp);
    	}
    	if(bufferReader.hasRemaining())
            throw new Exception();
    }
    
    private void parseReadHoldRegs(PacketModbusBodyObj packetModbusBodyObj) throws Exception {
    	BufferReader bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
    	
    	if (bufferReader.remaining() == 4) {
    		
    		ReadHoldRegsRequest readHoldRegsReq = new ReadHoldRegsRequest();
    		
    		readHoldRegsReq.setRefno(bufferReader.readUint16());
    		readHoldRegsReq.setWordCnt(bufferReader.readUint16());
    		packetModbusBodyObj.setReadHoldRegsRequest(readHoldRegsReq);
    		
    	} else {
    		
    		ReadHoldRegsResponse readHoldRegsResp = new ReadHoldRegsResponse();
    		
    		readHoldRegsResp.setByteCnt(bufferReader.readUint8());
    		readHoldRegsResp.setRespData(bufferReader.readUint16());
    		packetModbusBodyObj.setReadHoldRegsResponse(readHoldRegsResp);
    	}
    	if(bufferReader.hasRemaining())
            throw new Exception();
    }

    private void parseReadCoils(PacketModbusBodyObj packetModbusBodyObj) throws Exception {
        BufferReader bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
        int byteCount = bufferReader.readUint8();
        if (byteCount == bufferReader.remaining()) {
            byte[] data = bufferReader.read(byteCount);

            ReadCoilsResponse readCoilsResponse = new ReadCoilsResponse();
            readCoilsResponse.setByteCount(byteCount);
            readCoilsResponse.setData(data);
            packetModbusBodyObj.setReadCoilsResponse(readCoilsResponse);
        } else {
            bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
            int startAddress = bufferReader.readUint16();
            int quantityOfOutputs = bufferReader.readUint16();

            ReadCoilsRequest readCoilsRequest = new ReadCoilsRequest();
            readCoilsRequest.setStartAddress(startAddress);
            readCoilsRequest.setQuantityOfOutputs(quantityOfOutputs);
            packetModbusBodyObj.setReadCoilsRequest(readCoilsRequest);
        }
        if(bufferReader.hasRemaining())
            throw new Exception();
    }

    private void parseReadDiscreteInputs(PacketModbusBodyObj packetModbusBodyObj) throws Exception {
        BufferReader bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
        int byteCount = bufferReader.readUint8();
        if (byteCount == bufferReader.remaining()) {
            byte[] data = bufferReader.read(byteCount);

            ReadDiscreteInputsResponse readDiscreteInputsResponse = new ReadDiscreteInputsResponse();
            readDiscreteInputsResponse.setByteCount(byteCount);
            readDiscreteInputsResponse.setData(data);
            packetModbusBodyObj.setReadDiscreteInputsResponse(readDiscreteInputsResponse);
        } else {
            bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
            int startAddress = bufferReader.readUint16();
            int quantityOfInputs = bufferReader.readUint16();

            ReadDiscreteInputsRequest readDiscreteInputsRequest = new ReadDiscreteInputsRequest();
            readDiscreteInputsRequest.setStartAddress(startAddress);
            readDiscreteInputsRequest.setQuantityOfInputs(quantityOfInputs);
            packetModbusBodyObj.setReadDiscreteInputsRequest(readDiscreteInputsRequest);
        }
        if(bufferReader.hasRemaining())
            throw new Exception();
    }

    private void parseReadInputRegisters(PacketModbusBodyObj packetModbusBodyObj) throws Exception {
        BufferReader bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
        int byteCount = bufferReader.readUint8();
        if (byteCount == bufferReader.remaining()) {
            byte[] data = bufferReader.read(byteCount);

            ReadInputRegistersResponse readInputRegistersResponse = new ReadInputRegistersResponse();
            readInputRegistersResponse.setByteCount(byteCount);
            readInputRegistersResponse.setData(data);
            packetModbusBodyObj.setReadInputRegistersResponse(readInputRegistersResponse);
        } else {
            bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
            int startAddress = bufferReader.readUint16();
            int quantityOfInputRegisters = bufferReader.readUint16();

            ReadInputRegistersRequest readInputRegistersRequest = new ReadInputRegistersRequest();
            readInputRegistersRequest.setStartAddress(startAddress);
            readInputRegistersRequest.setQuantityOfInputRegisters(quantityOfInputRegisters);
            packetModbusBodyObj.setReadInputRegistersRequest(readInputRegistersRequest);
        }
        if(bufferReader.hasRemaining())
            throw new Exception();
    }

    private void parseWriteMultiCoils(PacketModbusBodyObj packetModbusBodyObj) throws Exception {
        BufferReader bufferReader = new BufferReader(packetModbusBodyObj.getData(), true);
        int startAddress = bufferReader.readUint16();
        int quantityOfOutputs = bufferReader.readUint16();
        if (!bufferReader.hasRemaining()) {
            WriteMultipleCoilsResponse writeMultipleCoilsResponse = new WriteMultipleCoilsResponse();
            writeMultipleCoilsResponse.setStartAddress(startAddress);
            writeMultipleCoilsResponse.setQuantityOfOutputs(quantityOfOutputs);
            packetModbusBodyObj.setWriteMultipleCoilsResponse(writeMultipleCoilsResponse);
        } else {
            int byteCount = bufferReader.readUint8();
            byte[] data = bufferReader.read(byteCount);

            WriteMultipleCoilsRequest writeMultipleCoilsRequest = new WriteMultipleCoilsRequest();
            writeMultipleCoilsRequest.setStartAddress(startAddress);
            writeMultipleCoilsRequest.setQuantityOfOutputs(quantityOfOutputs);
            writeMultipleCoilsRequest.setByteCount(byteCount);
            writeMultipleCoilsRequest.setData(data);
            packetModbusBodyObj.setWriteMultipleCoilsRequest(writeMultipleCoilsRequest);
        }
        if(bufferReader.hasRemaining())
            throw new Exception();
    }
}
