package com.max.myserver.data;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FileItem {
	private String fileName;
	private String filePath;
	private CompletableFuture<Optional<String>> completionStage;
	
	public FileItem(String fileName) {
		this.fileName = fileName;
		this.filePath = null;
		this.completionStage = null;
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
	public CompletableFuture<Optional<String>> getCompletionStage() {
		return completionStage;
	}
	public void setCompletionStage(CompletableFuture<Optional<String>> completionStage) {
		this.completionStage = completionStage;
	}
	
}
