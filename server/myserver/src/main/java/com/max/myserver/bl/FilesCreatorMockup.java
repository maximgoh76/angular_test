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
				//Thread.sleep(3000);				
				FileData f1= new FileData("file5");//dos
				f1.setFilePath("dos.png");
				callbackClass.setFileStatus("file5", true, f1, null);
				f1= new FileData("file4");//tdo
				f1.setFilePath("dos.png");
				callbackClass.setFileStatus("file4", true, f1, null);
				Thread.sleep(5000);			
				f1= new FileData("file3");//ars
				f1.setFilePath("ars.png");
				callbackClass.setFileStatus("file3", true, f1, null);
				f1= new FileData("file2");//art
				f1.setFilePath("ars.png");
				callbackClass.setFileStatus("file2", true, f1, null);
				//Thread.sleep(3000);
				
				f1= new FileData("file1");//arp
				f1.setFilePath("arp.png");
				callbackClass.setFileStatus("file1", true, f1, null);
				
				//Thread.sleep(3000);
				f1= new FileData("file0");//tcs
				f1.setFilePath("arp.png");
				callbackClass.setFileStatus("file0", true, f1, null);
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
