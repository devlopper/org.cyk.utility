package org.cyk.utility.bean;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.Classes;

public class PropertyImpl extends AbstractObject implements Property,Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	private Boolean isDerivable,isDerived;
	private Classes valueGetterClassQualifiers;
	
	@Override
	public Object read() {
		return  __injectByQualifiersClasses__(PropertyValueGetter.class, __inject__(ArrayHelper.class).instanciate(Class.class, getValueGetterClassQualifiers()))
				.setObject(getParent()).setProperty(this).execute().getOutput();
	}
	
	@Override
	public Property setParent(Object parent) {
		return (Property) super.setParent(parent);
	}
	
	@Override
	public Classes getValueGetterClassQualifiers() {
		return valueGetterClassQualifiers;
	}
	
	@Override
	public Property setValueGetterClassQualifiers(Classes valueGetterClassQualifiers) {
		this.valueGetterClassQualifiers = valueGetterClassQualifiers;
		return this;
	}
	
	@Override
	public Classes getValueGetterClassQualifiers(Boolean injectIfNull) {
		return (Classes) __getInjectIfNull__(FIELD_VALUE_GETTER_CLASS_QUALIFIERS, injectIfNull);
	}
	
	@Override
	public Property addValueGetterClassQualifiers(@SuppressWarnings("rawtypes") Collection<Class> valueGetterClassQualifiers) {
		getValueGetterClassQualifiers(Boolean.TRUE).add(valueGetterClassQualifiers);
		return this;
	}
	
	@Override
	public Property addValueGetterClassQualifiers(@SuppressWarnings("rawtypes") Class... valueGetterClassQualifiers) {
		getValueGetterClassQualifiers(Boolean.TRUE).add(valueGetterClassQualifiers);
		return this;
	}
	
	@Override
	public Object getValue() {
		
		return value;
	}
	
	@Override
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public Boolean getIsDerivable() {
		return isDerivable;
	}
	
	@Override
	public Property setIsDerivable(Boolean isDerivable) {
		this.isDerivable = isDerivable;
		return this;
	}
	
	@Override
	public Boolean getIsDerived() {
		return isDerived;
	}
	
	@Override
	public Property setIsDerived(Boolean isDerived) {
		this.isDerived = isDerived;
		return this;
	}
	
	@Override
	public String toString() {
		return value == null ? super.toString() : value.toString();
	}
	
	/**/
	
	public static final String FIELD_VALUE_GETTER_CLASS_QUALIFIERS = "valueGetterClassQualifiers";
}
