package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueSource;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueType;
import org.cyk.utility.common.userinterface.command.Menu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
		Menu menu = new Menu().setRenderType(Menu.RenderType.BAR);
		getPropertiesMap().setMainMenu(menu);
		addOneChild(menu);
		if(this.actionOnClass!=null){
			menu.addNode("add")._setPropertyUrl(Constant.Action.CREATE, this.actionOnClass);
		}
		addColumn("order.number", FIELD___ORDER_NUMBER__).setCellValueSource(CellValueSource.ROW);
		
		addColumn("action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP).setCellValueType(CellValueType.MENU).set__orderNumber__(Long.MAX_VALUE);
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
	
	public Column addColumn(String labelStringIdentifier,String fieldName){
		if(getPropertiesMap().getColumns()==null)
			getPropertiesMap().setColumns(new Columns());
		return ((Columns)getPropertiesMap().getColumns()).addColumn(labelStringIdentifier, fieldName);
	}
	
	public DataTable addManyRow(Collection<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			for(Object object : collection){
				Row row = new Row(this,object);
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
					case ROW_PROPERTIES_MAP : value = row.getPropertiesMap().get(fieldName);break;
					case ROW_PROPERTY_VALUE:value = FieldHelper.getInstance().read(row.getPropertiesMap().getValue(), fieldName);break;
					}	
			}
			cell.getPropertiesMap().setValue(value);
			return cell;
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
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Row extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
				
		/**/
		
		public Row(DataTable dataTable,Object object) {
			getPropertiesMap().setValue(object);
			Menu menu = new Menu().setRenderType(Menu.RenderType.BAR);
			getPropertiesMap().setMainMenu(menu);
			addOneChild(menu);
			menu.addNode("read")._setPropertyUrl(Constant.Action.READ,object);
			menu.addNode("update")._setPropertyUrl(Constant.Action.UPDATE,object);
			menu.addNode("delete")._setPropertyUrl(Constant.Action.DELETE,object);
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Cell extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}

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
	
	public static class ClassLocator extends org.cyk.utility.common.ClassLocator implements Serializable {

		private static final long serialVersionUID = -3187769614985951029L;

		private String action;
		
		public ClassLocator(String action) {
			this.action = action;
			setClassType(this.action+"DataTable");
			Listener listener = new Listener.Adapter(){
				private static final long serialVersionUID = -979036256355287919L;

				@Override
				public Boolean isLocatable(Class<?> basedClass) {
					return Boolean.TRUE;
				}
			};
			
			listener.setGetNameMethod(new GetOrgCykSystem(this));
			getClassLocatorListeners().add(listener);
		}
		
		public ClassLocator(Constant.Action action) {
			this(StringHelper.getInstance().applyCaseType(action.name(), StringHelper.CaseType.FURL));
		}
		
		@Override
		protected Class<?> getDefault(Class<?> aClass) {
			return DataTable.class;
		}
		
		/**/
		
		public static class GetOrgCykSystem extends Listener.AbstractGetOrgCykSystem {
			private static final long serialVersionUID = 1L;

			private ClassLocator classLocator;
			
			public GetOrgCykSystem(ClassLocator classLocator) {
				this.classLocator = classLocator;
			}
			
			@Override
			protected String getBaseClassPackageName() {
				return "model";
			}
			
			@Override
			protected String[] getSystemIdentifiers(Class<?> aClass) {
				return ArrayUtils.addAll(super.getSystemIdentifiers(aClass), "") ;
			}
			
			@Override
			protected String[] getModulePrefixes() {
				return new String[]{"ui.web.primefaces.page"};
			}
			
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{"DataTable",classLocator.action+"Window$DataTable"};
			}
			
			@Override
			protected String[] __execute__(Class<?> aClass) {
				String[] names =  super.__execute__(aClass);
				for(int index = 0 ; index < names.length ; index++)
					if(StringUtils.contains(names[index], "org.cyk.system.ui."))
						names[index] = StringUtils.replace(names[index],"org.cyk.system.ui.", "org.cyk.ui.");
				return names;
			}
		}
		
	}
}
