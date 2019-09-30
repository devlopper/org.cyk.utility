package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;

public abstract class AbstractInputOutputBuilderImpl<INPUT_OUTPUT extends InputOutput<VALUE>,VALUE> extends AbstractVisibleComponentBuilderImpl<INPUT_OUTPUT> implements InputOutputBuilder<INPUT_OUTPUT, VALUE> {
	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>,Map<String,Field>> CLASSES_FIELDS_MAP = new HashMap<>();
	
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
				if(CollectionHelper.isNotEmpty(fieldNameStrings)) {
					fieldName = FieldHelper.join(fieldNameStrings.get());
					if(StringHelper.isNotBlank(fieldName)) {
						Map<String,Field> map = CLASSES_FIELDS_MAP.get(object.getClass());
						if(map == null)
							CLASSES_FIELDS_MAP.put(object.getClass(), map = new HashMap<>());
						else {
							field = map.get(fieldName);
							if(field == null) {
								String tempFieldName = fieldName;
								String objectFieldName = StringUtils.contains(fieldName, ConstantCharacter.DOT.toString()) ? StringUtils.substringBeforeLast(fieldName, ConstantCharacter.DOT.toString()) : null;
								fieldName = objectFieldName == null ? fieldName : StringUtils.substringAfterLast(fieldName, ConstantCharacter.DOT.toString());
								if(objectFieldName!=null)
									object = FieldHelper.read(object, objectFieldName);
								field = FieldHelper.getByName(object.getClass(), fieldName);
								map.put(tempFieldName, field);
							}
						}	
					}
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
		if(fieldNameStrings == null && Boolean.TRUE.equals(injectIfNull))
			fieldNameStrings = __inject__(Strings.class);
		return fieldNameStrings;
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
