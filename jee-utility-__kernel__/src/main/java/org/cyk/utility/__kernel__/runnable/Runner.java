package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.runnable.Runner.Arguments.SuccessMessageGetter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Run a code and handle exception thrown. Custom message can be sent when success or error.
 * @author CYK
 *
 */
public interface Runner {

	Object run(Arguments arguments);
	
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
	
	public abstract class AbstractImpl extends AbstractObject implements Runner,Serializable {
		private static final long serialVersionUID = 1L;

		public Object run(Arguments arguments) {
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
					if(Boolean.TRUE.equals(ValueHelper.defaultToIfNull(arguments.messageRenderable, Boolean.TRUE))) {
						Message message = arguments.successMessageGetter == null ? SuccessMessageGetter.getDefault(this) : arguments.successMessageGetter.get(this);
						message.setSeverity(arguments.successMessageArguments.getSeverity());
						MessageRenderer.getInstance().render(message, arguments.successMessageArguments.getRenderTypes());
					}
				}			
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
				Throwable cause = ThrowableHelper.getCause(exception);
				arguments.throwable = cause;
				if(Boolean.TRUE.equals(arguments.throwableCatchableOnly)) {
					
				}else {
					if(arguments.throwableMessageArguments == null) {
						throw cause instanceof RuntimeException ? (RuntimeException)cause : new RuntimeException(exception);
					}else {
						arguments.message = new Message().setSeverity(arguments.throwableMessageArguments.getSeverity());
						if(cause instanceof org.cyk.utility.__kernel__.throwable.RuntimeException) {
							org.cyk.utility.__kernel__.throwable.RuntimeException runtimeException = (org.cyk.utility.__kernel__.throwable.RuntimeException) cause;
							if(CollectionHelper.isEmpty(runtimeException.getMessages())) {
								arguments.message.setSummary(runtimeException.getMessage());
								arguments.message.setDetails(runtimeException.getMessage());
							}else {
								Collection<String> summaries = new ArrayList<>();
								Collection<String> details = new ArrayList<>();
								Integer count = 1;
								for(org.cyk.utility.__kernel__.throwable.Message index : runtimeException.getMessages()) {
									String summary = index.getSummary();
									String detail = index.getDetails();
									if(CollectionHelper.getSize(runtimeException.getMessages())> 1) {
										summary = count+ " - " + summary;
										detail = count+ " - " + detail;
									}
									summaries.add(summary);
									details.add(detail);
									count++;
								}
								arguments.message.setSummary(StringHelper.concatenate(summaries, "\r\n"));
								arguments.message.setDetails(StringHelper.concatenate(details, "\r\n"));	
							}					
						}else {
							arguments.message.setSummary(cause.getMessage());
						}
						if(Boolean.TRUE.equals(ValueHelper.defaultToIfNull(arguments.messageRenderable, Boolean.TRUE)))
							MessageRenderer.getInstance().render(arguments.message, arguments.throwableMessageArguments.getRenderTypes());
					}
				}				
			}
			return arguments.getResult();
		}
	}

	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Arguments implements Serializable {
		private Action action;
		private Class<?> actionOnClass;
		private String name;
		private Collection<Runnable> runnables;
		private SuccessMessageGetter successMessageGetter;
		private MessageRenderer.Arguments successMessageArguments;
		private MessageRenderer.Arguments throwableMessageArguments;
		private Boolean throwableCatchableOnly;
		private Boolean messageRenderable;
		private ExecutorService executorService;
		private Long timeOut;
		private TimeUnit timeOutUnit;
		private Object result;
		
		private Message message;
		private Throwable throwable;
		
		public SuccessMessageGetter getSuccessMessageGetter(Boolean injectIfNull) {
			if(successMessageGetter == null && Boolean.TRUE.equals(injectIfNull))
				successMessageGetter = new SuccessMessageGetter.AbstractImpl.Default();
			return successMessageGetter;
		}
		
		
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
		
		public static interface SuccessMessageGetter {
			Message get(Runner runner);
			
			static Message getDefault(Runner runner) {
				String details = "Opération bien éffectuée";
				return new Message().setDetails(details).setSummary(details).setSeverity(Severity.INFORMATION);
			}
			
			public static abstract class AbstractImpl extends AbstractObject implements SuccessMessageGetter,Serializable {
				
				@Override
				public Message get(Runner runner) {
					return getDefault(runner);
				}
				
				
				public static class Default extends AbstractImpl implements Serializable {
					
				}
			}
		}
	}
	
	/**/
	
	static Runner getInstance() {
		return Helper.getInstance(Runner.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}