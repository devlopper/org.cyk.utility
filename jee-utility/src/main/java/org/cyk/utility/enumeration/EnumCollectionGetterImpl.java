package org.cyk.utility.enumeration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @SuppressWarnings("rawtypes")
public class EnumCollectionGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection> implements EnumCollectionGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private EnumGetter getter;
	private Collection<String> names;
	
	@Override
	protected Collection __execute__() throws Exception {
		Collection<String> names = getNames();
		Collection<Enum> enums = null;
		if(CollectionHelper.isNotEmpty(names)) {
			enums = new LinkedHashSet<>();
			
			EnumGetter getter = getGetter();
			if(getter == null) {
				getter = __inject__(EnumGetter.class);
				getter.setIsNameCaseSensitive(Boolean.TRUE);
			}
			
			for(String index : names) {
				enums.add(__inject__(EnumGetter.class).setClazz(getter.getClazz()).setName(index).setIsNameCaseSensitive(getter.getIsNameCaseSensitive()).execute().getOutput());
			}
		}
		
		return enums;
	}

	@Override
	public EnumCollectionGetter setGetter(EnumGetter getter) {
		this.getter = getter;
		return this;
	}

	@Override
	public EnumGetter getGetter() {
		return getter;
	}

	@Override
	public EnumCollectionGetter setNames(Collection<String> names) {
		this.names = names;
		return this;
	}

	@Override
	public EnumCollectionGetter addNames(Collection<String> names) {
		this.names = CollectionHelper.add(ArrayList.class, this.names, Boolean.TRUE, names);
		return this;
	}

	@Override
	public EnumCollectionGetter addNames(String... names) {
		addNames(List.of(names));
		return this;
	}

	@Override
	public Collection<String> getNames() {
		return names;
	}
	
	

}
