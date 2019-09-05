package com.max.myserver.bl;

import com.max.myserver.data.FileData;

public interface IFileReadyCallBack {
	public void setFileStatus(String fileName, boolean succeded, FileData fileData, String error);
}
