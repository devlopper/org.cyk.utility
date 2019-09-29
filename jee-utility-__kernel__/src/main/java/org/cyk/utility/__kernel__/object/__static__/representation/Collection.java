package org.cyk.utility.__kernel__.object.__static__.representation;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.Objectable;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

/**
 * Collection of element
 * @author CYK
 *
 * @param <ELEMENT>
 */
public interface Collection<ELEMENT> extends Objectable {
	
	@SuppressWarnings("unchecked") @JsonbTransient
	default Class<ELEMENT> getElementClass() {
		return (Class<ELEMENT>) FieldHelper.read(this, PROPERTY_ELEMENT_CLASS);
	}
	
	@JsonbTransient
	default Collection<ELEMENT> setElementClass(Class<ELEMENT> elementClass) {
		FieldHelper.write(this, PROPERTY_ELEMENT_CLASS, elementClass);
		return this;
	}
	
	@SuppressWarnings("unchecked") @JsonbTransient
	default ELEMENT instantiateElement() {
		Class<ELEMENT> elementClass = getElementClass();
		if(elementClass == null)
			setElementClass(elementClass = (Class<ELEMENT>) ClassHelper.getByName(StringUtils.substringBeforeLast(getClass().getName(), Collection.class.getSimpleName())));
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("collection element class to be used for instantiation", elementClass);		
		return (ELEMENT) ClassHelper.instanciate(elementClass);
	}
		
	@SuppressWarnings("unchecked")
	default java.util.Collection<ELEMENT> getElements() {
		return (java.util.Collection<ELEMENT>) FieldHelper.read(this, PROPERTY_ELEMENTS);
	}
	
	default Collection<ELEMENT> setElements(java.util.Collection<ELEMENT> elements) {
		FieldHelper.write(this, PROPERTY_ELEMENTS, elements);
		return this;
	}
	
	default Collection<ELEMENT> add(java.util.Collection<ELEMENT> elements) {
		if(elements != null && !elements.isEmpty()) {
			java.util.Collection<ELEMENT> __elements__ = getElements();
			if(__elements__ == null)
				setElements(__elements__ = new ArrayList<ELEMENT>());
			__elements__.addAll(elements);
		}
		return this;
	}
	
	default Collection<ELEMENT> add(@SuppressWarnings("unchecked") ELEMENT...elements) {
		if(elements != null && elements.length > 0)
			add(List.of(elements));
		return this;
	}

	/**/
	
	String PROPERTY_ELEMENT_CLASS = "elementClass";
	String PROPERTY_ELEMENTS = "elements";
}
