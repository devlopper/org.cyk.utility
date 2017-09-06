package org.cyk.utility.common.formatter;

import java.io.Serializable;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage.Builder;

import lombok.Getter;
import lombok.Setter;

@Deprecated
public interface Formatter<INPUT, OUTPUT> extends Action<INPUT, OUTPUT> {

	public static enum CharacterSet{DIGIT,LETTER};
	
	CharacterSet getCharacterSet();
	Formatter<INPUT, OUTPUT> setCharacterSet(CharacterSet characterSet);
	Formatter<INPUT, OUTPUT> setCharacterSet(java.lang.String characterSetName);
	
	java.lang.String getPercentageSymbol();
	Formatter<INPUT, OUTPUT> setPercentageSymbol(java.lang.String percentageSymbol);
	
	java.lang.String getLeftPadding();
	Formatter<INPUT, OUTPUT> setLeftPadding(java.lang.String leftPadding);
	
	Integer getWidth();
	Formatter<INPUT, OUTPUT> setWidth(Integer width);
	
	/**/
	
	@Getter @Setter @Deprecated
	public static class Adapter<INPUT, OUTPUT> extends Action.Adapter.Default<INPUT, OUTPUT> implements Formatter<INPUT, OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		protected CharacterSet characterSet = CharacterSet.DIGIT;
		protected java.lang.String percentageSymbol = Constant.CHARACTER_PERCENT.toString(),leftPadding=Constant.CHARACTER_SPACE.toString();
		protected Integer width;
		
		public Adapter(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
			super("Format", inputClass, input, outputClass, logMessageBuilder);
		}
		
		@Override
		public Formatter<INPUT, OUTPUT> setCharacterSet(CharacterSet characterSet) {
			this.characterSet = characterSet;
			return this;
		}
		
		@Override
		public Formatter<INPUT, OUTPUT> setCharacterSet(java.lang.String characterSetName) {
			return setCharacterSet(CharacterSet.valueOf(characterSetName));
		}
		
		@Override
		public Formatter<INPUT, OUTPUT> setPercentageSymbol(java.lang.String percentageSymbol) {
			this.percentageSymbol = percentageSymbol;
			return this;
		}
		
		@Override
		public Formatter<INPUT, OUTPUT> setLeftPadding(java.lang.String leftPadding) {
			this.leftPadding = leftPadding;
			return this;
		}
		
		@Override
		public Formatter<INPUT, OUTPUT> setWidth(Integer width) {
			this.width = width;
			return this;
		}
		
		
		
		/**/
		@Getter @Setter @Deprecated
		public static class Default<INPUT, OUTPUT> extends Formatter.Adapter<INPUT, OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Class<INPUT> inputClass, INPUT input, Class<OUTPUT> outputClass,Builder logMessageBuilder) {
				super(inputClass, input, outputClass, logMessageBuilder);
			}
			
		}
		
	}
	
}
