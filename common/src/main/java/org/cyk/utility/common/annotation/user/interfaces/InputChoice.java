package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cyk.utility.common.helper.SelectItemHelper;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD,ElementType.METHOD})
public @interface InputChoice {

	/**
	 * True if automatically trigger load , false otherwise 
	 * @return
	 */
	boolean load() default true;

	/**
	 * True is null value must be added at first , false otherwise
	 * @return
	 */
	boolean nullable() default true;
	
	/**
	 * Select item builder class
	 * @return
	 */
	Class<? extends SelectItemHelper.Builder.One<?>> itemBuilderClass() default SelectItemHelper.Builder.One.Null.class;
	
	/**
	 * Select items builder class
	 * @return
	 */
	Class<? extends SelectItemHelper.Builder.Many<?>> itemsBuilderClass() default SelectItemHelper.Builder.Many.Null.class;
	
	/**
	 * Set of choices
	 * @return
	 */
	ChoiceSet set() default ChoiceSet.AUTO;
	
	/**/
	
	public enum ChoiceSet{AUTO,YES_NO}
	
	/**/
}
