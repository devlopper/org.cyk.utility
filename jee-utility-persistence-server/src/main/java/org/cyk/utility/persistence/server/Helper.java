package org.cyk.utility.persistence.server;

public interface Helper {

	static String ifTrueYesElseNo(Boolean value) {
		if(Boolean.TRUE.equals(value))
			return YES;
		return NO;
	}
	
	/**/
	
	String YES = "Oui";
	String NO = "Non";
}
