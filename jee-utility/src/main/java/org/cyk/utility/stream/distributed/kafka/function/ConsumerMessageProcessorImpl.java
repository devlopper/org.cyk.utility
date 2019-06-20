package org.cyk.utility.stream.distributed.kafka.function;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.stream.distributed.AbstractConsumerMessageProcessorImpl;

@Dependent
public class ConsumerMessageProcessorImpl extends AbstractConsumerMessageProcessorImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __process__(org.cyk.utility.stream.distributed.Message message) {
		
	}
	
}