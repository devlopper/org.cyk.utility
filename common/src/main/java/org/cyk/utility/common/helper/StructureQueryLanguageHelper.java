package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class StructureQueryLanguageHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BETWEEN_FORMAT = "%s BETWEEN %s AND %s";
	
	private static StructureQueryLanguageHelper INSTANCE;
	
	public static StructureQueryLanguageHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StructureQueryLanguageHelper();
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
	
}
