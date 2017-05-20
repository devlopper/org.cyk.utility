package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

@Singleton
public class JavaScriptHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 4662624027559597185L;
	
	private static final String OPEN_WINDOW_FORMAT = "window.open('%s', '%s', 'location=no,menubar=no,titlebar=no,toolbar=no,width=%s, height=%s');";
	private static final String WINDOW_LOCATION = "window.location = '%s';";
	
	private static final String INSTRUCTION_SEPARATOR = ";";
	private static final String DOUBLE_SPACE = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_SPACE.toString();
	
	private static JavaScriptHelper INSTANCE;
	
	public static JavaScriptHelper getInstance() {
		if(INSTANCE==null)
			INSTANCE = new JavaScriptHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String concatenate(String...instructions){
		StringBuilder stringBuilder = new StringBuilder();
		for(String instruction : instructions)
			stringBuilder.append(normalize(instruction));
		return stringBuilder.toString();
	}
	
	public String normalize(String instructions){
		instructions=StringUtils.replace(instructions, DOUBLE_SPACE, Constant.CHARACTER_SPACE.toString());
		StringBuilder builder= new StringBuilder(instructions==null?Constant.EMPTY_STRING:instructions);
		if(StringUtils.isNotBlank(instructions) && !StringUtils.endsWith(builder, INSTRUCTION_SEPARATOR))
			builder.append(INSTRUCTION_SEPARATOR);
		return builder.toString();
	}
	
	public String openWindow(String id,String url,Integer width,Integer height){
		return String.format(OPEN_WINDOW_FORMAT, url,id,width,height);
	}
	
	public String windowHref(String url){
		return "window.location='"+url+"';";
	}
	
	public String windowBack(){
		return "window.history.back();";
	}
	
	public String hide(String path){
		return "$('"+path+"').hide();";
	}
	
	public String hide(String[] styleClasses) {
		String script = "";
		for(String styleClasse : styleClasses)
		if(StringUtils.isNotBlank(styleClasse))
			script = concatenate(script,hide("."+styleClasse));
		return script;
	}
	
	
	
	/*public String update(WebInput<?, ?, ?, ?> input,Object value){
		if( Boolean.TRUE.equals(((Input<?, ?, ?, ?, ?, ?>)input).getReadOnly()) && !Boolean.TRUE.equals(((Input<?, ?, ?, ?, ?, ?>)input).getKeepShowingInputOnReadOnly())){
			return "$('."+input.getCss().getUniqueClass()+"').html('"+value+"');";
		}else{
			return "$('."+input.getCss().getUniqueClass()+"').val('"+value+"');";
		}
	}*/
	
	/*public String getFormControlVisibility(Control<?, ?, ?, ?, ?> control,Boolean visible){
		if(control==null)
			return Constant.EMPTY_STRING;
		//We suppose that form is 2 columns based
		//TODO should work with more than 2 columns
		return "$('."+control.getCss().getUniqueClass()+"').closest('table').closest('tr')."+(Boolean.TRUE.equals(visible)?"show":"hide")+"();";
	}*/
	
	public String getWindowLocation(String url){
		return String.format(WINDOW_LOCATION, url);
	}

}
