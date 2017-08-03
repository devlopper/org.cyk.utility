package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class StructuredQueryLanguageHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BETWEEN_FORMAT = "%s BETWEEN %s AND %s";
	
	private static StructuredQueryLanguageHelper INSTANCE;
	
	public static StructuredQueryLanguageHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StructuredQueryLanguageHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getBetween(String field,Object from,Object to){
		return String.format(BETWEEN_FORMAT, field,from,to);
	}
	
	/**/
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String>{
		
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable{
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(String.class);
			}
			
			public static class Default extends Builder.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;

				
				
			}
		}
		
	}
	
}
