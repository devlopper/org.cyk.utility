package org.cyk.utility.internationalization;

import java.io.Serializable;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.Case;

public class InternalizationKeyStringBuilderImpl extends AbstractStringFunctionImpl implements InternalizationKeyStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	
	@Override
	protected String __execute__() throws Exception {
		String string = null;
		Object key = getValue();
		if(key!=null) {
			string = __injectStringHelper__().concatenate(
					__injectStringHelper__().applyCase(__injectStringHelper__().splitByCharacterTypeCamelCase(key.toString()),Case.LOWER),CharacterConstant.DOT.toString());
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

}
