package org.cyk.utility.string;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

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
	
}
