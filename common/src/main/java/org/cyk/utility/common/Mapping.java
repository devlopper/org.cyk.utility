package org.cyk.utility.common;

import java.io.Serializable;

public interface Mapping<INPUT,OUTPUT> extends Action<INPUT, OUTPUT> {

	public static class Adapter<INPUT,OUTPUT> extends Action.Adapter.Default<INPUT, OUTPUT> implements Mapping<INPUT,OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		public Adapter(Class<INPUT> inputClass,INPUT input,Class<OUTPUT> outputClass) {
			super("build", inputClass, input, outputClass, null);
		}
		
		/**/
		
		public static class Default<INPUT,OUTPUT> extends Mapping.Adapter<INPUT,OUTPUT> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Class<INPUT> inputClass,INPUT input,Class<OUTPUT> outputClass) {
				super(inputClass,input,outputClass);
			}
			
		}
		
	}
	
}
