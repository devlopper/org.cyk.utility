package org.cyk.utility.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD,ElementType.METHOD})
@Deprecated //TODO to be deleted
public @interface UIField {
	
	public static enum OneRelationshipInputType{AUTO,COMBOBOX,AUTOCOMPLETE,FIELDS,FORM}
	public static enum ManyRelationshipInputType{AUTO,TABLE}
	public static enum TextValueType{I18N_ID,I18N_VALUE,VALUE}
	public static enum SelectOneInputType{COMBOBOX,RADIO}
	public static enum MessageLocation{RIGHT,TOP}
	public static enum SeparatorAfter{AUTO,TRUE,FALSE}
	public static enum BreakLineAfter{AUTO,TRUE,FALSE}
	
	String label() default "";
	TextValueType labelValueType() default TextValueType.I18N_ID;
	
	String description() default "";
	TextValueType descriptionValueType() default TextValueType.I18N_ID;
	
	boolean applicationId() default false;
	String inputType() default "";
	SelectOneInputType selectOneInputType() default SelectOneInputType.COMBOBOX;
	
	int rowSpan() default 1;
	int columnSpan() default 1;
	
	boolean textArea() default false;
	boolean textAreaRich() default false;
	int textRowCount() default 1;
	int textColumnCount() default 20;
	
	boolean ignore() default false;
	
	BreakLineAfter breakLineAfter() default BreakLineAfter.AUTO;
	SeparatorAfter separatorAfter() default SeparatorAfter.AUTO;
	String separatorLabel() default "";
	TextValueType separatorLabelValueType() default TextValueType.I18N_ID;
	
	boolean showCreateButton() default true;
	OneRelationshipInputType oneRelationshipInputType() default OneRelationshipInputType.AUTO;
	ManyRelationshipInputType manyRelationshipInputType() default ManyRelationshipInputType.AUTO;
	Class<?> manyRelationshipClass() default Void.class;
	
	Class<?>[] groups() default {};
	
	/* Table */
	
	boolean tableColumnIgnore() default false;
}