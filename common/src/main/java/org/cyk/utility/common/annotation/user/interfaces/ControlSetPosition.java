package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ControlSetPosition {

	String identifier();
	ControlSetColumn column();
	ControlSetRow row();
	
}
