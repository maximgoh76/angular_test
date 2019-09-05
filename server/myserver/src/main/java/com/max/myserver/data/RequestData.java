package com.max.myserver.data;

public class RequestData {
	private String sourceFileName;
	private int time1;
	private int time2;
	private int freq1;
	private int freq2;
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public int getTime1() {
		return time1;
	}
	public void setTime1(int time1) {
		this.time1 = time1;
	}
	public int getTime2() {
		return time2;
	}
	public void setTime2(int time2) {
		this.time2 = time2;
	}
	public int getFreq1() {
		return freq1;
	}
	public void setFreq1(int freq1) {
		this.freq1 = freq1;
	}
	public int getFreq2() {
		return freq2;
	}
	public void setFreq2(int freq2) {
		this.freq2 = freq2;
	}
}
