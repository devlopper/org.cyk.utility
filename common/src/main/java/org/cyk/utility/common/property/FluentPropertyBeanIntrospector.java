package org.cyk.utility.common.property;

import java.beans.IntrospectionException;
import java.io.Serializable;

import org.apache.commons.beanutils.IntrospectionContext;
import org.cyk.utility.common.string.RegularExpressionHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class FluentPropertyBeanIntrospector extends org.apache.commons.beanutils.FluentPropertyBeanIntrospector implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String targetClassNameIncludedRegularExpression;
	private String targetClassNameExcludedRegularExpression;
	
	@Override
	public void introspect(IntrospectionContext introspectionContext) throws IntrospectionException {
		if(isIntrospectable(introspectionContext.getTargetClass()))
			super.introspect(introspectionContext);
	}
	
	/*
	public FluentPropertyBeanIntrospector addTargetClassNameIncludedRegularExpression(Collection<String> strings){
		
		targetClassNameIncludedRegularExpression = StringHelper.getInstance().concatenate(CollectionHelper.getInstance().add strings, Constant.CHARACTER_VERTICAL_BAR);
		return this;
	}
	
	public FluentPropertyBeanIntrospector addTargetClassNameIncludedRegularExpression(String...strings){
		addTargetClassNamePrefixesIncluded(CollectionHelper.getInstance().instanciate(strings));
		return this;
	}*/
	
	public Boolean isIntrospectable(String className){
		Boolean included = RegularExpressionHelper.getInstance().hasMatch(className, targetClassNameIncludedRegularExpression);
		Boolean excluded = RegularExpressionHelper.getInstance().hasMatch(className, targetClassNameExcludedRegularExpression);
		//System.out.println("FluentPropertyBeanIntrospector.isIntrospectable() : "+className+" "+Boolean.TRUE.equals(included)+" and "+Boolean.FALSE.equals(excluded));
		return Boolean.TRUE.equals(included) && Boolean.FALSE.equals(excluded);
	}
	
	public Boolean isIntrospectable(Class<?> aClass){
		return isIntrospectable(aClass.getName());
	}
}
