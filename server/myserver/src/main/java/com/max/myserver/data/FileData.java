package com.max.myserver.data;

public class FileData {
	private String fileName;
	private String filePath;
	private String sourceFileName;
	private int width;
	private int height;
	
	public FileData(String fileName) {
		this.fileName = fileName;
	}
	
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isReady() {
		return (this.getFilePath()==null)? false:true; 
	}
	
	
	
	public String toJson() {
		String result = "{\"fileName\":\"" + this.getFileName() +"\",\"filePath\":\"" + this.getFilePath()  + "\"}";
		return result ;
	}

}
