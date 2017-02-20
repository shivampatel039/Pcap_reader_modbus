package org.uvic.ece.pcap;

import org.uvic.ece.pcap.obj.*;
import org.uvic.ece.pcap.obj.request.ReadHoldRegsRequest;
import org.uvic.ece.pcap.obj.request.WriteMultipleRegistersRequest;
import org.uvic.ece.pcap.obj.response.ReadHoldRegsResponse;
import org.uvic.ece.pcap.reader.GlobalHeaderReader;
import org.uvic.ece.pcap.reader.PacketDataReader;
import org.uvic.ece.pcap.reader.PacketHeaderReader;
import org.uvic.ece.pcap.reader.buffer.BufferReader;
import org.uvic.ece.pcap.reader.file.FileReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static FileInputStream fileInputStream;


    public static void main(String[] args) throws Exception {

//        if (1 != args.length) {
//            System.out.println("Argument Length: " + args.length);
//            return;
//        }
        //FileInputStream fileInputStream ;

        //System.out.println(fileInputStream);

        try {
        	writeCsvHeader();
            fileInputStream = new FileInputStream(new File("/Users/shivam/Meng project/PcapReader/Feb_07.pcap"));
            System.out.println("Input File: " + fileInputStream);
           // fileInputStream = new FileInputStream(new File(args[0]));
            

            GlobalHeaderReader globalHeaderReader = new GlobalHeaderReader(fileInputStream);
            GlobalHeaderObj globalHeaderObj = globalHeaderReader.read();
            System.out.println("Global Header object: " + globalHeaderObj);

            if (GlobalHeaderObj.NETWORK_ETHERNET != globalHeaderObj.getNetwork()){
            System.out.println("gdfgbdfbg");
            	return;
            }
            FileReader fileReader = new FileReader(fileInputStream, globalHeaderObj.isSwapped());
            PacketHeaderReader packetHeaderReader = new PacketHeaderReader(fileReader);
            int num = 0;
            while (true) {
                num++;
                try {
                    // read packet header
                    PacketHeaderObj packetHeaderObj = packetHeaderReader.read();

                    // read packet body
                    byte[] data = fileReader.read((int) packetHeaderObj.getInclLen());
                    if (packetHeaderObj.getInclLen() != packetHeaderObj.getOrigLen()) {
                        System.err.println("Packet No." + num + ", the original packet was limited");
                        continue;
                    }

                    // parse body
                    try {
                        parseBody(num, data);
                    } catch (Exception e) {
                        System.err.println("Packet No." + num + ", exception occurs, message = " + e.getClass());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        } finally {
            if (null != fileInputStream)
                fileInputStream.close();
        }
    }

    private static void parseBody(int num, byte[] data) throws Exception {
        // prepare for parsing
        BufferReader bufferReader = new BufferReader(data, true);
        PacketDataReader packetDataReader = new PacketDataReader(bufferReader);

        // parse ethernet header
        PacketEthernetObj packetEthernetObj = packetDataReader.parseEthernet();
        if (PacketEthernetObj.TYPE_IPV4 != packetEthernetObj.getType())
            return;

        // parse ipv4 header
        PacketIpv4Obj packetIpv4Obj = packetDataReader.parseIpv4();
        if (PacketIpv4Obj.TYPE_IPV4 != packetIpv4Obj.getType())
            return;
        if (PacketIpv4Obj.PROTOCOL_TCP != packetIpv4Obj.getProtocol())
            return;
        if (PacketIpv4Obj.HEADER_LENGTH != packetIpv4Obj.getHeaderLength()) {
            System.err.println("Packet No." + num + ", the ipv4 data was wrong");
            return;
        } else if (packetIpv4Obj.getTotalLength() + PacketEthernetObj.PACKET_LENGTH != data.length) {
            //System.err.println("Packet No." + num + ", the packet length does not match");
            return;
        }

        // parse tcp header
        PacketTcpObj packetTcpObj = packetDataReader.parseTcp();
        List<PacketModbusBodyObj> packetModbusBodyObjList = new LinkedList<>();
        while (bufferReader.hasRemaining()) {
            if (bufferReader.remaining() <= 8)
                return;

            // parse modbus header
            PacketModbusHeaderObj packetModbusHeaderObj = packetDataReader.parseModbusHeader();
            /*if (PacketModbusHeaderObj.PROTOCOL_IDENTIFIER != packetModbusHeaderObj.getProtocolIdentifier()
                    || PacketModbusHeaderObj.UNIT_IDENTIFIER != packetModbusHeaderObj.getUnitIdentifier())
                return;*/

            int length = packetModbusHeaderObj.getLength();
            if (length <= 2 || length > bufferReader.remaining() + 1)
                return;

            // parse modbus body
            PacketModbusBodyObj packetModbusBodyObj = packetDataReader.parseModbusBody(length);
         
            packetModbusBodyObjList.add(packetModbusBodyObj);
            int[] source = packetIpv4Obj.getSource();
            int[] dest = packetIpv4Obj.getDest();
            int Srcport = packetTcpObj.getSourcePort();
            int Destport = packetTcpObj.getDestPort();
            int ProtocolIdentif = packetModbusHeaderObj.getProtocolIdentifier();
            int UnitIdentif = packetModbusHeaderObj.getUnitIdentifier();
            int TransIdentif = packetModbusHeaderObj.getTransactionIdentifier();
            int FuncCode = packetModbusBodyObj.getFunctionCode();
            String Alarm_speed;
            String Alarm_water_level;
            
            if (FuncCode == 0x03) {
            		ReadHoldRegsRequest readHoldRegsRequest = packetModbusBodyObj.getReadHoldRegsRequest();
            		if (null != readHoldRegsRequest) {
            			int Refno = readHoldRegsRequest.getRefno();
            			int RespData = 0;
            			Alarm_speed = "No";
            			Alarm_water_level = "No";
            			System.out.printf("\"%d\",\"%d.%d.%d.%d\",\"%d.%d.%d.%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"\n", 
                         		num,source[0], source[1], source[2], source[3], dest[0], dest[1], dest[2], dest[3],Srcport, Destport,
                         		ProtocolIdentif, UnitIdentif,TransIdentif, FuncCode, Refno,RespData);
            			CsvDataCollector packet = new CsvDataCollector(source,dest,Srcport,Destport,ProtocolIdentif,UnitIdentif
            														,TransIdentif,FuncCode,Refno,0,RespData,Alarm_speed,Alarm_water_level);
            			writepackets(packet);
            		} else {
            			
            		 ReadHoldRegsResponse readHoldRegsResp = packetModbusBodyObj.getReadHoldRegsResponse();
            		 int RespData = readHoldRegsResp.getRespData();
            		 if ((RespData >= 95) || (RespData <= 5)) {
            			  Alarm_water_level = "Yes";
            			  Alarm_speed = "No";
            		 } else {
            			 Alarm_water_level = "No";
            			 Alarm_speed = "No";
            		 }
            		 int Refno = 0;
            		 CsvDataCollector packet = new CsvDataCollector(source,dest,Srcport,Destport,ProtocolIdentif,UnitIdentif
								,TransIdentif,FuncCode,Refno,0,RespData,Alarm_speed,Alarm_water_level);
            		 writepackets(packet);
            		 System.out.printf("\"%d\",\"%d.%d.%d.%d\",\"%d.%d.%d.%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"\n", 
                     		num,source[0], source[1], source[2], source[3], dest[0], dest[1], dest[2], dest[3],Srcport, Destport,
                     		ProtocolIdentif, UnitIdentif,TransIdentif, FuncCode, Refno,RespData);
            	    }
            	
            } else if (FuncCode == 0x10) {
            	WriteMultipleRegistersRequest writeMultipleRegistersRequest = packetModbusBodyObj.getWriteMultipleRegistersRequest();
            	if (null != writeMultipleRegistersRequest) {
            		int Refno = writeMultipleRegistersRequest.getRefno();
                	int WriteData = writeMultipleRegistersRequest.getWriteData();
                	if ((WriteData > 9) || (WriteData < 0)) {
                		Alarm_speed = "Yes";
                		Alarm_water_level = "No";
                	} else {
                		Alarm_speed = "No";
                		Alarm_water_level = "No";
                	}
                	System.out.printf("\"%d\",\"%d.%d.%d.%d\",\"%d.%d.%d.%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"\n", 
                    		num,source[0], source[1], source[2], source[3], dest[0], dest[1], dest[2], dest[3],Srcport, Destport,
                    		ProtocolIdentif, UnitIdentif,TransIdentif, FuncCode, Refno,WriteData);
                	CsvDataCollector packet = new CsvDataCollector(source,dest,Srcport,Destport,ProtocolIdentif,UnitIdentif
							,TransIdentif,FuncCode,Refno,WriteData,0,Alarm_speed,Alarm_water_level);
                	writepackets(packet);
            	} else {
            		int Refno = 32210;
            		int WriteData = 0;
            		Alarm_speed = "No";
            		Alarm_water_level = "No";
            		CsvDataCollector packet = new CsvDataCollector(source,dest,Srcport,Destport,ProtocolIdentif,UnitIdentif
																	,TransIdentif,FuncCode,Refno,WriteData,0,Alarm_speed,Alarm_water_level);
                	writepackets(packet);
            		System.out.printf("\"%d\",\"%d.%d.%d.%d\",\"%d.%d.%d.%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"\n", 
                    		num,source[0], source[1], source[2], source[3], dest[0], dest[1], dest[2], dest[3],Srcport, Destport,
                    		ProtocolIdentif, UnitIdentif,TransIdentif, FuncCode, Refno,WriteData);
            	}	
            	
            }
            
            System.out.printf("\"%d\",\"%d.%d.%d.%d\",\"%d.%d.%d.%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"\n", 
            		num,source[0], source[1], source[2], source[3], dest[0], dest[1], dest[2], dest[3],Srcport, Destport,
            		ProtocolIdentif, UnitIdentif,TransIdentif, FuncCode);
   
            
        }
    }
    private static void writeCsvHeader() {
		try {
			FileWriter fileWriter = new FileWriter("/Users/shivam/Modbusdata_07.csv");
			fileWriter.append("\"sourceIP\",\"DestIP\",\"SourcePort\",\"destPort\",\"ProtocolIdentif\",\"UnitIdentif\","
					+ "\"TransIdentif\",\"FuncCode\",\"Refno\",\"WriteData\",\"RespData\",\"Alarm_speed\",\"Alarm_water_level\"");
			fileWriter.append("\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
	}

	private static void writepackets(CsvDataCollector packet) {
		// TODO Auto-generated method stub
		try {
			FileWriter fileWriter = new FileWriter("/Users/shivam/Modbusdata_07.csv", true);
			fileWriter.append(String.valueOf(packet));
			fileWriter.append("\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
