package org.cyk.utility.__kernel__;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public interface InstanceHelper {

	static String __getMethodNameFromFieldName__(String fieldName,String prefix) {
		String methodName = null;
		if(fieldName!=null && fieldName.length()>0) {
			methodName = prefix+StringUtils.substring(fieldName, 0,1).toUpperCase()+StringUtils.substring(fieldName, 1);
		}
		return methodName;
	}
	
	static String getMethodGetterNameFromFieldName(String fieldName) {
		String methodName = null;
		if(fieldName!=null && fieldName.length()>0) {
			methodName = StringUtils.substring(fieldName, 0,1).toUpperCase()+StringUtils.substring(fieldName, 1);
		}
		return methodName;
	}
	
	static String getMethodSetterNameFromFieldName(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	static Object executeMethodGetter(Object object, String fieldName) {
		Object value = null;
		if(object != null && fieldName!=null && fieldName.length()>0) {
			String methodName = __getMethodNameFromFieldName__(fieldName, METHOD_GETTER_PREFIX);
			try {
				value = MethodUtils.invokeMethod(object, methodName);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
		return value;
	}

	static void executeMethodSetter(Object object, String fieldName, Object value) {
		if(object != null  && fieldName!=null) {
			String methodName = __getMethodNameFromFieldName__(fieldName, METHOD_SETTER_PREFIX);
			try {
				value = MethodUtils.invokeMethod(object, methodName,value);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}
	
	/**/
	
	String METHOD_GETTER_PREFIX = "get";
	String METHOD_SETTER_PREFIX = "set";
}
