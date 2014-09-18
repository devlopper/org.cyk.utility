package org.cyk.utility.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelBean {
	
	public static enum GenderType{UNSET,MALE,FEMALE}
	public static enum CrudStrategy{ENUMERATION,BUSINESS,INTERNAL,INHERITED}
	public static enum CrudInheritanceStrategy{ALL,CHILDREN_ONLY}
	
	GenderType genderType() default GenderType.UNSET;
	CrudStrategy crudStrategy();
	CrudInheritanceStrategy crudInheritanceStrategy() default CrudInheritanceStrategy.ALL;
	
	String uiIconName() default "";
	
}