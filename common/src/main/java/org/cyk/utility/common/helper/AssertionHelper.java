package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

@Singleton
public class AssertionHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static AssertionHelper INSTANCE;
	
	public static AssertionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new AssertionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static interface Assertion<INPUT> extends org.cyk.utility.common.Action<INPUT,java.lang.Object>  {
		
		public static class Adapter<INPUT> extends org.cyk.utility.common.Action.Adapter.Default<INPUT,java.lang.Object> implements Assertion<INPUT> , Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass,INPUT input) {
				super("assert",inputClass,input,java.lang.Object.class);
			}
			
			/**/
			
			public static class Default<INPUT> extends Assertion.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass,INPUT input,INPUT expected,String message) {
					super(inputClass,input);
					setProperty(PROPERTY_NAME_EXPECTED, expected);
					setProperty(PROPERTY_NAME_MESSAGE, message);
					setIsInputRequired(Boolean.FALSE);
				}
				
				public Default(Class<INPUT> inputClass,INPUT input,INPUT expected) {
					this(inputClass,input,expected,null);
				}
				
				@Override
				protected java.lang.Object __execute__() {
					INPUT input = getInput();
					@SuppressWarnings("unchecked")
					INPUT expected = (INPUT) getProperty(PROPERTY_NAME_EXPECTED);
					String message = (String) getProperty(PROPERTY_NAME_MESSAGE);
					__assert__(message,expected, input);
					return input;
				}
				
				protected void __assert__(String message,INPUT expected,INPUT actual){
					//Assert.assertEquals(message, expected, actual);
					ThrowableHelper.getInstance().throwNotYetImplemented();
				}
				
			}
		}
		
		/**/
		
		public static interface Equals<INPUT> extends Assertion<INPUT>  {
			
			public static class Adapter<INPUT> extends Assertion.Adapter.Default<INPUT> implements Equals<INPUT> , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<INPUT> inputClass,INPUT input,INPUT expected,String message) {
					super(inputClass,input,expected,message);
				}
				
				public Adapter(Class<INPUT> inputClass,INPUT input,INPUT expected) {
					super(inputClass,input,expected);
				}
				
				/**/
				
				public static class Default<INPUT> extends Equals.Adapter<INPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT> inputClass,INPUT input,INPUT expected,String message) {
						super(inputClass,input,expected,message);
					}
					
					public Default(Class<INPUT> inputClass,INPUT input,INPUT expected) {
						super(inputClass,input,expected);
					}
					
					@Override
					protected void __assert__(java.lang.String message, INPUT expected, INPUT actual) {
						Assert.assertEquals(message, expected, actual);
					}
				}
			}
		}
	}
	
	public static  interface Builder<INPUT> extends org.cyk.utility.common.Builder<INPUT, java.lang.Boolean> {
		
		public static class Adapter<INPUT> extends org.cyk.utility.common.Builder.Adapter.Default<INPUT, java.lang.Boolean> implements Builder<INPUT> , Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass, INPUT input) {
				super(inputClass, input, java.lang.Boolean.class);
			}
			
			/**/
			
			public static class Default<INPUT> extends Builder.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass, INPUT input) {
					super(inputClass, input);
				}
				
			}
			
		}
		
		/**/
		
		public static  interface String extends Builder<java.lang.String> {
			
			public static class Adapter extends Builder.Adapter.Default<java.lang.String> implements String , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.String input) {
					super(java.lang.String.class, input);
				}
				
				/**/
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(java.lang.String input) {
						super(input);
					}
					
					@Override
					protected java.lang.Boolean __execute__() {
						java.lang.String string = getInput();
						if(NumberHelper.getInstance().isNumber(string))
							return new Number.Adapter.Default(NumberHelper.getInstance().get(string)).execute();
						return Boolean.parseBoolean(StringUtils.lowerCase(string));
					}
				}	
			}
		}
		
		public static  interface Number extends Builder<java.lang.Number> {
			
			public static class Adapter extends Builder.Adapter.Default<java.lang.Number> implements Number , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.Number input) {
					super(java.lang.Number.class, input);
				}
				
				/**/
				
				public static class Default extends Number.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(java.lang.Number input) {
						super(input);
					}
					
					@Override
					protected java.lang.Boolean __execute__() {
						java.lang.Number number = getInput();
						return number.intValue() != 0 || number.doubleValue()!=0;
					}
				}	
			}
		}
		
		/**/
		
		
		
	}
	
}
