package org.uvic.ece.pcap.obj.request;

public class WriteMultipleRegistersRequest {
    private int Refno;
    private int WordCnt;
    private int ByteCnt;
    private int WriteData;
    
    public int getRefno() {
		return Refno;
	}
	public void setRefno(int refno) {
		Refno = refno;
	}
	public int getWordCnt() {
		return WordCnt;
	}
	public void setWordCnt(int wordCnt) {
		WordCnt = wordCnt;
	}
	public int getByteCnt() {
		return ByteCnt;
	}
	public void setByteCnt(int byteCnt) {
		ByteCnt = byteCnt;
	}
	public int getWriteData() {
		return WriteData;
	}
	public void setWriteData(int writeData) {
		WriteData = writeData;
	}
    
}
