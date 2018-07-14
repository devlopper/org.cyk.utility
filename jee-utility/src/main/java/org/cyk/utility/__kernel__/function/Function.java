package org.cyk.utility.__kernel__.function;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.properties.Properties;

public interface Function<INPUT,OUTPUT> extends Objectable {

	Function<INPUT,OUTPUT> setInput(INPUT input);
	Function<INPUT,OUTPUT> execute();
	OUTPUT getOutput();
	
	Function<INPUT,OUTPUT> setProperties(Properties properties);
	
	Function<INPUT,OUTPUT> setIsCatchThrowable(Boolean value);
	Boolean getIsCatchThrowable();
	
	Function<INPUT,OUTPUT> addChild(Object... children);
	
	Function<INPUT,OUTPUT> addChildren(Collection<Object> children);
}
