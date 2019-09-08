package org.cyk.utility.css;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.string.Strings;

@Dependent
public class StyleImpl extends AbstractObject implements Style,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strings classes,values;
	
	@Override
	public Strings getClasses() {
		return classes;
	}
	
	@Override
	public Strings getClasses(Boolean injectIfNull) {
		if(classes == null && Boolean.TRUE.equals(injectIfNull))
			classes = DependencyInjection.inject(Strings.class);
		return classes;
	}
	
	@Override
	public String getClassesAsString() {
		String string = null;
		Strings classes = getClasses();
		if(classes!=null)
			string = StringHelperImpl.__concatenate__(classes.get(), ConstantCharacter.SPACE.toString());
		return string;
	}

	@Override
	public Style setClasses(Strings classes) {
		this.classes = classes;
		return this;
	}

	@Override
	public Strings getValues() {
		return values;
	}
	
	@Override
	public Strings getValues(Boolean injectIfNull) {
		if(values == null && Boolean.TRUE.equals(injectIfNull))
			values = DependencyInjection.inject(Strings.class);
		return values;
	}

	@Override
	public Style setValues(Strings values) {
		this.values = values;
		return null;
	}
	
	@Override
	public String getValuesAsString() {
		String string = null;
		Strings values = getValues();
		if(values!=null)
			string = StringHelperImpl.__concatenate__(values.get(), ConstantCharacter.SEMI_COLON.toString());
		return string;
	}
	
	/**/
	
	/**/
	
	@Override
	public String toString() {
		return "style="+getValues()+" ::: class="+getClasses();
	}
	
	/**/
	
	public static final String FIELD_CLASSES = "classes";
	public static final String FIELD_VALUES = "values";

}
