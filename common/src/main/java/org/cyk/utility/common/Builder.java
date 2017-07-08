package org.cyk.utility.common;

import java.io.Serializable;

public interface Builder<OUTPUT> extends Action<Object, OUTPUT> {

	public static class Adapter<OUTPUT> extends Action.Adapter.Default<Object, OUTPUT> implements Builder<OUTPUT>,Serializable {
		private static final long serialVersionUID = 1L;

		public Adapter(Class<OUTPUT> outputClass) {
			super("build", null, null, outputClass, null);
		}
		
		/**/
		
		public static class Default<OUTPUT> extends Builder.Adapter<OUTPUT> implements Serializable {
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
