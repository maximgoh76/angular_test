package com.max.myserver.bl;

import com.max.myserver.data.FileData;

public class FilesCreatorMockup extends Thread  {

	IFileReadyCallBack callbackClass;
	public FilesCreatorMockup(IFileReadyCallBack callbackClass) {
		this.callbackClass = callbackClass;
	}
	
	@Override
	public void run() {
			try {
				Thread.sleep(3000);				
				FileData f1= new FileData("dos");
				f1.setFilePath("dos.png");
				callbackClass.setFileStatus("file5", true, f1, null);
				f1= new FileData("tdo");
				f1.setFilePath("dos.png");
				callbackClass.setFileStatus("file4", true, f1, null);
				Thread.sleep(3000);			
				f1= new FileData("ars");
				f1.setFilePath("ars.png");
				callbackClass.setFileStatus("file3", true, f1, null);
				f1= new FileData("art");
				f1.setFilePath("ars.png");
				callbackClass.setFileStatus("file2", true, f1, null);
				Thread.sleep(3000);
				
				f1= new FileData("arp");
				f1.setFilePath("arp.png");
				callbackClass.setFileStatus("file0", true, f1, null);
				
				Thread.sleep(3000);
				f1= new FileData("tcs");
				f1.setFilePath("arp.png");
				callbackClass.setFileStatus("file1", true, f1, null);
//				CompletableFuture.supplyAsync(()->{
//					callbackClass.setFileStatus("file5", true, "fiel5.jpg", null);
//					return true;
//					}
//				);
			} catch (Exception e) {
				Logger.writeError("FilesCreator", e);
			}	
	}
}
