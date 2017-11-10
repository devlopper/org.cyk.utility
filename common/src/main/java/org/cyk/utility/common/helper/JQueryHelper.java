package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;

@Singleton
public class JQueryHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static JQueryHelper INSTANCE;
	
	public static String JQUERY = "jQuery";
	public static final String SELECTOR_FORMAT = "%s(%s)";
	public static final String SELECTOR_CLASS_FORMAT = "%s(.%s)";
	
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
	
	public String getSelect(String...parameters){
		return String.format(SELECTOR_FORMAT, JQUERY,StringHelper.getInstance().concatenate(parameters, Constant.CHARACTER_COMA.toString()));
	}
	
	public String getSelectByClass(String...classes){
		return String.format(SELECTOR_CLASS_FORMAT, JQUERY,StringHelper.getInstance().concatenate(classes, Constant.CHARACTER_COMA.toString()));
	}
	
	public String getHide(String selectors,String...parameters){
		return JavaScriptHelper.getInstance().getObjectFunctionCall(getSelect(selectors),FUNCTION_NAME_HIDE);
	} 
	
	public String getShow(String selectors,String...parameters){
		return JavaScriptHelper.getInstance().getObjectFunctionCall(getSelect(selectors),FUNCTION_NAME_SHOW);
	} 
	
	/**/
	
	public static final String FUNCTION_NAME_HIDE = "hide";
	public static final String FUNCTION_NAME_SHOW = "show";
}
