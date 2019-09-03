package com.max.myserver.bl;

public interface IFileReadyCallBack {
	public void setFileStatus(String fileName, boolean succeded, String filePath, String error);
}
