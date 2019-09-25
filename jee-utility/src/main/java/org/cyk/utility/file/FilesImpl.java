package org.cyk.utility.file;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.thread.ExecutorServiceBuilder;

@Dependent
public class FilesImpl extends AbstractCollectionInstanceImpl<File> implements Files,Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean isDuplicateChecksumAllowed;
	
	@Override
	protected Boolean __isAddable__(File file) {
		Boolean isDuplicateChecksumAllowed = getIsDuplicateChecksumAllowed();
		return Boolean.TRUE.equals(super.__isAddable__(file)) && (isDuplicateChecksumAllowed==null || Boolean.TRUE.equals(isDuplicateChecksumAllowed) || 
				!collection.stream().map(x -> x.getChecksum()).collect(Collectors.toList()).contains(file.getChecksum()));
	}
	
	@Override
	public Boolean getIsDuplicateChecksumAllowed() {
		return isDuplicateChecksumAllowed;
	}
	
	@Override
	public Files setIsDuplicateChecksumAllowed(Boolean isDuplicateChecksumAllowed) {
		this.isDuplicateChecksumAllowed = isDuplicateChecksumAllowed;
		return this;
	}
	
	@Override
	public Files computeBytes(Boolean isOverridable) {
		if(collection!=null) {
			ExecutorServiceBuilder executorServiceBuilder = __inject__(ExecutorServiceBuilder.class);
			executorServiceBuilder.setCorePoolSize(20).setMaximumPoolSize(100).setQueue(new ArrayBlockingQueue<Runnable>(collection.size()));
			ExecutorService executorService = executorServiceBuilder.execute().getOutput();
			collection.stream().forEach(new Consumer<File>() {
				@Override
				public void accept(File file) {
					if(file.getChecksum() == null || Boolean.TRUE.equals(isOverridable))
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								file.computeBytes();
							}
						});				
				}
			});
			executorService.shutdown();
			try {
				executorService.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
		return this;
	}
	
	@Override
	public Files computeChecksum(Boolean isOverridable) {
		if(collection!=null) {
			/*
			collection.stream().forEach(new Consumer<File>() {
				@Override
				public void accept(File file) {
					if(file.getChecksum() == null || Boolean.TRUE.equals(isOverridable))
						file.computeChecksum();
				}
			});
			*/
			
			ExecutorServiceBuilder executorServiceBuilder = __inject__(ExecutorServiceBuilder.class);
			executorServiceBuilder.setCorePoolSize(20).setMaximumPoolSize(100).setQueue(new ArrayBlockingQueue<Runnable>(collection.size()));
			ExecutorService executorService = executorServiceBuilder.execute().getOutput();
			collection.stream().forEach(new Consumer<File>() {
				@Override
				public void accept(File file) {
					if(file.getChecksum() == null || Boolean.TRUE.equals(isOverridable))
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								file.computeChecksum();
							}
						});				
				}
			});
			executorService.shutdown();
			try {
				executorService.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
		return this;
	}
	
	@Override
	public Files removeDuplicateByChecksum() {
		computeChecksum(Boolean.FALSE);
		removeDuplicate(File::getChecksum);
		return this;
	}
	
	
}
