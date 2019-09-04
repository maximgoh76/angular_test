package com.max.myserver.bl;

public class FilesCreatorMockup extends Thread  {

	IFileReadyCallBack callbackClass;
	public FilesCreatorMockup(IFileReadyCallBack callbackClass) {
		this.callbackClass = callbackClass;
	}
	
	@Override
	public void run() {
			try {
				callbackClass.setFileStatus("file5", true, "fiel5.jpg", null);
				callbackClass.setFileStatus("file4", true, "fiel4.jpg", null);
				Thread.sleep(2000);				
				callbackClass.setFileStatus("file3", true, "fiel3.jpg", null);
				callbackClass.setFileStatus("file2", true, "fiel2.jpg", null);
				Thread.sleep(2000);
				callbackClass.setFileStatus("file1", true, "fiel1.jpg", null);
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
