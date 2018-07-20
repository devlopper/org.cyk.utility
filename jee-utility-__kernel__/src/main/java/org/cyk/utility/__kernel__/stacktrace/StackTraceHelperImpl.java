package org.cyk.utility.__kernel__.stacktrace;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;

@Singleton
public class StackTraceHelperImpl extends AbstractSingleton implements StackTraceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public StackTraceElement getAt(Integer index){
		return Thread.currentThread().getStackTrace()[index];
	}
	
	@Override
	public StackTraceElement getCurrent(){
		return getAt(3);
	}
	
	/*public String getStackTraceAsString(String packagePrefix){
		List<String> list = new ArrayList<>();
		for(StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()){
			if(packagePrefix==null || stackTraceElement.getClassName().startsWith(packagePrefix))
				list.add(StringUtils.substringAfterLast(stackTraceElement.getClassName(),Constant.CHARACTER_DOT.toString())+Constant.CHARACTER_DOT+stackTraceElement.getMethodName());
		}
		Collections.reverse(list);
		return StringHelper.getInstance().concatenate(list, Constant.CHARACTER_GREATER_THAN);
	}
	
	public String getStackTraceAsString(){
		return getStackTraceAsString(null);
	}*/
	
}
