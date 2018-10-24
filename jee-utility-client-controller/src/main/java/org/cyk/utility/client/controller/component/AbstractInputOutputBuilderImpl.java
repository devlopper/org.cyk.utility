package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.string.Strings;

public abstract class AbstractInputOutputBuilderImpl<INPUT_OUTPUT extends InputOutput<VALUE>,VALUE> extends AbstractVisibleComponentBuilderImpl<INPUT_OUTPUT> implements InputOutputBuilder<INPUT_OUTPUT, VALUE> {
	private static final long serialVersionUID = 1L;

	protected Object object;
	protected Field field;
	protected Strings fieldNameStrings;
	protected VALUE value;
	
	@Override
	protected void __execute__(INPUT_OUTPUT component) {
		super.__execute__(component);
		component.setValue(getValue());
		Object object = getObject();
		Field field = getField();
		//Object value = getValue();
		if(field == null) {
			if(object!=null) {
				String fieldName = null;
				Strings fieldNameStrings = getFieldNameStrings();
				if(__injectCollectionHelper__().isNotEmpty(fieldNameStrings))
					fieldName = __injectFieldHelper__().concatenate(fieldNameStrings.get());
				if(__injectStringHelper__().isNotBlank(fieldName)) {
					String objectFieldName = StringUtils.contains(fieldName, CharacterConstant.DOT.toString()) ? StringUtils.substringBeforeLast(fieldName, CharacterConstant.DOT.toString()) : null;
					fieldName = objectFieldName == null ? fieldName : StringUtils.substringAfterLast(fieldName, CharacterConstant.DOT.toString());
					if(objectFieldName!=null)
						object = __inject__(FieldValueGetter.class).execute(object, objectFieldName).getOutput();
					field = __injectCollectionHelper__().getFirst(__inject__(FieldGetter.class).execute(object.getClass(), fieldName).getOutput());	
				}
			}
		}
		
		__execute__(component, object, field);
	}
	
	protected abstract VALUE __getValue__(Object object,Field field,Object value);
	
	protected void __execute__(INPUT_OUTPUT component,Object object,Field field) {
		component.setObject(object);
		component.setField(field);
		if(object!=null && field!=null)
			component.setValueFromFieldValue();
	}
	
	@Override
	public Object getObject() {
		return object;
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT,VALUE> setObject(Object object) {
		this.object = object;
		return this;
	}
	
	@Override
	public Field getField() {
		return field;
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT,VALUE> setField(Field field) {
		this.field = field;
		return this;
	}
	
	@Override
	public Strings getFieldNameStrings() {
		return fieldNameStrings;
	}
	
	@Override
	public Strings getFieldNameStrings(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_FIELD_NAME_STRINGS, injectIfNull);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setFieldNameStrings(Strings fieldNameStrings) {
		this.fieldNameStrings = fieldNameStrings;
		return this;
	}
	
	@Override
	public VALUE getValue() {
		return value;
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT,VALUE> setValue(VALUE value) {
		this.value = value;
		return this;
	}
	
	@Override
	public Object getOutputPropertyValue() {
		return getOutputProperty(Properties.VALUE);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setOutputPropertyValue(Object value) {
		return setOutputProperty(Properties.VALUE,value);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setOutputProperty(Object key, Object value) {
		return (InputOutputBuilder<INPUT_OUTPUT, VALUE>) super.setOutputProperty(key, value);
	}

	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setAreaWidthProportions(Integer _default, Integer television,Integer desktop, Integer tablet, Integer phone) {
		return (InputOutputBuilder<INPUT_OUTPUT, VALUE>) super.setAreaWidthProportions(_default, television, desktop, tablet, phone);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setAreaWidthProportionsForNotPhone(Integer width) {
		return (InputOutputBuilder<INPUT_OUTPUT, VALUE>) super.setAreaWidthProportionsForNotPhone(width);
	}
	
	/**/
	
	public static final String FIELD_FIELD_NAME_STRINGS = "fieldNameStrings";
}
