package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueSource;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueType;
import org.cyk.utility.common.userinterface.command.Menu;

@Getter @Setter @Accessors(chain=true)
public class DataTable extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private Class<?> actionOnClass;
	private CollectionHelper.Instance<Row> rows = new CollectionHelper.Instance<Row>();
	
	/**/
	
	public DataTable(Class<?> actionOnClass) {
		this.actionOnClass = actionOnClass;
	}
	
	public DataTable() {
		this(null);
	}
	
	@Override
	public DataTable build() {
		Columns columns = (Columns) getPropertiesMap().getColumns();
		if(columns!=null){
			CollectionHelper.getInstance().sort((Collection<?>) columns.getPropertiesMap().getValue());
		}
		return (DataTable) super.build();
	}
	
	@Override
	public DataTable prepare() {
		super.prepare();
		Menu menu = new Menu().setRenderType(Menu.RenderType.BAR);
		getPropertiesMap().setMainMenu(menu);
		addOneChild(menu);
		if(this.actionOnClass!=null){
			menu.addNode("add")._setPropertyUrl(Constant.Action.CREATE, this.actionOnClass);
		}
		addColumn("order.number", FIELD___ORDER_NUMBER__).setCellValueSource(CellValueSource.ROW);
		__prepare__();
		addColumn("action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP).setCellValueType(CellValueType.MENU).set__orderNumber__(Long.MAX_VALUE);
		
		loadRows();//can be trigger by callback to enabled fast rendering of table structure
		
		return this;
	}
	
	protected void __prepare__(){}
	
	public Column addColumn(String labelStringIdentifier,String fieldName){
		if(getPropertiesMap().getColumns()==null)
			getPropertiesMap().setColumns(new Columns());
		return ((Columns)getPropertiesMap().getColumns()).addColumn(labelStringIdentifier, fieldName);
	}
	
	public Column addColumnByFieldName(String fieldName){
		String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
		return addColumn(labelStringIdentifier, fieldName);
	}
	
	public DataTable addColumnsByFieldNames(String...fieldNames){
		for(String fieldName : fieldNames)
			addColumnByFieldName(fieldName);
		return this;
	}
	
	public DataTable addManyRow(Collection<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			for(Object object : collection){
				Row row = new Row()._setObject(object);
				//row.getPropertiesMap().setValue(object);
				rows.addOne(row);
				row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows.getElements()),0l));
				addOneChild(row);
			}
		if(getPropertiesMap().getValue()==null && CollectionHelper.getInstance().isNotEmpty(rows))
			getPropertiesMap().setValue(rows.getElements());
		return this;
	}
	
	public DataTable addManyRow(CollectionHelper.Instance<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			addManyRow(collection.getElements());
		return this;
	}
	
	public void loadRows(){
		if(actionOnClass!=null)
			addManyRow(InstanceHelper.getInstance().get(actionOnClass));
	}
	
	/**/
	
	/**/
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class Column extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;

		public static enum CellValueSource {ROW_PROPERTIES_MAP,ROW_PROPERTY_VALUE,ROW; public static CellValueSource DEFAULT = CellValueSource.ROW_PROPERTY_VALUE;}
		public static enum CellValueType {TEXT,MENU,FILE,IMAGE; public static CellValueType DEFAULT = CellValueType.TEXT;}
		
		private CellValueSource cellValueSource = CellValueSource.DEFAULT;
		private CellValueType cellValueType = CellValueType.DEFAULT;
		
		/**/
		
		public Cell getCell(Row row){
			Cell cell = new Cell();
			Object value = null;
			if(row==null){
				value = "ROW IS NULL";
			}
			if(row!=null){
				String fieldName = (String) getPropertiesMap().getFieldName();
				if(StringHelper.getInstance().isBlank(fieldName))
					value = "NO FIELD NAME";
				else {
					//CellValueSource cellValueSource = InstanceHelper.getInstance().getIfNotNullElseDefault(this.cellValueSource, CellValueSource.DEFAULT);
					if(cellValueSource==null)
						;
					else
						switch(cellValueSource){
						case ROW : value = FieldHelper.getInstance().read(row, fieldName);break;
						case ROW_PROPERTIES_MAP : value = row.getPropertiesMap().get(fieldName); break;
						case ROW_PROPERTY_VALUE:value = FieldHelper.getInstance().read(row.getPropertiesMap().getValue(), fieldName);break;
						}	
				}	
			}
			cell.getPropertiesMap().setValue(value);
			return cell;
		}
		
		/**/
		
		public static Column instanciateOne(String labelStringIdentifier,String fieldName,Column.CellValueSource cellValueSource){
			DataTable.Column column = new DataTable.Column();
			column.setCellValueSource(cellValueSource);
			column.setLabelFromIdentifier(labelStringIdentifier);
			column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
			column.getPropertiesMap().setFieldName(fieldName);
			return column;
		}
		
		public static Column instanciateOne(String labelStringIdentifier,String fieldName){
			return instanciateOne(labelStringIdentifier, fieldName, CellValueSource.DEFAULT);
		}
		
	}

	public static class Columns extends Component implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/
		
		public Column addColumn(String labelStringIdentifier,String fieldName){
			DataTable.Column column = new DataTable.Column();
			column.setLabelFromIdentifier(labelStringIdentifier);
			column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
			column.getPropertiesMap().setFieldName(fieldName);
			addOneChild(column);
			
			if(getPropertiesMap().getValue()==null)
				getPropertiesMap().setValue(getChildren().getElements());
			return column;
		}
		
		/**/
		
		public static Column add(Component component,String labelStringIdentifier,String fieldName,CellValueSource cellValueSource){
			DataTable.Column column = Column.instanciateOne(labelStringIdentifier, fieldName, cellValueSource);
			
			Columns columns;
			if( (columns = (Columns) component.getPropertiesMap().getColumns()) == null){
				columns = new Columns();
				component.getPropertiesMap().setColumns(columns);
				
			}
				
			columns.addOneChild(column);
			
			if( columns.getPropertiesMap().getValue() == null){
				columns.getPropertiesMap().setValue(columns.getChildren().getElements());
			}
			
			return column;
		}
		
		public static Column add(Component component,String labelStringIdentifier,String fieldName){
			return add(component, labelStringIdentifier, fieldName, CellValueSource.DEFAULT);
		}
		
		public static Column addByFieldName(Component component,String fieldName,CellValueSource cellValueSource){
			String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
			return add(component,labelStringIdentifier, fieldName,cellValueSource);
		}
		
		public static Column addByFieldName(Component component,String fieldName){
			return addByFieldName(component, fieldName, CellValueSource.DEFAULT);
		}
		
		public static void addByFieldNames(Component component,CellValueSource cellValueSource,String...fieldNames){
			for(String fieldName : fieldNames)
				addByFieldName(component,fieldName,cellValueSource);
		}
		
		public static void addByFieldNames(Component component,String...fieldNames){
			addByFieldNames(component, CellValueSource.DEFAULT, fieldNames);
		}
		
		public static void build(Component component) {
			Columns columns = (Columns) component.getPropertiesMap().getColumns();
			if(columns!=null){
				CollectionHelper.getInstance().sort((Collection<?>) columns.getPropertiesMap().getValue());
			}
		}

	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Row extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
				
		/**/
		
		public Row _setObject(Object object){
			getPropertiesMap().setValue(object);
			Menu menu = new Menu().setRenderType(Menu.RenderType.BAR);
			getPropertiesMap().setMainMenu(menu);
			addOneChild(menu);
			menu.addNode("read")._setPropertyUrl(Constant.Action.READ,object);
			menu.addNode("update")._setPropertyUrl(Constant.Action.UPDATE,object);
			menu.addNode("delete")._setPropertyUrl(Constant.Action.DELETE,object);
			return this;
		}
		
		/**/
		
		public static Collection<Row> instanciateMany(Collection<?> collection){
			Collection<Row> rows = new ArrayList<>();
			if(CollectionHelper.getInstance().isNotEmpty(collection))
				for(Object object : collection){
					Row row = new Row()._setObject(object);
					rows.add(row);
					row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows),0l));
				}
			/*
			if(getPropertiesMap().getValue()==null && CollectionHelper.getInstance().isNotEmpty(rows))
				getPropertiesMap().setValue(rows.getElements());
			*/
			return rows;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Cell extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}

	/**/
	
	/**/
	
	public static interface Listener {
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			
			}
			
		}
		
	}
	
	/**/
	
	public static class ClassLocator extends Component.ClassLocator implements Serializable {

		private static final long serialVersionUID = -3187769614985951029L;

		public ClassLocator(String action) {
			super(DataTable.class,action);
		}
		
		public ClassLocator(Constant.Action action) {
			super(DataTable.class,action);
		}
		
	}
}
