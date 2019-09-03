package com.max.myserver.bl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class MediaCreator implements IFileReadyCallBack {
	private String txtInput = null;
	int fileCount = 0;
	int FILES_COUNT_TOTAL= 5;
	
	IFileReadyCallBack callbackClass = null;
	
	public MediaCreator(String input,IFileReadyCallBack callbackClass) {
		this.txtInput = input;
		this.callbackClass = callbackClass;
	}
	
	public CompletionStage<Boolean> runAsync(){
		
		CompletableFuture<Boolean> cf = new CompletableFuture<Boolean>();
		
		FilesCreator filesCreator = new FilesCreator(this);
		filesCreator.start();
		
//		CompletableFuture.supplyAsync(  ()->{
//			FilesCreator filesCreator = new FilesCreator(this);
//			filesCreator.run();
//			cf.complete(new Boolean(true));
//			return new Boolean(true);
//		});
		System.out.println("continue async after call to run");
		cf.complete(true);
		return cf;
	}

	
	
	@Override
	public synchronized void setFileStatus(String fileName,boolean succeded, String filePath, String error) {
		fileCount ++;
		callbackClass.setFileStatus(fileName, succeded, filePath, error);
		if (fileCount==FILES_COUNT_TOTAL) {
			System.out.println("Files Creation Done");
		}
	}
	
}
