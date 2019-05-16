package org.cyk.utility.stream.distributed;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;

public enum Topic {

	MAIL
	
	;
	
	@Getter private String identifier;
	@Getter @Setter Class<? extends ConsumerMessageProcessor> messageProcessorClass;
	@Getter private Boolean isConsumerStarted;
	
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
		if(Boolean.TRUE.equals(isConsumerStarted))
			return;
		Boolean isStartable = DependencyInjection.inject(BooleanHelper.class).get(DependencyInjection.inject(SystemHelper.class)
				.getProperty(String.format(IS_STARTABLE_FORMAT, name().toLowerCase()),Boolean.TRUE));
		if(Boolean.TRUE.equals(isStartable)) {
			Consumer consumer = DependencyInjection.inject(ConsumerBuilder.class).setTopic(this).execute().getOutput();
			consumer.setIsExecuteAsynchronously(Boolean.TRUE).execute();
			isConsumerStarted = Boolean.TRUE;
			System.out.println("Distributed stream consumer for <<"+this+">> started.");	
		}
	}
	
	public void stopConsumer() {
		
		isConsumerStarted = Boolean.FALSE;
		System.out.println("Distributed stream consumer for <<"+this+">> stopped.");
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
	private static final String IS_STARTABLE_FORMAT = "topic.%s.is.startable";
}
