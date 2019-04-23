package org.cyk.utility.stream.distributed;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;

public enum Topic {

	MAIL
	
	;
	
	@Getter private String identifier;
	@Getter @Setter Class<? extends ConsumerMessageProcessor> messageProcessorClass;
	
	private Topic() {
		initialise();
	}
	
	public Topic initialise() {
		String suffix = name().toLowerCase();
		String systemPropertyName = String.format(SYSTEM_PROPERTY_NAME_FORMAT, suffix);
		identifier = DependencyInjection.inject(ValueHelper.class).defaultToIfNull(
				DependencyInjection.inject(SystemHelper.class).getProperty(systemPropertyName,Boolean.TRUE),suffix);
		return this;
	}
	
	public void startConsumer() {
		Consumer consumer = DependencyInjection.inject(ConsumerBuilder.class).setTopic(this).execute().getOutput();
		consumer.setIsExecuteAsynchronously(Boolean.TRUE).execute();
		System.out.println("Distributed stream consumer for <<"+this+">> started.");
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	/**/
	
	public static void startConsumers(Collection<Topic> topics) {
		if(topics!=null)
			topics.forEach(new java.util.function.Consumer<Topic>() {
				@Override
				public void accept(Topic topic) {
					topic.startConsumer();
				}
			});
	}
	
	public static void startConsumers(Topic...topics) {
		if(DependencyInjection.inject(ArrayHelper.class).isNotEmpty(topics))
			startConsumers(DependencyInjection.inject(CollectionHelper.class).instanciate(topics));
	}
	
	public static void startAllConsumers() {
		startConsumers(values());
	}
	
	/**/
	
	private static final String SYSTEM_PROPERTY_NAME_FORMAT = "stream.distributed.topic.%s";
}
