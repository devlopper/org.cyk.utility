package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.client.controller.data.RowClassGetter;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderSelectImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderSelect,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strings gridColumnsFieldNames;
	private Collection<?> gridObjects;
		
	@Override
	protected Class<? extends Row> __getRowClass__(Class<? extends Row> aClass) {
		SystemAction systemAction = getSystemAction();
		if(systemAction!=null)
			aClass = __inject__(RowClassGetter.class).setSystemAction(systemAction).execute().getOutput();
		return aClass;
	}
	
	@Override
	public Strings getGridColumnsFieldNames() {
		return gridColumnsFieldNames;
	}
	
	@Override
	public Strings getGridColumnsFieldNames(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_GRID_COLUMNS_FIELD_NAMES, injectIfNull);
	}
	
	@Override
	public WindowContainerManagedWindowBuilderSelect setGridColumnsFieldNames(Strings gridColumnsFieldNames) {
		this.gridColumnsFieldNames = gridColumnsFieldNames;
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNames(Collection<String> gridColumnsFieldNames) {
		getGridColumnsFieldNames(Boolean.TRUE).add(gridColumnsFieldNames);
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNames(String... gridColumnsFieldNames) {
		addGridColumnsFieldNames(__inject__(CollectionHelper.class).instanciate(gridColumnsFieldNames));
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNamesWithPrefix(String prefix,Collection<String> gridColumnsFieldNames) {
		getGridColumnsFieldNames(Boolean.TRUE).addWithPrefix(__injectStringHelper__().addToEndIfDoesNotEndWith(prefix, CharacterConstant.DOT), gridColumnsFieldNames);
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNamesWithPrefix(String prefix,String... gridColumnsFieldNames) {
		return addGridColumnsFieldNamesWithPrefix(prefix,__injectCollectionHelper__().instanciate(gridColumnsFieldNames));
	}
	
	@Override
	public Collection<?> getGridObjects() {
		return gridObjects;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderSelect setGridObjects(Collection<?> gridObjects) {
		this.gridObjects = gridObjects;
		return this;
	}
	
	public static final String FIELD_GRID_COLUMNS_FIELD_NAMES = "gridColumnsFieldNames";
	
}
