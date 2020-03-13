package org.cyk.utility.__kernel__.object.__static__.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Text {

	public static enum Type{AUTO,LABEL,DESCRIPTION}
	public static enum ValueType{AUTO,ID,VALUE}
	
	Type type() default Type.AUTO;
	
	ValueType valueType() default ValueType.AUTO;
	
	String value() default "";
	
}
