package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;

@Singleton
public class JQueryHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static JQueryHelper INSTANCE;
	
	public static String JQUERY = "jQuery";
	public static final String SELECTOR_CLASS = "%s(.%s)";
	
	public static JQueryHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new JQueryHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getSelectByClass(String...classes){
		return String.format(SELECTOR_CLASS, JQUERY,StringHelper.getInstance().concatenate(classes, Constant.CHARACTER_COMA.toString()));
	}
	
}
