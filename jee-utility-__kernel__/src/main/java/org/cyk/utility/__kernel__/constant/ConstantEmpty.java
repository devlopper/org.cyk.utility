package org.cyk.utility.__kernel__.constant;

import java.util.Collection;
import java.util.List;

public interface ConstantEmpty {

	String STRING = "";
	
	Collection<String> STRINGS = List.of();
	
	Collection<String> STRINGS_WITH_ONE_ELEMENT = List.of("-*--*-");
}