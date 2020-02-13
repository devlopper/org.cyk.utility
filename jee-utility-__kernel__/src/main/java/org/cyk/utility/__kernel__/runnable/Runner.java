package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Runner {

	default Object run(Arguments arguments) {
		try {
			if(arguments == null)
				return null;
			if(CollectionHelper.isEmpty(arguments.runnables))
				return null;
			if(arguments.executorService == null) {
				for(Runnable runnable : arguments.runnables)
					runnable.run();
			}else {
				for(Runnable index : arguments.runnables)
					arguments.executorService.submit(index);
				arguments.executorService.shutdown();
				Long timeOut = ValueHelper.defaultToIfNull(arguments.timeOut, 60l * 2);
				TimeUnit timeOutUnit = ValueHelper.defaultToIfNull(arguments.timeOutUnit, TimeUnit.SECONDS);		
				if(arguments.executorService.awaitTermination(timeOut, timeOutUnit))
					;
				else
					throw new RuntimeException(arguments.name+" : runnables executor service time out!!!");	
			}
			if(arguments.successMessageArguments == null) {
				
			}else {
				MessageRenderer.getInstance().render("Opération bien éffectuée",arguments.successMessageArguments.getSeverity(), arguments.successMessageArguments.getRenderTypes());
			}			
		} catch (Exception exception) {
			if(arguments.throwableMessageArguments == null) {
				throw new RuntimeException(exception);
			}else {
				Throwable throwable = ThrowableHelper.getInstanceOf(exception, org.cyk.utility.__kernel__.throwable.RuntimeException.class);
				Message message = new Message().setSeverity(arguments.throwableMessageArguments.getSeverity());
				if(throwable instanceof org.cyk.utility.__kernel__.throwable.RuntimeException) {
					org.cyk.utility.__kernel__.throwable.RuntimeException runtimeException = (org.cyk.utility.__kernel__.throwable.RuntimeException) throwable;
					if(CollectionHelper.isEmpty(runtimeException.getMessages())) {
						message.setSummary(runtimeException.toString());
						message.setDetails(runtimeException.toString());
					}else {
						//TODO handle all messages
						org.cyk.utility.__kernel__.throwable.Message runtimeExceptionMessage = CollectionHelper.getFirst(runtimeException.getMessages());
						message.setSummary(runtimeExceptionMessage.getSummary());
						message.setDetails(runtimeExceptionMessage.getDetails());	
					}					
				}else {
					message.setSummary("Erreur lors de l'exécution de l'opération : "+(throwable == null ? exception : throwable).toString());
				}
				MessageRenderer.getInstance().render(message, arguments.throwableMessageArguments.getRenderTypes());
			}
		}
		return arguments.getResult();
	}
	
	default Object run(Collection<Runnable> runnables) {
		if(CollectionHelper.isEmpty(runnables))
			return null;
		Arguments arguments = new Arguments().addRunnables(runnables).assignDefaultMessageArguments();
		return run(arguments);
	}
	
	default Object run(Runnable...runnables) {
		if(ArrayHelper.isEmpty(runnables))
			return null;
		return run(CollectionHelper.listOf(runnables));
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Arguments implements Serializable {
		private String name;
		private Collection<Runnable> runnables;
		private MessageRenderer.Arguments successMessageArguments;
		private MessageRenderer.Arguments throwableMessageArguments;
		private ExecutorService executorService;
		private Long timeOut;
		private TimeUnit timeOutUnit;
		private Object result;
		
		public Collection<Runnable> getRunnables(Boolean injectIfNull) {
			if(runnables == null && Boolean.TRUE.equals(injectIfNull))
				runnables = new ArrayList<>();
			return runnables;
		}
		
		public Arguments addRunnables(Collection<Runnable> runnables) {
			if(CollectionHelper.isEmpty(runnables))
				return this;
			getRunnables(Boolean.TRUE).addAll(runnables);
			return this;
		}
		
		public Arguments addRunnables(Runnable...runnables) {
			if(ArrayHelper.isEmpty(runnables))
				return this;
			return addRunnables(CollectionHelper.listOf(runnables));
		}
		
		public Arguments assignDefaultMessageArguments() {
			successMessageArguments = new MessageRenderer.Arguments(MessageRenderer.Arguments.INFORMATION_INLINE_DIALOG);
			throwableMessageArguments = new MessageRenderer.Arguments(MessageRenderer.Arguments.ERROR_INLINE_DIALOG);
			return this;
		}
	}
	
	/**/
	
	static Runner getInstance() {
		return Helper.getInstance(Runner.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
