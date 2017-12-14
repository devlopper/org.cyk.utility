package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;

@Singleton
public class JavaServerFacesHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum Library {PRIMEFACES;public static Library DEFAULT = PRIMEFACES;}
	
	private static JavaServerFacesHelper INSTANCE;
	
	public static JavaServerFacesHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new JavaServerFacesHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static class Primefaces extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Script extends AbstractBean implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private static Script INSTANCE;
			
			public static final String PRIMEFACES = "PF";
			public static final String GET_COMPONENT_BY_WIDGET_VAR_FORMAT = PRIMEFACES+"('%s')";
			public static final String CALL_COMPONENT_METHOD_FORMAT = GET_COMPONENT_BY_WIDGET_VAR_FORMAT+".%s(%s)";
			
			public static Script getInstance() {
				if(INSTANCE == null)
					INSTANCE = new Script();
				return INSTANCE;
			}
			
			@Override
			protected void initialisation() {
				INSTANCE = this;
				super.initialisation();
			}
			
			private Properties getProperties(Object object){
				return object instanceof Properties ? (Properties)object : object instanceof AbstractBean ? ((AbstractBean)object).getPropertiesMap() : null;
			}
			
			public String getComponent(Object object){
				return String.format(GET_COMPONENT_BY_WIDGET_VAR_FORMAT,getProperties(object).getWidgetVar());
			}
			
			public String getMethodCall(Object object,String methodName,String parameters){
				return String.format(CALL_COMPONENT_METHOD_FORMAT,getProperties(object).getWidgetVar(),methodName,parameters);
			}
			
			public String getMethodCall(Object object,String methodName){
				return getMethodCall(object, methodName, Constant.EMPTY_STRING);
			}
			
			public String getMethodCallFilter(Object object){
				return getMethodCall(object, FILTER);
			}
			
			public String getMethodCallShow(Object object){
				return getMethodCall(object, SHOW);
			}
			
			public String getMethodCallHide(Object object){
				return getMethodCall(object, HIDE);
			}
			
			public String getMethodCallBlock(Object object){
				return getMethodCall(object, BLOCK);
			}
			
			public String getMethodCallUnBlock(Object object){
				return getMethodCall(object, UN_BLOCK);
			}
			
			/**/
			
			public static final String FILTER = "filter";
			public static final String SHOW = "show";
			public static final String HIDE = "hide";
			public static final String BLOCK = "block";
			public static final String UN_BLOCK = "unblock"; 
			
		}
		
	}
}
