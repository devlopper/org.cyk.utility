package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Consumer extends AbstractProducerConsumer implements Serializable {
	
	@Override
	public Consumer setName(String name) {
		return (Consumer) super.setName(name);
	}
	
	@Override
	public Consumer setNumberOfRunnablesToBeExecuted(Integer numberOfRunnablesToBeExecuted) {
		return (Consumer) super.setNumberOfRunnablesToBeExecuted(numberOfRunnablesToBeExecuted);
	}
	
	@Override
	public Consumer setExecutorService(ExecutorService executorService) {
		return (Consumer) super.setExecutorService(executorService);
	}
	
	@Override
	public Consumer setListener(Listener listener) {
		return (Consumer) super.setListener(listener);
	}
	
	@Override
	public Consumer setTimeout(Long timeout) {
		return (Consumer) super.setTimeout(timeout);
	}
	
	@Override
	public Consumer setTimeoutUnit(TimeUnit timeoutUnit) {
		return (Consumer) super.setTimeoutUnit(timeoutUnit);
	}
}