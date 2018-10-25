package org.cyk.utility.css;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

public class StyleBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Style> implements StyleBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Strings classes,values;
	
	@Override
	protected Style __execute__() throws Exception {
		Style style = __inject__(Style.class);
		Strings classes = getClasses();
		if(__injectCollectionHelper__().isNotEmpty(classes))
			style.getClasses(Boolean.TRUE).add(classes);
		
		Strings values = getValues();
		if(__injectCollectionHelper__().isNotEmpty(values))
			style.getValues(Boolean.TRUE).add(values);
		return style;
	}
	
	@Override
	public Strings getClasses() {
		return classes;
	}
	
	@Override
	public Strings getClasses(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_CLASSES, injectIfNull);
	}
	
	@Override
	public StyleBuilder addClasses(String... classes) {
		getClasses(Boolean.TRUE).add(classes);
		return this;
	}

	@Override
	public StyleBuilder setClasses(Strings classes) {
		this.classes = classes;
		return this;
	}

	@Override
	public Strings getValues() {
		return values;
	}
	
	@Override
	public Strings getValues(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_VALUES, injectIfNull);
	}

	@Override
	public StyleBuilder setValues(Strings values) {
		this.values = values;
		return null;
	}
	
	@Override
	public StyleBuilder addValues(String... values) {
		getValues(Boolean.TRUE).add(values);
		return this;
	}
	
	/**/
	
	public static final String FIELD_CLASSES = "classes";
	public static final String FIELD_VALUES = "values";
	
}
