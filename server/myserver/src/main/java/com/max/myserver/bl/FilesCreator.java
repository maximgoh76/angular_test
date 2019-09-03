package com.max.myserver.bl;

//import java.util.concurrent.CompletableFuture;

public class FilesCreator extends Thread  {

	IFileReadyCallBack callbackClass;
	
	public FilesCreator(IFileReadyCallBack callbackClass) {
		this.callbackClass = callbackClass;
	}
	
	@Override
	public void run() {
			try {
				System.out.println("run started");
				
				callbackClass.setFileStatus("file5", true, "fiel5.jpg", null);
				callbackClass.setFileStatus("file4", true, "fiel4.jpg", null);
				
				Thread.sleep(5000);
				
				callbackClass.setFileStatus("file3", true, "fiel3.jpg", null);
				callbackClass.setFileStatus("file2", true, "fiel2.jpg", null);
				callbackClass.setFileStatus("file1", true, "fiel1.jpg", null);
				
//				CompletableFuture.supplyAsync(()->{
//					callbackClass.setFileStatus("file5", true, "fiel5.jpg", null);
//					return true;
//					}
//				);
//				CompletableFuture.supplyAsync(()->{
//					callbackClass.setFileStatus("file4", true, "fiel4.jpg", null);
//					return true;
//					}
//				);
//				CompletableFuture.supplyAsync(()->{
//					callbackClass.setFileStatus("file3", true, "fiel3.jpg", null);
//					return true;
//					}
//				);
//
//				CompletableFuture.supplyAsync(()->{
//					callbackClass.setFileStatus("file1", true, "fiel1.jpg", null);
//					return true;
//					}
//				);
//
//				CompletableFuture.supplyAsync(()->{
//					callbackClass.setFileStatus("file3", true, "fiel3.jpg", null);
//					return true;
//					}
//				);

				System.out.println("run ended");
			} catch (Exception e) {
				Logger.writeError("FilesCreator", e);
			}
			
		
	}
	 
}
