package org.cyk.utility.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.function.FunctionExecutionMessage;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.stream.distributed.ProducerBuilder;
import org.cyk.utility.stream.distributed.StreamDistributedHelper;
import org.cyk.utility.stream.distributed.Topic;

@ApplicationScoped
public class FunctionHelperImpl extends AbstractHelper implements FunctionHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FunctionHelper produce(String function, Map<String, String> inputs, Map<String, String> outputs) {
		if(Boolean.TRUE.equals(__inject__(StreamDistributedHelper.class).getIsEnable())) {
			FunctionExecutionMessage functionExecutionMessage = __inject__(FunctionExecutionMessage.class);
			functionExecutionMessage.setFunction(function).setInputs(inputs).setOutputs(outputs);
			Producer producer = __inject__(ProducerBuilder.class).setTopic(Topic.FUNCTION).execute().getOutput();
			producer.setMessage(UUID.randomUUID().toString(),functionExecutionMessage);		
			producer.execute();	
		}
		return this;
	}

	public static Collection<Runnable> __getRunnables__(Collection<? extends Function<?,?>> functions) {
		Collection<Runnable> runnables = null;
		if(CollectionHelper.isNotEmpty(functions)) {
			runnables = new ArrayList<>();
			for(Function<?,?> index : functions) {
				runnables.add(new Runnable() {
					@Override
					public void run() {
						index.execute();
					}
				});	
			}
		}
		return runnables;
	}
	
}
