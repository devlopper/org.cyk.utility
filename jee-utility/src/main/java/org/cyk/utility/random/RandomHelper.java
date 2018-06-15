package org.cyk.utility.random;

import org.cyk.utility.helper.Helper;

public interface RandomHelper extends Helper {

	String getAlphanumeric(Integer length);
	String getAlphabetic(Integer length);
	Number getNumeric(Integer length);

}
