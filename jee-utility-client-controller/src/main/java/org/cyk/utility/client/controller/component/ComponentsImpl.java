package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;

public class ComponentsImpl extends AbstractCollectionInstanceImpl<Component> implements Components,Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	public Layout getLayout() {
		return layout;
	}

	@Override
	public Components setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}
	
	@Override
	public Components setInputOutputValueFromFieldValue() {
		Collection<Component> collection = get();
		if(__inject__(CollectionHelper.class).isNotEmpty(collection))
			for(Component index : collection)
				if(index instanceof InputOutput<?>)
					((InputOutput<?>)index).setValueFromFieldValue();
		return this;
	}

	@Override
	public Components setInputOutputFieldValueFromValue() {
		Collection<Component> collection = get();
		if(__inject__(CollectionHelper.class).isNotEmpty(collection))
			for(Component index : collection)
				if(index instanceof InputOutput<?>)
					((InputOutput<?>)index).setFieldValueFromValue();
		return this;
	}

}
