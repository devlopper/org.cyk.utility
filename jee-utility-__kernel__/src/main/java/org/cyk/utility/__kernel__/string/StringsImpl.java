package org.cyk.utility.__kernel__.string;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;

@Dependent
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
		if(elements == null || elements.isEmpty())
			return this;
		if(prefix == null)
			prefix = ConstantEmpty.STRING;
		final String prefixFinal = prefix;
		elements = elements.stream().map(x -> prefixFinal+x).collect(Collectors.toList());
		return add(elements);
	}

	@Override
	public Strings addWithPrefix(String prefix, String... elements) {
		if(elements == null || elements.length == 0)
			return this;
		return addWithPrefix(prefix,List.of(elements));
	}
	
	@Override
	public String concatenate(Object separator) {
		return StringHelper.concatenate(get(), separator == null ? ConstantEmpty.STRING : separator.toString());
	}
	
	@Override
	public String concatenate() {
		return concatenate(ConstantEmpty.STRING);
	}
}
