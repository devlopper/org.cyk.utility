package org.cyk.utility.common.helper;

import java.io.Serializable;

import org.cyk.utility.common.Builder;

public class PolishNotationHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @author Christian Yao Komenan
	 *
	 */
	//operator operand1 operand2
	public static interface Pre extends Builder.NullableInput<String> {
		
		public static class Adapter extends Builder.NullableInput.Adapter.Default<String> implements Pre,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(String.class);
			}
			
			public static class Default extends Pre.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				
				
			}
			
		}
		
	}
	
}
