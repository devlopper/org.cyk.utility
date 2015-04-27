package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface InputCalendar {

	public enum Format{AUTO,DATE_SHORT,DATE_LONG,TIME,DATETIME_SHORT,DATETIME_LONG}
	
	Format format() default Format.AUTO;
	
}
