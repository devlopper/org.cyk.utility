package org.cyk.utility.css;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;

public class StyleImpl extends AbstractObject implements Style,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strings classes,values;
	
	@Override
	public Strings getClasses() {
		return classes;
	}
	
	@Override
	public Strings getClasses(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_CLASSES, injectIfNull);
	}
	
	@Override
	public String getClassesAsString() {
		String string = null;
		Strings classes = getClasses();
		if(classes!=null)
			string = __inject__(StringHelper.class).concatenate(classes.get(), CharacterConstant.SPACE.toString());
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
		return (Strings) __getInjectIfNull__(FIELD_VALUES, injectIfNull);
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
			string = __inject__(StringHelper.class).concatenate(values.get(), CharacterConstant.SEMI_COLON.toString());
		return string;
	}
	
	/**/
	
	public static final String FIELD_CLASSES = "classes";
	public static final String FIELD_VALUES = "values";

}
