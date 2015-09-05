package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.cyk.utility.common.FileExtensionGroup;

@Retention(RetentionPolicy.RUNTIME)
public @interface FileExtensions {

	String[] values() default {};
	
	FileExtensionGroup[] groups() default {};
	
}
