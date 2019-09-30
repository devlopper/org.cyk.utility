package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.Row;

public abstract class AbstractWindowContainerManagedWindowBuilderListImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderList,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strings gridColumnsFieldNames;
	private Collection<?> gridObjects;
		
	@Override
	protected Class<? extends Row> __getRowClass__(Class<? extends Row> aClass) {
		SystemAction systemAction = getSystemAction();
		if(systemAction!=null)
			aClass = __inject__(DataHelper.class).getRowClass(systemAction);
		return aClass;
	}
	
	@Override
	public Strings getGridColumnsFieldNames() {
		return gridColumnsFieldNames;
	}
	
	@Override
	public Strings getGridColumnsFieldNames(Boolean injectIfNull) {
		if(gridColumnsFieldNames == null && Boolean.TRUE.equals(injectIfNull))
			gridColumnsFieldNames = __inject__(Strings.class);
		return gridColumnsFieldNames;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderList setGridColumnsFieldNames(Strings gridColumnsFieldNames) {
		this.gridColumnsFieldNames = gridColumnsFieldNames;
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderList addGridColumnsFieldNames(Collection<String> gridColumnsFieldNames) {
		getGridColumnsFieldNames(Boolean.TRUE).add(gridColumnsFieldNames);
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderList addGridColumnsFieldNames(String... gridColumnsFieldNames) {
		addGridColumnsFieldNames(CollectionHelper.listOf(gridColumnsFieldNames));
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderList addGridColumnsFieldNamesWithPrefix(String prefix,Collection<String> gridColumnsFieldNames) {
		getGridColumnsFieldNames(Boolean.TRUE).addWithPrefix(StringHelper.addToEndIfDoesNotEndWith(prefix, ConstantCharacter.DOT), gridColumnsFieldNames);
		return this;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderList addGridColumnsFieldNamesWithPrefix(String prefix,String... gridColumnsFieldNames) {
		return addGridColumnsFieldNamesWithPrefix(prefix,CollectionHelper.listOf(gridColumnsFieldNames));
	}
	
	@Override
	public Collection<?> getGridObjects() {
		return gridObjects;
	}
	
	@Override
	public WindowContainerManagedWindowBuilderList setGridObjects(Collection<?> gridObjects) {
		this.gridObjects = gridObjects;
		return this;
	}
	
	public static final String FIELD_GRID_COLUMNS_FIELD_NAMES = "gridColumnsFieldNames";
	
}
