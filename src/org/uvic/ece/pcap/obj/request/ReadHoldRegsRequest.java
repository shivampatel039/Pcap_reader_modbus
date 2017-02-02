package org.uvic.ece.pcap.obj.request;

public class ReadHoldRegsRequest {
    private int Refno;
    private int WordCnt;
    
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
        
}
