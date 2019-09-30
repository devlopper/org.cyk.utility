package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

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
		if(CollectionHelper.isNotEmpty(collection))
			for(Component index : collection)
				if(index instanceof InputOutput<?>)
					((InputOutput<?>)index).setValueFromFieldValue();
		return this;
	}

	@Override
	public Components setFieldValueFromValue(Class<?>...classes) {
		Collection<Component> collection = get();
		if(CollectionHelper.isNotEmpty(collection))
			for(Component index : collection)
				if(Boolean.TRUE.equals(ClassHelper.isInstanceOfOne(index.getClass(), classes))) {
					if(index instanceof InputOutput)
						((InputOutput<?>)index).setFieldValueFromValue();
				}
		return this;
	}
	
	@Override
	public Collection<Commandable> getCommandables(Boolean isRecursive) {
		Collection<Commandable> commandables = null;
		if(CollectionHelper.isNotEmpty(collection)) {
			if(Boolean.TRUE.equals(isRecursive)) {
				if(commandables == null)
					commandables = new ArrayList<Commandable>();
				__addCommandablesRecursively__(collection, commandables);
			}else {
				commandables = getIsInstanceOfOne(Commandable.class);
			}	
		}
		return commandables;
	}
	
	private void __addCommandablesRecursively__(Collection<Component> components,Collection<Commandable> commandables) {
		for(Component index : components) {
			if(index instanceof Commandable)
				commandables.add((Commandable) index);
			else if(index instanceof View)
				__addCommandablesRecursively__(((View)index).getComponents().get(), commandables);
			else if(index instanceof Grid) {
				for(String indexViewKey : new String[] {ViewMap.HEADER,ViewMap.BODY,ViewMap.FOOTER}) {
					View view = ((Grid)index).getView(indexViewKey);
					if(view!=null)
						__addCommandablesRecursively__(view.getComponents().get(), commandables);
				}
				
			}
		}
	}
	
	@Override
	public Collection<Commandable> getCommandables() {
		return getCommandables(Boolean.FALSE);
	}
	
	@Override
	public Commandable getCommandableByIdentifier(Object identifier, Boolean isRecursive) {
		Collection<Commandable> commandables = getCommandables(isRecursive);
		if(CollectionHelper.isNotEmpty(commandables)) {
			for(Commandable index : commandables) {
				if(index.getIdentifier().equals(identifier))
					return index;
			}
		}
		return null;
	}
	
	@Override
	public Commandable getCommandableByIdentifier(Object identifier) {
		return getCommandableByIdentifier(identifier, Boolean.FALSE);
	}

}
