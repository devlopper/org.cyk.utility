package org.cyk.utility.internationalization;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent @Deprecated
public class InternalizationStringBuilderByStringMapImpl extends AbstractMapInstanceImpl<String, InternalizationStringBuilder> implements InternalizationStringBuilderByStringMap,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InternalizationStringBuilderByStringMap setInternalizationKeyValue(String key, String value) {
		get(key, Boolean.TRUE).setKeyValue(value);
		return this;
	}

}
