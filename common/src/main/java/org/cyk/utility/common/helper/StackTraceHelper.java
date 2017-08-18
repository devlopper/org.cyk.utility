package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

public class StackTraceHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static StackTraceHelper INSTANCE;
	
	public static StackTraceHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StackTraceHelper();
		return INSTANCE;
	}
	 
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public StackTraceElement getAt(Integer index){
		return Thread.currentThread().getStackTrace()[index];
	}
	
	public StackTraceElement getCurrent(){
		return getAt(3);
	}
	
	public String getStackTraceAsString(String packagePrefix){
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
	}
}
