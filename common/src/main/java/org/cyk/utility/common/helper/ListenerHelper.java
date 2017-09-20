package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod;
import org.cyk.utility.common.helper.MethodHelper.Method;

import lombok.Getter;

@Singleton
public class ListenerHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static ListenerHelper INSTANCE;
	
	public static ListenerHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ListenerHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public <LISTENER,RESULT> RESULT listen(ListenerHelper.Executor<LISTENER,RESULT> executor,Collection<LISTENER> listeners,ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		if(executor == null || CollectionHelper.getInstance().isEmpty(listeners) || StringHelper.getInstance().isBlank(methodName))
			return null;
		executor.setResultMethod(resultMethod);
		return executor.addListener(listeners).execute();
	}
	
	public <LISTENER> void listen(ListenerHelper.Executor<LISTENER,java.lang.Void> executor,Collection<LISTENER> listeners,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		/*if(executor == null || CollectionHelper.getInstance().isEmpty(listeners) || StringHelper.getInstance().isBlank(methodName))
			return;
		executor.setResultMethod(new ListenerHelper.Executor.ResultMethod.Adapter.Default.Void<LISTENER>() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void __execute__(LISTENER listener) {
				MethodHelper.getInstance().call(listener, java.lang.Void.class, methodName,pParameters);
			}
		});
		executor.addListener(listeners).execute();
		*/
		listen(executor, listeners, new ListenerHelper.Executor.ResultMethod.Adapter.Default.Void<LISTENER>() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void __execute__(LISTENER listener) {
				MethodHelper.getInstance().call(listener, java.lang.Void.class, methodName,pParameters);
			}
		}, methodName, pParameters);
	}

	public <LISTENER> void listen(ListenerHelper.Executor<LISTENER,java.lang.Void> executor,LISTENER listener,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		listen(executor, Arrays.asList(listener), methodName, pParameters);
	}
	
	public <LISTENER> void listen(Collection<LISTENER> listeners,String methodName,final MethodHelper.Method.Parameter...pParameters){
		listen(new ListenerHelper.Executor.Procedure.Adapter.Default<LISTENER>(), listeners, methodName,pParameters);
	}
	
	public <LISTENER> void listen(LISTENER listener,String methodName,final MethodHelper.Method.Parameter...pParameters){
		listen(Arrays.asList(listener), methodName, pParameters);
	}
	
	public <LISTENER,RESULT> RESULT listen(Collection<LISTENER> listeners,Class<RESULT> resultClass,ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod,String methodName,final MethodHelper.Method.Parameter...pParameters){
		return listen(new ListenerHelper.Executor.Function.Adapter.Default<LISTENER,RESULT>(resultClass), listeners,resultMethod, methodName,pParameters);
	}

	@SuppressWarnings("unchecked")
	public <LISTENER,RESULT> String listenString(Collection<LISTENER> listeners,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod = (ResultMethod<RESULT, LISTENER>) 
			new org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod.Adapter.Default.String<LISTENER>(){
				private static final long serialVersionUID = 1L;
			protected java.lang.String __execute__() {
				return MethodHelper.getInstance().call(getListener(), java.lang.String.class, methodName,pParameters);
			};
		};
		return (String) listen(new ListenerHelper.Executor.Function.Adapter.Default<LISTENER,RESULT>((Class<RESULT>) String.class), listeners,resultMethod, methodName,pParameters);
	}
	
	@SuppressWarnings("unchecked")
	public <LISTENER,RESULT> Boolean listenBoolean(Collection<LISTENER> listeners,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod = (ResultMethod<RESULT, LISTENER>) 
			new org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod.Adapter.Default.Boolean<LISTENER>(){
				private static final long serialVersionUID = 1L;
			protected java.lang.Boolean __execute__() {
				return MethodHelper.getInstance().call(getListener(), java.lang.Boolean.class, methodName,pParameters);
			};
		};
		return (Boolean) listen(new ListenerHelper.Executor.Function.Adapter.Default<LISTENER,RESULT>((Class<RESULT>) Boolean.class), listeners,resultMethod, methodName,pParameters);
	}
	
	@SuppressWarnings("unchecked")
	public <LISTENER,RESULT> Integer listenInteger(Collection<LISTENER> listeners,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod = (ResultMethod<RESULT, LISTENER>) 
			new org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod.Adapter.Default.Integer<LISTENER>(){
				private static final long serialVersionUID = 1L;
			protected java.lang.Integer __execute__() {
				return MethodHelper.getInstance().call(getListener(), java.lang.Integer.class, methodName,pParameters);
			};
		};
		return (Integer) listen(new ListenerHelper.Executor.Function.Adapter.Default<LISTENER,RESULT>((Class<RESULT>) Integer.class), listeners,resultMethod, methodName,pParameters);
	}
	
	@SuppressWarnings("unchecked")
	public <LISTENER,RESULT> Object listenObject(Collection<LISTENER> listeners,final String methodName,final MethodHelper.Method.Parameter...pParameters){
		ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod = (ResultMethod<RESULT, LISTENER>) 
			new org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod.Adapter.Default.Object<LISTENER>(){
				private static final long serialVersionUID = 1L;
			protected java.lang.Object __execute__() {
				return MethodHelper.getInstance().call(getListener(), java.lang.Object.class, methodName,pParameters);
			};
		};
		return (Object) listen(new ListenerHelper.Executor.Function.Adapter.Default<LISTENER,RESULT>((Class<RESULT>) Object.class), listeners,resultMethod, methodName,pParameters);
	}
	
	public <LISTENER,RESULT> RESULT listen(LISTENER listener,Class<RESULT> resultClass,ListenerHelper.Executor.ResultMethod<RESULT, LISTENER> resultMethod,String methodName,final MethodHelper.Method.Parameter...pParameters){
		return listen(Arrays.asList(listener),resultClass,resultMethod, methodName, pParameters);
	}
	
	public static interface Executor<LISTENER,RESULT> extends Action<Collection<LISTENER>, RESULT>{
		
		ResultMethod<RESULT,LISTENER> getResultMethod();
		Executor<LISTENER,RESULT> setResultMethod(ResultMethod<RESULT,LISTENER> resultMethod);
		
		Boolean getReturnFirstNotNull();
		Executor<LISTENER,RESULT> setReturnFirstNotNull(Boolean returnFirstNotNull);
		
		LISTENER getMatchingListener();
		
		Executor<LISTENER,RESULT> addListener(@SuppressWarnings("unchecked") LISTENER...listeners);
		Executor<LISTENER,RESULT> addListener(Collection<LISTENER> listeners);
		
		@Getter
		public static class Adapter<LISTENER,RESULT> extends Action.Adapter.Default<Collection<LISTENER>,RESULT> implements Executor<LISTENER,RESULT>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected ResultMethod<RESULT,LISTENER> resultMethod;
			protected Boolean returnFirstNotNull;
			protected LISTENER matchingListener;
			
			@SuppressWarnings("unchecked")
			public Adapter(Class<RESULT> outputClass) {
				super("listen", null, null, outputClass);
				setInputClass((Class<Collection<LISTENER>>) ClassHelper.getInstance().getByName(Collection.class.getName()));
			}
			
			@Override
			public Executor<LISTENER, RESULT> addListener(Collection<LISTENER> listeners) {
				return null;
			}
			
			@Override
			public Executor<LISTENER, RESULT> addListener(@SuppressWarnings("unchecked") LISTENER... listeners) {
				return null;
			}
			
			@Override
			public Executor<LISTENER, RESULT> setResultMethod(ResultMethod<RESULT,LISTENER> resultMethod) {
				return null;
			}
			
			@Override
			public Executor<LISTENER, RESULT> setReturnFirstNotNull(Boolean returnFirstNotNull) {
				return null;
			}
			
			public static class Default<LISTENER, RESULT> extends Executor.Adapter<LISTENER, RESULT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<RESULT> outputClass) {
					super(outputClass);
					setIsInputRequired(Boolean.FALSE);
				}
				
				@Override
				protected Boolean isShowInputLogMessage(Collection<LISTENER> input) {
					return Boolean.FALSE;
				}
				
				@Override
				public Executor<LISTENER, RESULT> addListener(Collection<LISTENER> listeners) {
					if(CollectionHelper.getInstance().isNotEmpty(listeners)){
						if(input==null)
							input = new ArrayList<>();
						input.addAll(listeners);	
					}
					return this;
				}
				
				@Override
				public Executor<LISTENER, RESULT> addListener(@SuppressWarnings("unchecked") LISTENER... listeners) {
					if(ArrayHelper.getInstance().isNotEmpty(listeners))
						addListener(Arrays.asList(listeners));
					return this;
				}
				
				@Override
				public Executor<LISTENER, RESULT> setResultMethod(ResultMethod<RESULT,LISTENER> resultMethod) {
					this.resultMethod = resultMethod;
					return this;
				}
				
				@Override
				public Executor<LISTENER, RESULT> setReturnFirstNotNull(Boolean returnFirstNotNull) {
					this.returnFirstNotNull = returnFirstNotNull;
					return this;
				}
				
			}
		}
		
		public static interface ResultMethod<OUTPUT,LISTENER> extends Method<Object, OUTPUT> {
			
			LISTENER getListener();
			ResultMethod<OUTPUT,LISTENER> setListener(LISTENER listener);
			
			@Getter
			public static class Adapter<OUTPUT,LISTENER> extends Method.Adapter.Default<Object, OUTPUT> implements ResultMethod<OUTPUT,LISTENER>,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected LISTENER listener;
				
				public Adapter( Class<OUTPUT> outputClass) {
					super("result", null, null, outputClass);
				}
				
				@Override
				public ResultMethod<OUTPUT, LISTENER> setListener(LISTENER listener) {
					return null;
				}
				
				/**/
				
				@Getter
				public static class Default<OUTPUT,LISTENER> extends ResultMethod.Adapter<OUTPUT,LISTENER> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					protected LISTENER listener;
					
					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
						setIsInputRequired(java.lang.Boolean.FALSE);
					}
					
					@Override
					public ResultMethod<OUTPUT, LISTENER> setListener(LISTENER listener) {
						this.listener = listener;
						return this;
					}
					
					public static class String<LISTENER> extends Default<java.lang.String,LISTENER> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public String() {
							super(java.lang.String.class);
						}
					}
					
					public static class Object<LISTENER> extends Default<java.lang.Object,LISTENER> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Object() {
							super(java.lang.Object.class);
						}
					}
					
					public static class Boolean<LISTENER> extends Default<java.lang.Boolean,LISTENER> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Boolean() {
							super(java.lang.Boolean.class);
						}
					}
					
					public static class Integer<LISTENER> extends Default<java.lang.Integer,LISTENER> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Integer() {
							super(java.lang.Integer.class);
						}
					}
					
					public static class Void<LISTENER> extends Default<java.lang.Void,LISTENER> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Void() {
							super(java.lang.Void.class);
						}
						
						@Override
						protected final java.lang.Void __execute__() {
							__execute__(getListener());
							return Constant.VOID;
						}
						
						protected void __execute__(LISTENER listener){
							ThrowableHelper.getInstance().throwNotYetImplemented();
						}
					}
				}
			}
			
		}
		
		/**/
		
		public static interface Function<LISTENER,RESULT> extends Executor<LISTENER, RESULT>{
			
			public static class Adapter<LISTENER,RESULT> extends Executor.Adapter.Default<LISTENER,RESULT> implements Function<LISTENER,RESULT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<RESULT> outputClass) {
					super(outputClass);
				}
				
				public static class Default<LISTENER, RESULT> extends Function.Adapter<LISTENER, RESULT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<RESULT> outputClass) {
						super(outputClass);
					}
					
					@Override
					protected RESULT __execute__() {
						ResultMethod<RESULT, LISTENER> resultMethod = getResultMethod();
						RESULT result = null;
						Collection<LISTENER> listeners = getInput();
						if(listeners!=null){
							java.lang.Boolean returnFirstNotNull = getReturnFirstNotNull();
							addLoggingMessageBuilderNamedParameters("#listeners",CollectionHelper.getInstance().getSize(listeners),"return if first not null",returnFirstNotNull
									);
							Integer count = 0;
							for(LISTENER listener : listeners){
								count++;
								resultMethod.setProperties(getProperties());
								RESULT value = resultMethod.setListener(listener).execute();
								if(value==null)
									;
								else{
									result = value;
									matchingListener = listener;
									if(java.lang.Boolean.TRUE.equals(returnFirstNotNull))
										break;
								}
							}
							addLoggingMessageBuilderNamedParameters("#called",count);
							if(java.lang.Boolean.TRUE.equals(returnFirstNotNull) && matchingListener!=null)
								addLoggingMessageBuilderNamedParameters("#matching",matchingListener);
						}
						if(result==null)
							result = resultMethod.getNullValue();
						return result;
					}
					
					public static class String<LISTENER> extends Default<LISTENER, java.lang.String> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public String() {
							super(java.lang.String.class);
						}
						
					}
					
					public static class Object<LISTENER> extends Default<LISTENER, java.lang.Object> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Object() {
							super(java.lang.Object.class);
						}
						
					}
					
					public static class Boolean<LISTENER> extends Default<LISTENER, java.lang.Boolean> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Boolean() {
							super(java.lang.Boolean.class);
						}
						
					}
					
				}
			}
		
		}
		
		public static interface Procedure<LISTENER> extends Executor<LISTENER, java.lang.Void>{
			
			public static class Adapter<LISTENER> extends Executor.Adapter.Default<LISTENER,java.lang.Void> implements Procedure<LISTENER>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter() {
					super(java.lang.Void.class);
				}
				
				public static class Default<LISTENER> extends Procedure.Adapter<LISTENER> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					protected Void __execute__() {
						Collection<LISTENER> listeners = getInput();
						if(listeners!=null){
							java.lang.Boolean returnFirstNotNull = getReturnFirstNotNull();
							addLoggingMessageBuilderNamedParameters("#listeners",CollectionHelper.getInstance().getSize(listeners),"return if first not null",returnFirstNotNull
									);
							Integer count = 0;
							for(LISTENER listener : listeners){
								count++;
								resultMethod.setProperties(getProperties());
								resultMethod.setListener(listener).execute();
							}
							addLoggingMessageBuilderNamedParameters("#called",count);
							if(java.lang.Boolean.TRUE.equals(returnFirstNotNull) && matchingListener!=null)
								addLoggingMessageBuilderNamedParameters("#matching",matchingListener);
						}
						return Constant.VOID;
					}
					
				}
			}
		}
	
		/**/

	}

	
}
