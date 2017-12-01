package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;

import lombok.Getter;

@Singleton
public class JavaScriptHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 4662624027559597185L;
	
	@Deprecated
	private static final String OPEN_WINDOW_FORMAT = "window.open('%s', '%s', 'location=no,menubar=no,titlebar=no,toolbar=no,width=%s, height=%s');";
	@Deprecated
	private static final String WINDOW_LOCATION = "window.location = '%s';";
	
	public static final String INSTRUCTION_SEPARATOR = ";";
	private static final String DOUBLE_SPACE = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_SPACE.toString();
	private static final String FUNCTION_CALL_FORMAT = "%s(%s);";
	private static final String OBJECT_FUNCTION_CALL_FORMAT = "%s.%s(%s);";
	private static final String FUNCTION_PARAMETER_STRING_FORMAT = "'%s'";
	public static final String OBJECT_THIS = "this";
	
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
	
	public String getFunctionCall(String name,String...parameters){
		return String.format(FUNCTION_CALL_FORMAT, name,StringHelper.getInstance().concatenate(parameters, Constant.CHARACTER_COMA));
	}
	
	public String getObjectFunctionCall(String object,String name,String...parameters){
		return String.format(OBJECT_FUNCTION_CALL_FORMAT, object,name,StringHelper.getInstance().concatenate(parameters, Constant.CHARACTER_COMA));
	}
	
	public String getFunctionCallPreview(String...parameters){
		return getFunctionCall(FUNCTION_NAME_PREVIEW, parameters);
	}
	
	public String getFunctionCallResetInputFile(String...parameters){
		return getFunctionCall(FUNCTION_NAME_RESET_INPUT_FILE, parameters);
	}
	
	public String getFunctionCallSetAttribute(String...parameters){
		return getFunctionCall(FUNCTION_NAME_SET_ATTRIBUTE, parameters);
	}
	
	public String getFunctionCallHideThis(){
		return getFunctionCall(FUNCTION_NAME_HIDE_THIS);
	}
	
	public String formatParameterString(Object value){
		return String.format(FUNCTION_PARAMETER_STRING_FORMAT, value == null ? Constant.EMPTY_STRING : value);
	}
	
	public String getScriptWindowGoBack(){
		return "window.history.back();";
	}
	
	public String getScriptWindowGoToReferrer(){
		return "location.replace(document.referrer);";
	}
	
	public String getScriptWindowGoTo(String url,Boolean goBackIfUrlIsBlank){
		if(StringHelper.getInstance().isBlank(url))
			if(Boolean.TRUE.equals(goBackIfUrlIsBlank))
				return JavaScriptHelper.getInstance().getScriptWindowGoToReferrer();
			else
				return Constant.EMPTY_STRING;
		else 
			return new JavaScriptHelper.Script.Window.Navigate.Adapter.Default().setUniformResourceLocator(url).execute();
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
	
	public static interface Script extends ScriptHelper.Builder {
		
		public static class Adapter extends ScriptHelper.Builder.Adapter.Default implements Script,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Script.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
		}
		
		public static interface Window extends JavaScriptHelper.Script {
			
			org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier();
			Window setUniformResourceLocatorStringifier(UniformResourceLocatorHelper.Stringifier uniformResourceLocatorStringifier);
			Window setUniformResourceLocatorStringifier(String pathIdentifier,Object[] queryKeyValues);
			Window setUniformResourceLocatorStringifier(Constant.Action action,Object object);
			
			String getUniformResourceLocator();
			Window setUniformResourceLocator(String uniformResourceLocator);
			
			String getIdentifier();
			Window setIdentifier(String identifier);
			
			Integer getLeftIndex();
			Window setLeftIndex(Integer leftIndex);
			
			Integer getTopIndex();
			Window setTopIndex(Integer topIndex);
			
			Integer getWidth();
			Window setWidth(Integer width);
			
			Integer getHeight();
			Window setHeight(Integer height);
			
			Map<String,Boolean> getShowableMap();
			Window setShowableMap(Map<String,Boolean> showableMap);
			Window setShowable(String key,Boolean value);
			Boolean getShowable(String identifier,Boolean nullValue);
			Boolean getShowable(String identifier);
			
			String MENU_BAR = "menubar";
			String STATUS_BAR = "status";
			String TITLE_BAR = "TITLE_BAR";
			String TOOL_BAR = "toolbar";
			String SCROLL_BARS = "scrollbars";
			String LOCATION = "location";
			String RESIZABLE = "resizable";
			
			String TOP = "top";
			String LEFT = "left";
			String WIDTH = "width";
			String HEIGHT = "height";
			
			@Getter 
			public static class Adapter extends JavaScriptHelper.Script.Adapter.Default implements Window,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected String identifier;
				protected Integer leftIndex,topIndex,width,height;
				protected Map<String,Boolean> showableMap;
				protected org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier uniformResourceLocatorStringifier;
				protected String uniformResourceLocator;
				
				@Override
				public Window setUniformResourceLocatorStringifier(Action action, Object object) {
					return null;
				}
				
				@Override
				public Window setUniformResourceLocatorStringifier(UniformResourceLocatorHelper.Stringifier uniformResourceLocatorStringifier) {
					return null;
				}
				
				@Override
				public Window setUniformResourceLocatorStringifier(String pathIdentifier, Object[] queryKeyValues) {
					return null;
				}
				
				@Override
				public Boolean getShowable(String identifier) {
					return null;
				}
				
				@Override
				public Boolean getShowable(String identifier,Boolean nullValue) {
					return null;
				}
				
				@Override
				public Window setShowableMap(Map<String, Boolean> showableMap) {
					return null;
				}
				
				@Override
				public Window setShowable(String key, Boolean value) {
					return null;
				}
				
				@Override
				public Window setIdentifier(String identifier) {
					return null;
				}

				@Override
				public Window setLeftIndex(Integer leftIndex) {
					return null;
				}

				@Override
				public Window setTopIndex(Integer topIndex) {
					return null;
				}

				@Override
				public Window setWidth(Integer width) {
					return null;
				}

				@Override
				public Window setHeight(Integer height) {
					return null;
				}
				
				@Override
				public Window setUniformResourceLocator(String uniformResourceLocator) {
					return null;
				}
				
				public static class Default extends Window.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default() {
						setUniformResourceLocatorStringifier(new UniformResourceLocatorHelper.Stringifier.Adapter.Default());
					}
					
					@Override
					public Window setUniformResourceLocator(String uniformResourceLocator) {
						this.uniformResourceLocator = uniformResourceLocator;
						return this;
					}
					
					@Override
					public Window setUniformResourceLocatorStringifier(Action action, Object object) {
						getUniformResourceLocatorStringifier().addQueryParameterAction(action);
						if(object instanceof Class<?>)
							getUniformResourceLocatorStringifier().addQueryParameterClass((Class<?>) object);
						else
							getUniformResourceLocatorStringifier().addQueryParameterIdentifiable(object);
						return this;
					}
					
					@Override
					public Window setUniformResourceLocatorStringifier(UniformResourceLocatorHelper.Stringifier uniformResourceLocatorStringifier) {
						this.uniformResourceLocatorStringifier = uniformResourceLocatorStringifier;
						return this;
					}
					
					@Override
					public Window setUniformResourceLocatorStringifier(String pathIdentifier, Object[] queryKeyValues) {
						getUniformResourceLocatorStringifier().setPathIdentifier(pathIdentifier);
						getUniformResourceLocatorStringifier().addQueryKeyValue(queryKeyValues);
						return this;
					}
					
					@Override
					public Boolean getShowable(String identifier) {
						return getShowable(identifier, Boolean.FALSE);
					}
					
					@Override
					public Boolean getShowable(String identifier,Boolean nullValue) {
						Map<String,Boolean> map = getShowableMap();
						Boolean value = null;
						if(map!=null && map.containsKey(identifier))
							value = map.get(identifier);
						return value == null ? nullValue : value;
					}
					
					@Override
					public Window setShowableMap(Map<String, Boolean> showableMap) {
						this.showableMap = showableMap;
						return this;
					}
					
					@Override
					public Window setShowable(String key, Boolean value) {
						if(this.showableMap==null)
							this.showableMap = new HashMap<>();
						this.showableMap.put(key, value);
						return this;
					}
					
					@Override
					public Window setIdentifier(String identifier) {
						this.identifier = identifier;
						return this;
					}

					@Override
					public Window setLeftIndex(Integer leftIndex) {
						this.leftIndex = leftIndex;
						return this;
					}

					@Override
					public Window setTopIndex(Integer topIndex) {
						this.topIndex = topIndex;
						return this;
					}

					@Override
					public Window setWidth(Integer width) {
						this.width = width;
						return this;
					}

					@Override
					public Window setHeight(Integer height) {
						this.height = height;
						return this;
					}	
				}				
			}
			
			public static interface Navigate extends JavaScriptHelper.Script.Window {
				
				String FORMAT = "window.location.href='%s'";
				
				public static class Adapter extends JavaScriptHelper.Script.Window.Adapter.Default implements Navigate,Serializable {
					private static final long serialVersionUID = 1L;
					
					public static class Default extends Navigate.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						protected String __execute__() {
							String uniformResourceLocator = getUniformResourceLocator();
							return String.format(FORMAT, StringHelper.getInstance().isBlank(uniformResourceLocator) 
									? getUniformResourceLocatorStringifier().execute() : uniformResourceLocator);
						}
					}
				}
			}
			
			public static interface Open extends JavaScriptHelper.Script.Window {
				
				String FORMAT = "window.open('%s','%s','%s','%s')";
				
				public static class Adapter extends JavaScriptHelper.Script.Window.Adapter.Default implements Open,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected Boolean replaced,isResizable;
					
					public static class Default extends Open.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						protected String __execute__() {
							if(leftIndex==null)
								leftIndex = 200;
							if(topIndex==null)
								topIndex=50;
							if(width==null)
								width = 900;
							if(height==null)
								height = 500;
							String s = new MapHelper.Stringifier.Adapter.Default().addKeyValue(TOOL_BAR,StringHelper.getInstance().getResponse(getShowable(TOOL_BAR, Boolean.TRUE),Locale.ENGLISH)
									,SCROLL_BARS,StringHelper.getInstance().getResponse(getShowable(SCROLL_BARS),Locale.ENGLISH)
									,RESIZABLE,StringHelper.getInstance().getResponse(Boolean.TRUE.equals(isResizable),Locale.ENGLISH)
									,STATUS_BAR,StringHelper.getInstance().getResponse(getShowable(STATUS_BAR),Locale.ENGLISH)
									,LOCATION,StringHelper.getInstance().getResponse(getShowable(LOCATION),Locale.ENGLISH)
									,MENU_BAR,StringHelper.getInstance().getResponse(getShowable(MENU_BAR),Locale.ENGLISH),TOP,topIndex,LEFT,leftIndex,WIDTH,width,HEIGHT,height)
									.setSeparator(Constant.CHARACTER_COMA.toString())
									.execute();
							
							//TODO should depends on windows _top _blank and so on
							//getUrlStringBuilder().getQueryStringBuilder().getNameValueCollectionStringBuilder().addDialog();
							
							return String.format(FORMAT, getUniformResourceLocatorStringifier().execute(),getIdentifier(),s,StringHelper.getInstance().getResponse(Boolean.TRUE.equals(replaced),Locale.ENGLISH));
						}
					}
				}				
			}
		}
	}
	
	/**/
	
	public static final String FUNCTION_NAME_PREVIEW = "preview";
	public static final String FUNCTION_NAME_RESET_INPUT_FILE = "resetInputFile";
	public static final String FUNCTION_NAME_SET_ATTRIBUTE = "setAttribute";
	public static final String FUNCTION_NAME_HIDE_THIS = "hideThis";
}
