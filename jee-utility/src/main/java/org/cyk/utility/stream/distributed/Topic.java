package org.cyk.utility.stream.distributed;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;

public enum Topic {

	/**
	 * Operations : send
	 */
	MAIL
	/**
	 * Operations : create , read , update , delete
	 */
	,FUNCTION
	;
	
	@Getter private String identifier;
	@Getter @Setter Class<? extends ConsumerMessageProcessor> messageProcessorClass;
	@Getter private Boolean isConsumerStarted;
	
	private Topic() {
		initialise();
	}
	
	public Topic initialise() {
		if(Boolean.TRUE.equals(DependencyInjection.inject(StreamDistributedHelper.class).getIsEnable())) {
			String suffix = name().toLowerCase();
			String systemPropertyName = String.format(SYSTEM_PROPERTY_NAME_FORMAT, suffix);
			identifier = DependencyInjection.inject(ValueHelper.class).defaultToIfNull(
					DependencyInjection.inject(SystemHelper.class).getProperty(systemPropertyName,Boolean.TRUE),suffix);	
		}else {
			
		}
		return this;
	}
	
	public void startConsumer(Boolean isStartable) {
		if(Boolean.TRUE.equals(DependencyInjection.inject(StreamDistributedHelper.class).getIsEnable())) {
			if(Boolean.TRUE.equals(isConsumerStarted)) {
				System.out.println("Distributed stream consumer for <<"+this+">> already started.");	
				return;
			}
			if(isStartable == null)
				isStartable = BooleanHelper.get(DependencyInjection.inject(SystemHelper.class)
					.getProperty(String.format(IS_STARTABLE_FORMAT, name().toLowerCase()),Boolean.TRUE));
			if(Boolean.TRUE.equals(isStartable)) {
				Consumer consumer = DependencyInjection.inject(ConsumerBuilder.class).setTopic(this).execute().getOutput();
				consumer.setIsExecuteAsynchronously(Boolean.TRUE).execute();
				isConsumerStarted = Boolean.TRUE;
				System.out.println("Distributed stream consumer for <<"+this+">> started.");	
			}else {
				
			}	
		}else {
			System.out.println("Cannot start stream consumer for << "+this+">> because distributed stream functionnality is not enable.");
		}
		
	}
	
	public void startConsumer() {
		startConsumer(null);
	}
	
	public void stopConsumer() {
		
		isConsumerStarted = Boolean.FALSE;
		System.out.println("Distributed stream consumer for <<"+this+">> stopped.");
	}
	
	@Override
	public String toString() {
		return identifier == null ? super.toString() : identifier;
	}
	
	/**/
	
	public static void startConsumers(Collection<Topic> topics) {
		if(Boolean.TRUE.equals(DependencyInjection.inject(StreamDistributedHelper.class).getIsEnable())) {
			if(topics!=null)
				topics.forEach(new java.util.function.Consumer<Topic>() {
					@Override
					public void accept(Topic topic) {
						topic.startConsumer();
					}
				});
		}else
			System.out.println("Cannot start consumers for topics<<"+topics+">> because distributed stream functionnality is not enable.");
	}
	
	public static void startConsumers(Topic...topics) {
		if(DependencyInjection.inject(ArrayHelper.class).isNotEmpty(topics))
			startConsumers(List.of(topics));
	}
	
	public static void startAllConsumers() {
		startConsumers(values());
	}
	
	public static void stopConsumers(Collection<Topic> topics) {
		if(topics!=null)
			topics.forEach(new java.util.function.Consumer<Topic>() {
				@Override
				public void accept(Topic topic) {
					if(Boolean.TRUE.equals(topic.isConsumerStarted))
						topic.stopConsumer();
				}
			});
	}
	
	public static void stopConsumers(Topic...topics) {
		if(DependencyInjection.inject(ArrayHelper.class).isNotEmpty(topics))
			stopConsumers(List.of(topics));
	}
	
	public static void stopAllConsumers() {
		stopConsumers(values());
	}
	
	/**/
	
	private static final String SYSTEM_PROPERTY_NAME_FORMAT = "stream.distributed.topic.%s";
	private static final String IS_STARTABLE_FORMAT = "topic.%s.is.startable";
}
