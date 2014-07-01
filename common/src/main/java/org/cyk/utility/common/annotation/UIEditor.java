package org.cyk.utility.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface UIEditor {
	
	public static enum LayoutType{AUTO,GRID}
	
	LayoutType layoutType() default LayoutType.AUTO;
	
	int columnsCount() default 2;
	
	
	
}