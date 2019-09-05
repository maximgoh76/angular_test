package com.max.myserver.data;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class QueueItem {
	private FileData file = null;
	private CompletableFuture<Optional<FileData>> streamCompleationStage = null;
	
	public CompletableFuture<Optional<FileData>> getStreamCompleationStage() {
		return streamCompleationStage;
	}
	public void setStreamCompleationStage(CompletableFuture<Optional<FileData>> streamCompleationStage) {
		this.streamCompleationStage = streamCompleationStage;
	}
	public FileData getFile() {
		return file;
	}
	public void setFile(FileData file) {
		this.file = file;
	}
}
