package org.cyk.utility.internationalization;

import org.cyk.utility.map.MapInstance;

public interface InternalizationStringBuilderByStringMap extends MapInstance<String, InternalizationStringBuilder> {

	InternalizationStringBuilderByStringMap setInternalizationKeyValue(String key,String value);
	
}
