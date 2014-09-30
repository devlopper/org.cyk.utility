package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Text {

	public static enum ValueType{AUTO,ID,VALUE}
	
	ValueType type() default ValueType.AUTO;
	
	String value() default "";
	
}
