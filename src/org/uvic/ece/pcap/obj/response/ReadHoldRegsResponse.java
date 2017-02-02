package org.uvic.ece.pcap.obj.response;

public class ReadHoldRegsResponse {
    private int ByteCnt;
    private int RespData;
    
	public int getByteCnt() {
		return ByteCnt;
	}
	public void setByteCnt(int byteCnt) {
		ByteCnt = byteCnt;
	}
	public int getRespData() {
		return RespData;
	}
	public void setRespData(int respData) {
		RespData = respData;
	}
    
}
