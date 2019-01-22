package org.cyk.utility.string;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;

public class StringsImpl extends AbstractCollectionInstanceImpl<String> implements Strings,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Strings add(Collection<String> collection) {
		return (Strings) super.add(collection);
	}
	
	@Override
	public Strings add(String... elements) {
		return (Strings) super.add(elements);
	}

	@Override
	public Strings addWithPrefix(String prefix, Collection<String> elements) {
		if(__inject__(StringHelper.class).isNotEmpty(prefix) && elements instanceof List && __inject__(CollectionHelper.class).isNotEmpty(elements)) {
			for(Integer index = 0 ; index < elements.size() ; index = index + 1)
				((List<String>)elements).set(index, prefix+((List<String>)elements).get(index));
		}
		return add(elements);
	}

	@Override
	public Strings addWithPrefix(String prefix, String... elements) {
		return addWithPrefix(prefix,__inject__(CollectionHelper.class).instanciate(elements));
	}
	
	@Override
	public String concatenate(Object separator) {
		return __inject__(StringHelper.class).concatenate(get(), separator == null ? StringConstant.EMPTY : separator.toString());
	}
	
	@Override
	public String concatenate() {
		return concatenate(StringConstant.EMPTY);
	}
}
