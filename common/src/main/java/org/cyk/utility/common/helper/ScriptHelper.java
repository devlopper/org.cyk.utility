package org.cyk.utility.common.helper;

import java.io.Serializable;

public class ScriptHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ScriptHelper INSTANCE;
	
	public static ScriptHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ScriptHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String> {
		
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter<String> implements ScriptHelper.Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(String.class);
			}
			
			/**/
			
			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default() {
					setIsInputRequired(Boolean.FALSE);
				}
				
			}	
		}
	}
	
}
