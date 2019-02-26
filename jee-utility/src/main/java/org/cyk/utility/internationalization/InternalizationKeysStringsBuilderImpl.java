package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

public class InternalizationKeysStringsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Strings>> implements InternalizationKeyRelatedStringsBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	
	@Override
	protected Collection<Strings> __execute__() throws Exception {
		Collection<Strings> collection = null;
		
		return collection;
	}
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public InternalizationKeyRelatedStringsBuilder setValue(Object value) {
		this.value = value;
		return this;
	}

	/**/
	
	private static final String DOT = CharacterConstant.DOT.toString();

}
