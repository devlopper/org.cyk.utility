package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.userinterface.CascadeStyleSheetHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Row extends Dimension implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String MENU_STYLE_CLASS = CascadeStyleSheetHelper.getInstance().getClass(DataTable.class,Row.class,Menu.class);
	
	private Menu menu;
	private MenuNode deleteMenuNode;
	
	/**/
	
	public Row() {
		// TODO Auto-generated constructor stub
	}
	
	public Row _setObject(Object object){
		getPropertiesMap().setValue(object);
		menu = new Menu().setRenderType(Menu.RenderType.BAR);
		menu.getPropertiesMap().addString(Properties.STYLE_CLASS, Constant.CHARACTER_SPACE, MENU_STYLE_CLASS);
		getPropertiesMap().setMainMenu(menu);
		addOneChild(menu);
		menu.addNode("read")._setPropertyUrl(Constant.Action.READ,object)._setLabelPropertyRendered(Boolean.FALSE)._setPropertyTitleFromLabel()
			._setPropertyIcon(IconHelper.Icon.FontAwesome.EYE);
		menu.addNode("update")._setPropertyUrl(Constant.Action.UPDATE,object)._setLabelPropertyRendered(Boolean.FALSE)._setPropertyTitleFromLabel()
			._setPropertyIcon(IconHelper.Icon.FontAwesome.EDIT);
		deleteMenuNode = (MenuNode) menu.addNode("delete")._setPropertyUrl(Constant.Action.DELETE,object)._setLabelPropertyRendered(Boolean.FALSE)._setPropertyTitleFromLabel()
			._setPropertyIcon(IconHelper.Icon.FontAwesome.TRASH);
		
		return this;
	}
	
	public Cell getCell(Column column){
		return Cell.getOne(column, this,(Cell.Listener) getPropertiesMap().getCellListener());
	}
	
	public Cell getCell(String columnFieldName){
		DataTable dataTable = (DataTable) getPropertiesMap().getParent();
		return getCell(dataTable.getColumn(columnFieldName));
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	public static Row instanciateOne(Object object,Long orderNumber,Columns columns,Boolean sortable){
		Row row = new Row()._setObject(object);
		row.set__orderNumber__(orderNumber);
		
		for(Column column : (Collection<Column>)columns.getPropertiesMap().getValue()){
			if(object!=null)
				column.computeCellValueType(object.getClass(),sortable);
		}
			
		return row;
	}
	
	//@SuppressWarnings("unchecked")
	public static CollectionHelper.Instance<Row> instanciateMany(Collection<?> collection,Component component,CollectionHelper.Instance<Row> rows){
		//Columns columns = (Columns) component.getPropertiesMap().getColumns();
		
		if(CollectionHelper.getInstance().isNotEmpty(collection)){
			if(rows == null)
				rows = new CollectionHelper.Instance<>();
			for(Object object : collection){
				Row row = instanciateOne(object,NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows.getElements())+1,0l)
						,(Columns) component.getPropertiesMap().getColumns(),component instanceof DataTable);
				row.getPropertiesMap().setParent(component);
				
				rows.addOne(row);
				component.addOneChild(row);
				
				/*Row row = new Row()._setObject(object);
				row.getPropertiesMap().setParent(component);
				rows.addOne(row);
				row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows.getElements()),0l));
				component.addOneChild(row);
				
				for(Column column : (Collection<Column>)columns.getPropertiesMap().getValue())
					column.computeCellValueType(object);
				*/
			}
		}
		
		return rows;
	}
}