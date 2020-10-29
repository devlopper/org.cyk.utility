package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Producer extends AbstractProducerConsumer implements Serializable {
	
	private Consumer consumer;
	
	@Override
	public void run() {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("consumer", consumer);
		super.run();
	}
	
	@Override
	public Producer setName(String name) {
		return (Producer) super.setName(name);
	}
	
	@Override
	public Producer setNumberOfRunnablesToBeExecuted(Integer numberOfRunnablesToBeExecuted) {
		return (Producer) super.setNumberOfRunnablesToBeExecuted(numberOfRunnablesToBeExecuted);
	}
	
	@Override
	public Producer setExecutorService(ExecutorService executorService) {
		return (Producer) super.setExecutorService(executorService);
	}
	
}