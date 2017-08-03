package org.cyk.utility.common.helper;

import java.io.Serializable;

public class PredicateHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static PredicateHelper INSTANCE;
	
	public static PredicateHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new PredicateHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public static interface Builder<OUTPUT> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {
		
		//Builder<OUTPUT> set();
		
		public static class Adapter<OUTPUT> extends org.cyk.utility.common.Builder.NullableInput.Adapter<OUTPUT> implements Serializable {

			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}
			
			/**/
			
			public static class Default<OUTPUT> extends Builder.Adapter<OUTPUT> implements Serializable {

				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
			}
			
		}
		
		/**/
		
		public static interface String extends Builder<java.lang.String> {
			
			public static class Adapter extends Builder.Adapter.Default<java.lang.String> implements String,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(java.lang.String.class);
				}
								
				/**/
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
										
				}
				
			}
			
		}
		
	}
	
}
