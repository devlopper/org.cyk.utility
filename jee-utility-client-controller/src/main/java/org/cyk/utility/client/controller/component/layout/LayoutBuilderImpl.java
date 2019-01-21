package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.ComponentRoles;
import org.cyk.utility.function.FunctionsExecutor;

public class LayoutBuilderImpl extends AbstractVisibleComponentBuilderImpl<Layout> implements LayoutBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItemBuilders items;
	private LayoutType type;
	private LayoutGridRowModel gridRowModel;
	
	@Override
	protected void __execute__(Layout layout) {
		super.__execute__(layout);
		ComponentRoles roles = getRoles();
		LayoutItemBuilders items = getItems();
		LayoutType type = getType();
		layout.setType(type);
		if(items==null) {
			if(layout.getType() instanceof LayoutTypeGrid) {
				if(__injectCollectionHelper__().isEmpty(roles))
					layout.getRoles(Boolean.TRUE).add(ComponentRole.GRID);
				LayoutTypeGrid table = (LayoutTypeGrid) layout.getType();
				items = __inject__(LayoutItemBuilders.class);
				if(Boolean.TRUE.equals(table.getIsHasHeader()))
					items.add(__inject__(LayoutItemBuilder.class));
				Integer orderNumberColumnWidth = 1;
				Integer commandablesColumnWidth = 1;
				Boolean isHasOrderNumberColumn = table.getIsHasOrderNumberColumn();
				Boolean isHasCommandablesColumn = table.getIsHasCommandablesColumn();
				Integer width = __inject__(LayoutWidthGetter.class).execute().getOutput().intValue();
				if(Boolean.TRUE.equals(isHasOrderNumberColumn))
					width = width - orderNumberColumnWidth;
				if(Boolean.TRUE.equals(isHasCommandablesColumn))
					width = width - commandablesColumnWidth;
				Integer columnWidth = width / table.getColumnCount();
				Integer remainder = width % table.getColumnCount();
				for(Integer index = 0 ; index < table.getRowCount() ; index++) {
					Collection<ComponentRole> rowRoles = __injectCollectionHelper__().instanciate(ComponentRole.ROW,index % 2 == 0 ? ComponentRole.EVEN : ComponentRole.ODD);
					
					if(Boolean.TRUE.equals(table.getIsHasOrderNumberColumn())) {
						items.add(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(orderNumberColumnWidth).addRoles(rowRoles));
					}
					
					for(Integer indexColumn = 0 ; indexColumn < table.getColumnCount() ; indexColumn++) {
						items.add(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(columnWidth + ( indexColumn == table.getColumnCount()-1 ? remainder :0 ))
								.addRoles(rowRoles));
					}
					
					if(Boolean.TRUE.equals(table.getIsHasCommandablesColumn())) {
						items.add(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(commandablesColumnWidth).addRoles(rowRoles));
					}
				}
				if(Boolean.TRUE.equals(table.getIsHasFooter()))
					items.add(__inject__(LayoutItemBuilder.class));
			}
		}
				
		if(__injectCollectionHelper__().isNotEmpty(items)) {
			FunctionsExecutor functionsExecutor = __inject__(FunctionsExecutor.class);
			for(LayoutItemBuilder index : items.get()) 
					functionsExecutor.addFunctions(index);
			functionsExecutor.execute();
			for(LayoutItemBuilder index : items.get()) {
				LayoutItem layoutItem = index.getComponent();
				if(layoutItem!=null)
					layout.addChild(index.getComponent());			
			}
		}
	}
	
	@Override
	public LayoutItemBuilders getItems() {
		return items;
	}

	@Override
	public LayoutItemBuilders getItems(Boolean injectIfNull) {
		return (LayoutItemBuilders) __getInjectIfNull__(FIELD_ITEMS, injectIfNull);
	}

	@Override
	public LayoutBuilder setItems(LayoutItemBuilders items) {
		this.items = items;
		return this;
	}

	@Override
	public LayoutBuilder addItems(Collection<LayoutItemBuilder> items) {
		if(__injectCollectionHelper__().isNotEmpty(items))
			getItems(Boolean.TRUE).add(items);
		return this;
	}
	
	@Override
	public LayoutBuilder addItems(LayoutItemBuilder... items) {
		return addItems(__injectCollectionHelper__().instanciate(items));
	}

	@Override
	public LayoutBuilder setType(LayoutType type) {
		this.type = type;
		return this;
	}
	
	@Override
	public LayoutType getType() {
		return type;
	}
	
	@Override
	public LayoutGridRowModel getGridRowModel() {
		return gridRowModel;
	}
	
	@Override
	public LayoutGridRowModel getGridRowModel(Boolean injectIfNull) {
		return (LayoutGridRowModel) __getInjectIfNull__(FIELD_GRID_ROW_MODEL, injectIfNull);
	}
	
	@Override
	public LayoutBuilder setGridRowModel(LayoutGridRowModel gridRowModel) {
		this.gridRowModel = gridRowModel;
		return this;
	}
	
	/**/
	
	public static final String FIELD_ITEMS = "items";
	public static final String FIELD_GRID_ROW_MODEL = "gridRowModel";
}
