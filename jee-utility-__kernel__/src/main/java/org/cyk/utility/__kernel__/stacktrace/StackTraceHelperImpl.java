package org.cyk.utility.__kernel__.stacktrace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;

@ApplicationScoped
public class StackTraceHelperImpl extends AbstractSingleton implements StackTraceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public StackTraceElement getAt(Integer index){
		StackTraceElement stackTraceElement = null;
		if(index != null && index > -1) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if(index < stackTraceElements.length)
				stackTraceElement = stackTraceElements[index];
		}
		return stackTraceElement;
	}
	
	@Override
	public StackTraceElement getCurrent(){
		return getAt(3);
	}
	
	public String getStackTraceAsString(String packagePrefix){
		List<String> list = new ArrayList<>();
		for(StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()){
			if(packagePrefix==null || stackTraceElement.getClassName().startsWith(packagePrefix))
				list.add(StringUtils.substringAfterLast(stackTraceElement.getClassName(),DOT)+DOT+stackTraceElement.getMethodName());
		}
		Collections.reverse(list);
		return StringUtils.join(list, GREATER_THAN);
	}
	
	public String getStackTraceAsString(){
		return getStackTraceAsString(null);
	}
	
	@Override
	public String getCallerMethodName(Integer offset) {
		if(offset == null)
			offset = 0;
		StackTraceElement stackTraceElement = getAt(4 + offset);
		return stackTraceElement == null ? null : stackTraceElement.getMethodName();
	}
	
	@Override
	public String getCallerMethodName() {
		return getCallerMethodName(1);
	}
	
	/**/
	
	private static final String DOT = ".";
	private static final String GREATER_THAN = ">";
	
}
