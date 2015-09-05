package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface InputFile {
	
	FileExtensions extensions() default @FileExtensions;
	
	Thumbnail thumbnail() default @Thumbnail;
	
	Distance size() default @Distance(from=@Number(integer=1),to=@Number(integer=1024 * 1));

}
