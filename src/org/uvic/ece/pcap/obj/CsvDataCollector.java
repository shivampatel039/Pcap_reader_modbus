package org.uvic.ece.pcap.obj;

public class CsvDataCollector {
	
	private int[] 	  sourceIP;
	private int[] 	  destIP;
	private int   	  sourcePort;
	private int   	  destPort;
	private int       ProtocolIdentif;
	private int 	  UnitIdentif;
	private int       TransIdentif;
	private int       FuncCode;
	private int       Refno;
	private int	      WriteData;
	private int 	  RespData;
	
	

	public CsvDataCollector(int[] sourceIP, int[] destIP, int sourcePort,int destPort,int ProtocolIdentif,
							int UnitIdentif,int TransIdentif,int FuncCode,
							int Refno,int WriteData, int RespData){
		this.sourceIP = sourceIP;
		this.destIP = destIP;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
		this.ProtocolIdentif = ProtocolIdentif;
		this.UnitIdentif = UnitIdentif;
		this.TransIdentif = TransIdentif;
		this.FuncCode = FuncCode;
		this.Refno = Refno;
		this.WriteData = WriteData;
		this.RespData = RespData;
		
		
	}
	
	public int[] getsourceIP() {
        return sourceIP;
    }

    public void setsourceIP(int[] sourceIP) {
        this.sourceIP = sourceIP;
    }

    public int[] getdestIP() {
        return destIP;
    }

    public void setdestIP(int[] destIP) {
        this.destIP = destIP;
    }
    
    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }
    
    public int getProtocolIdentif() {
		return ProtocolIdentif;
	}

	public void setProtocolIdentif(int protocolIdentif) {
		ProtocolIdentif = protocolIdentif;
	}

	public int getUnitIdentif() {
		return UnitIdentif;
	}

	public void setUnitIdentif(int unitIdentif) {
		UnitIdentif = unitIdentif;
	}

	public int getTransIdentif() {
		return TransIdentif;
	}

	public void setTransIdentif(int transIdentif) {
		TransIdentif = transIdentif;
	}

	public int getFuncCode() {
		return FuncCode;
	}

	public void setFuncCode(int funcCode) {
		FuncCode = funcCode;
	}

	public int getRefno() {
		return Refno;
	}

	public void setRefno(int refno) {
		Refno = refno;
	}

	public int getWriteData() {
		return WriteData;
	}

	public void setWriteData(int writeData) {
		WriteData = writeData;
	}

	public int getRespData() {
		return RespData;
	}

	public void setRespData(int respData) {
		RespData = respData;
	}

	public String toString() {
    	return  "\""+ sourceIP[0] + "."+ sourceIP[1] + "." + sourceIP[2] + "." + sourceIP[3] +"\""+ "," +"\""+ destIP[0] + "." + destIP[1] + "."+ destIP[2] + "."+ destIP[3] +"\""+ ","
    			+ "\""+ sourcePort + "\""+"," + "\""+destPort+"\""+","+ "\""+ProtocolIdentif + "\""+ "," + "\""+UnitIdentif+ "\""+ ","+ "\""+TransIdentif + "\""+ "," 
    			+ "\""+ FuncCode + "\""+ "," + "\""+ Refno+ "\""+ "," + "\""+ WriteData + "\""+ ","+"\""+ RespData+ "\"";
    } 
	
	
	
}
