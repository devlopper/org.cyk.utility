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

		public static enum CharacterSet{
			DIGIT
			,LETTER
			;
			public static CharacterSet DEFAULT = DIGIT;
		};
		
		CharacterSet getCharacterSet();
		Stringifier<INPUT> setCharacterSet(CharacterSet characterSet);
		Stringifier<INPUT> setCharacterSet(java.lang.String characterSetName);
		
		java.lang.String getPercentageSymbol();
		Stringifier<INPUT> setPercentageSymbol(java.lang.String percentageSymbol);
		
		java.lang.String getLeftPadding();
		Stringifier<INPUT> setLeftPadding(java.lang.String leftPadding);
		
		Integer getWidth();
		Stringifier<INPUT> setWidth(Integer width);
		
		public static class Adapter<INPUT> extends Builder.Adapter.Default<INPUT, String> implements Stringifier<INPUT>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass, INPUT input) {
				super(inputClass, input, String.class);
				setName("stringify");
			}
			
			@Override
			public org.cyk.utility.common.Builder.Stringifier.CharacterSet getCharacterSet() {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.Builder.Stringifier<INPUT> setCharacterSet(org.cyk.utility.common.Builder.Stringifier.CharacterSet characterSet) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.Builder.Stringifier<INPUT> setCharacterSet(String characterSetName) {
				return null;
			}
			
			@Override
			public String getPercentageSymbol() {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.Builder.Stringifier<INPUT> setPercentageSymbol(String percentageSymbol) {
				return null;
			}
			
			@Override
			public String getLeftPadding() {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.Builder.Stringifier<INPUT> setLeftPadding(String leftPadding) {
				return null;
			}
			
			@Override
			public Integer getWidth() {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.Builder.Stringifier<INPUT> setWidth(Integer width) {
				return null;
			}
			
			/**/
			
			public static class Default<INPUT> extends Stringifier.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass, INPUT input) {
					super(inputClass, input);
				}
				
				@Override
				public org.cyk.utility.common.Builder.Stringifier.CharacterSet getCharacterSet() {
					return (org.cyk.utility.common.Builder.Stringifier.CharacterSet) getProperty(PROPERTY_NAME_CHARACTER_SET);
				}
				
				@Override
				public org.cyk.utility.common.Builder.Stringifier<INPUT> setCharacterSet(org.cyk.utility.common.Builder.Stringifier.CharacterSet characterSet) {
					setProperty(PROPERTY_NAME_CHARACTER_SET, characterSet);
					return this;
				}
				
				@Override
				public org.cyk.utility.common.Builder.Stringifier<INPUT> setCharacterSet(String characterSetName) {
					setCharacterSet(CharacterSet.valueOf(characterSetName));
					return this;
				}
				
				@Override
				public String getPercentageSymbol() {
					return getPropertyAsString(PROPERTY_NAME_PERCENTAGE_SYMBOL);
				}
				
				@Override
				public org.cyk.utility.common.Builder.Stringifier<INPUT> setPercentageSymbol(String percentageSymbol) {
					return (org.cyk.utility.common.Builder.Stringifier<INPUT>) setProperty(PROPERTY_NAME_PERCENTAGE_SYMBOL, percentageSymbol);
				}
				
				@Override
				public String getLeftPadding() {
					return getPropertyAsString(PROPERTY_NAME_LEFT_PADDING);
				}
				
				@Override
				public org.cyk.utility.common.Builder.Stringifier<INPUT> setLeftPadding(String leftPadding) {
					return (org.cyk.utility.common.Builder.Stringifier<INPUT>) setProperty(PROPERTY_NAME_LEFT_PADDING, leftPadding);
				}
				
				@Override
				public Integer getWidth() {
					return getPropertyAsNumber(Integer.class,PROPERTY_NAME_WIDTH);
				}
				
				@Override
				public org.cyk.utility.common.Builder.Stringifier<INPUT> setWidth(Integer width) {
					return (org.cyk.utility.common.Builder.Stringifier<INPUT>) setProperty(PROPERTY_NAME_WIDTH, width);
				}
				
			}
			
		}
		
	}

}
