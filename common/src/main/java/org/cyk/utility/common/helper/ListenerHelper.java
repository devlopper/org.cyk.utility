package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.helper.MethodHelper.Method;

import lombok.Getter;

@Singleton
public class ListenerHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static interface Executor<LISTENER,RESULT> extends Action<Collection<LISTENER>, RESULT>{
		
		ResultMethod<RESULT,LISTENER> getResultMethod();
		Executor<LISTENER,RESULT> setResultMethod(ResultMethod<RESULT,LISTENER> resultMethod);
		
		Boolean getReturnFirstNotNull();
		Executor<LISTENER,RESULT> setReturnFirstNotNull(Boolean returnFirstNotNull);
		
		LISTENER getMatchingListener();
		
		@Getter
		public static class Adapter<LISTENER,RESULT> extends Action.Adapter.Default<Collection<LISTENER>,RESULT> implements Executor<LISTENER,RESULT>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected ResultMethod<RESULT,LISTENER> resultMethod;
			protected Boolean returnFirstNotNull;
			protected LISTENER matchingListener;
			
			@SuppressWarnings("unchecked")
			public Adapter(Class<RESULT> outputClass) {
				super("listen", null, null, outputClass);
				setInputClass((Class<Collection<LISTENER>>) new ClassHelper().getByName(Collection.class.getName()));
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
						if(listeners!=null)
							for(LISTENER listener : listeners){
								resultMethod.setProperties(getProperties());
								RESULT value = resultMethod.setListener(listener).execute();
								if(value==null)
									;
								else{
									result = value;
									matchingListener = listener;
									if(java.lang.Boolean.TRUE.equals(getReturnFirstNotNull()))
										break;
								}
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
		
		public static interface Procedure<LISTENER,RESULT> extends Executor<LISTENER, RESULT>{
			
			public static class Adapter<LISTENER,RESULT> extends Executor.Adapter.Default<LISTENER,RESULT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<RESULT> outputClass) {
					super(outputClass);
				}
				
				public static class Default<LISTENER, RESULT> extends Procedure.Adapter<LISTENER, RESULT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<RESULT> outputClass) {
						super(outputClass);
					}
					
				}
			}
		}
	}
	
}
