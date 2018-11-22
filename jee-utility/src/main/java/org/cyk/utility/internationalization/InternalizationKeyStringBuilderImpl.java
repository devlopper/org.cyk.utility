package org.cyk.utility.internationalization;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.system.action.SystemAction;

public class InternalizationKeyStringBuilderImpl extends AbstractStringFunctionImpl implements InternalizationKeyStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	private InternalizationKeyStringType type;
	
	@Override
	protected String __execute__() throws Exception {
		String string = null;
		Object key = getValue();
		if(key!=null) {
			if(key instanceof SystemAction)
				key = ((SystemAction)key).getClass();
			
			if(key instanceof Class) {
				Class<?> clazz = (Class<?>) key;
				if(__injectClassHelper__().isInstanceOf(clazz, SystemAction.class))
					key = StringUtils.substringAfter(clazz.getSimpleName(), SystemAction.class.getSimpleName());
				else
					key = __injectStringHelper__().applyCase(clazz.getSimpleName(),Case.FIRST_CHARACTER_LOWER);
				
				//if(__injectClassHelper__().isInstanceOf(clazz, AbstractSystemActionImpl.class))
					key = StringUtils.substringBefore(key.toString(), "Impl");
			}
						
			string = __injectStringHelper__().concatenate(
					__injectStringHelper__().applyCase(__injectStringHelper__().splitByCharacterTypeCamelCase(key.toString()),Case.LOWER),CharacterConstant.DOT.toString());
			
			if(__injectStringHelper__().isNotBlank(string)) {
				InternalizationKeyStringType type = getType();
				if(type!=null)
					string = String.format(type.getFormat(), string);
			}
		}
		return string;
	}
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public InternalizationKeyStringBuilder setValue(Object value) {
		this.value = value;
		return this;
	}
	
	@Override
	public InternalizationKeyStringType getType() {
		return type;
	}
	
	@Override
	public InternalizationKeyStringBuilder setType(InternalizationKeyStringType type) {
		this.type = type;
		return this;
	}

}
