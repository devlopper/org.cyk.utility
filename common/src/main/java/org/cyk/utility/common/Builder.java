package org.cyk.utility.common;

import java.io.Serializable;

public interface Builder<INPUT,OUTPUT> extends Action<INPUT, OUTPUT> {

	public static class Adapter<INPUT,OUTPUT> extends Action.Adapter.Default<INPUT, OUTPUT> implements Builder<INPUT,OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
			super("build", inputClass, input, outputClass);
		}
		
		/**/
		
		public static class Default<INPUT,OUTPUT> extends Builder.Adapter<INPUT,OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass) {
				super(inputClass, input, outputClass);
			}
			
		}
		
	}

	/**/
	
	public static interface NullableInput<OUTPUT> extends Builder<Object, OUTPUT> {
		
		public static class Adapter<OUTPUT> extends Builder.Adapter.Default<Object, OUTPUT> implements NullableInput<OUTPUT>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(null, null, outputClass);
			}
			
			/**/
			
			public static class Default<OUTPUT> extends NullableInput.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
					setIsInputRequired(Boolean.FALSE);
				}
				
				@Override
				protected Boolean isShowInputLogMessage(Object input) {
					return Boolean.FALSE;
				}
				
				@Override
				protected OUTPUT __execute__() {
					if(Boolean.TRUE.equals(isInstanceNull())){
						return buildWhenInstanceIsNull();
					}else{
						return buildWhenInstanceIsNotNull();
					}
				}
				
				protected Boolean isInstanceNull(){
					return output == null;
				}
				
				protected OUTPUT buildWhenInstanceIsNull(){
					return null;
				}
				
				protected OUTPUT buildWhenInstanceIsNotNull(){
					return output;
				}
				
			}
			
		}
		
	}

	/**/
	
	public interface Stringifier<INPUT> extends Builder<INPUT,String> {

		public static class Adapter<INPUT> extends Builder.Adapter.Default<INPUT, String> implements Stringifier<INPUT>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass, INPUT input) {
				super(inputClass, input, String.class);
				setName("stringify");
			}
			
			/**/
			
			public static class Default<INPUT> extends Stringifier.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass, INPUT input) {
					super(inputClass, input);
				}
				
			}
			
		}
		
	}

}
