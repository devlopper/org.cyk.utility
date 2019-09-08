package org.cyk.utility.css;
import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface CascadeStyleSheetHelper extends Helper {

	Collection<String> getStyleClassesFromRoles(Collection<?> roles);
	Collection<String> getStyleClassesFromRoles(Object...roles);
	
	String PREFIX = "cyk_";
}