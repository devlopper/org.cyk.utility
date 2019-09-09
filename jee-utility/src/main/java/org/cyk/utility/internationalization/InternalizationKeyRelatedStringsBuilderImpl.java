package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;

@Dependent @Deprecated
public class InternalizationKeyRelatedStringsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Strings>> implements InternalizationKeyRelatedStringsBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	private Object key;
	
	@Override
	protected Collection<Strings> __execute__() throws Exception {
		Collection<Strings> collection = null;
		Object key = getKey();
		if(key != null) {
			String keyAsString = __inject__(InternalizationKeyStringBuilder.class).setValue(key).execute().getOutput();
			if(__inject__(StringHelper.class).isNotBlank(keyAsString)) {
				if(StringUtils.contains(keyAsString, ConstantCharacter.DOT)) {
					//y.x => x of y
					for(String index : X_OF_Y) {
						String dotIndex = ConstantCharacter.DOT + index;
						if(StringUtils.endsWith(keyAsString, dotIndex)) {							
							collection = __add__(collection,__inject__(Strings.class).add(index,OF,StringUtils.substringBeforeLast(keyAsString, dotIndex)));
							break;
						}
					}	
				}
			}
		}
		return collection;
	}
	
	private Collection<Strings> __add__(Collection<Strings> collection,Strings strings) {
		if(__injectCollectionHelper__().isNotEmpty(strings)) {
			if(collection == null)
				collection = new ArrayList<Strings>();
			collection.add(strings);
		}
		return collection;
	}
	
	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public InternalizationKeyRelatedStringsBuilder setKey(Object key) {
		this.key = key;
		return this;
	}

	/**/
	
	private static final String OF = "of";
	private static final String[] X_OF_Y = {"type","category"};

}
