package org.cyk.utility.bean;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

public abstract class AbstractPropertyValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements PropertyValueGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object object;
	private Strings pathStrings;
	private Property property;
	
	@Override
	protected Object __execute__() throws Exception {
		Object value = null;
		Object object = getObject();
		Property property = getProperty();
		if(property == null) {
			Strings pathStrings = getPathStrings();
			if(CollectionHelperImpl.__isNotEmpty__(pathStrings)) {
				property = (Property) org.cyk.utility.__kernel__.field.FieldHelper.read(object,pathStrings.get());
			}
		}
		if(property!=null) {
			if(Boolean.TRUE.equals(property.getIsDerived())) {
				value = property.getValue();
			}else if(Boolean.TRUE.equals(property.getIsDerivable())) {
				value = __derive__(property);
				property.setValue(value);
				property.setIsDerived(Boolean.TRUE);
			}
		}
		return value;
	}
	
	protected Object __derive__(Property property) {
		return property.getValue();
	}
	
	@Override
	public PropertyValueGetter setObject(Object object) {
		this.object = object;
		return this;
	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public PropertyValueGetter setPathStrings(Strings pathStrings) {
		this.pathStrings = pathStrings;
		return this;
	}

	@Override
	public Strings getPathStrings() {
		return pathStrings;
	}
	
	@Override
	public Property getProperty() {
		return property;
	}
	
	@Override
	public PropertyValueGetter setProperty(Property property) {
		this.property = property;
		return this;
	}
	
	@Override
	public PropertyValueGetter addPathStrings(Collection<String> strings) {
		getPathStrings(Boolean.TRUE).add(strings);
		return this;
	}
	
	@Override
	public PropertyValueGetter addPathStrings(String... strings) {
		getPathStrings(Boolean.TRUE).add(strings);
		return this;
	}

	@Override
	public Strings getPathStrings(Boolean injectIfNull) {
		if(pathStrings == null && Boolean.TRUE.equals(injectIfNull))
			pathStrings = DependencyInjection.inject(Strings.class);
		return pathStrings;
	}
	
	/**/
	
	public static final String FIELD_PATH_STRINGS = "pathStrings";
}
