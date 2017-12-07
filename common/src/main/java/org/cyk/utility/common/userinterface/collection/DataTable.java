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
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueSource;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputFile;
import org.cyk.utility.common.userinterface.output.OutputText;

@Getter @Setter @Accessors(chain=true)
public class DataTable extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private Class<?> actionOnClass;
	//private CollectionHelper.Instance<Row> rows = new CollectionHelper.Instance<Row>();
	
	private Boolean onPrepareAddMenu;
	private Boolean onPrepareAddColumnOrderNumber = Boolean.TRUE;
	private Boolean onPrepareAddColumnAction;
	
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
	
		if(Boolean.TRUE.equals(onPrepareAddMenu)){
			Menu menu = new Menu().setRenderType(Menu.RenderType.BAR);
			getPropertiesMap().setMainMenu(menu);
			addOneChild(menu);
			if(this.actionOnClass!=null){
				menu.addNode("add")._setPropertyUrl(Constant.Action.CREATE, this.actionOnClass)
					._setPropertyIcon(IconHelper.Icon.FontAwesome.PLUS);
			}	
		}
		if(Boolean.TRUE.equals(onPrepareAddColumnOrderNumber)){
			addColumn("order.number", FIELD___ORDER_NUMBER__).setCellValueSource(CellValueSource.ROW).set__orderNumber__(Long.MIN_VALUE);	
		}
		__prepare__();
		if(Boolean.TRUE.equals(onPrepareAddColumnAction)){
			addColumn("action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP).setCellValueType(Cell.ValueType.MENU).set__orderNumber__(Long.MAX_VALUE);	
		}
		if(Boolean.TRUE.equals(onPrepareCallLoad)){
			load(); //can be trigger by callback to enabled fast rendering of table structure	
		}
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
		if(CollectionHelper.getInstance().isNotEmpty(collection)){
			@SuppressWarnings("unchecked")
			Collection<Row> rows = (Collection<Row>) getPropertiesMap().getValue();
			rows = Row.instanciateMany(collection,this, rows);
			//if(rows == null)
			//	rows = new ArrayList<>();
			/*for(Object object : collection){
				Row row = new Row()._setObject(object);
				//row.getPropertiesMap().setValue(object);
				//rows.addOne(row);
				rows.add(row);
				//row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows.getElements()),0l));
				row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows),0l));
				addOneChild(row);
			}
			*/
			if(getPropertiesMap().getValue()==null && CollectionHelper.getInstance().isNotEmpty(rows))
				getPropertiesMap().setValue(rows);
		}
		return this;
	}
	
	public DataTable addManyRow(CollectionHelper.Instance<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			addManyRow(collection.getElements());
		return this;
	}
	
	@Override
	public Component load() {
		super.load();
		if(actionOnClass!=null)
			addManyRow(InstanceHelper.getInstance().get(actionOnClass));
		return this;
	}
	
	public Column getColumn(String fieldName){
		Columns columns = (Columns)getPropertiesMap().getColumns();
		if(columns!=null)
			return columns.getColumn(fieldName);
		return null;
	}
	
	/**/
	
	/**/
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class Column extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;

		public static enum CellValueSource {ROW_PROPERTIES_MAP,ROW_PROPERTY_VALUE,ROW; public static CellValueSource DEFAULT = CellValueSource.ROW_PROPERTY_VALUE;}
		//public static enum CellValueType {TEXT,MENU,FILE,IMAGE; public static CellValueType DEFAULT = CellValueType.TEXT;}

		//private Collection<Listener> listeners = new ArrayList<>();
		private CellValueSource cellValueSource = CellValueSource.DEFAULT;
		private Cell.ValueType cellValueType;
		
		/**/
		
		public Cell getCell(Row row){
			Cell cell = new Cell();
			Output output = null;
			/*if(row==null){
				value = "ROW IS NULL";
			}*/
			if(row!=null){
				String fieldName = (String) getPropertiesMap().getFieldName();
				if(StringHelper.getInstance().isBlank(fieldName)){
					output = new OutputText();
					output.getPropertiesMap().setValue("NO FIELD NAME");
				}else {
					if(cellValueSource==null)
						;
					else
						switch(cellValueSource){
						case ROW : 
							output = new Output(); 
							output.getPropertiesMap().setValue(FieldHelper.getInstance().read(row, fieldName)); 
							break;
						case ROW_PROPERTIES_MAP : 
							output = new Output(); 
							output.getPropertiesMap().setValue(row.getPropertiesMap().get(fieldName)); 
							break;
						case ROW_PROPERTY_VALUE:
							Object object = FieldHelper.getInstance().readBeforeLast(row.getPropertiesMap().getValue(), fieldName);
							output = Output.getListener().get(null,object, FieldHelper.getInstance().getLast(object.getClass(),fieldName)); 
							break;
						}	
				}	
			}
			if(output instanceof OutputFile){
				Image thumbnail = (Image) ((OutputFile) output).getPropertiesMap().getThumbnail();
				if(ContentType.HTML.equals(Component.RENDER_AS_CONTENT_TYPE)){
					thumbnail.getPropertiesMap().setWidth("20px").setHeight("20px");
				}
			}
			computeCellValueType(row.getPropertiesMap().getValue());
			cell.getPropertiesMap().setValue(output);
			return cell;
		}
		
		public Column computeCellValueType(Object object){
			if(object!=null && cellValueType==null){
				String fieldName = (String) getPropertiesMap().getFieldName();
				Class<?> fieldType = null;
				if(CellValueSource.ROW.equals(cellValueSource))
					fieldType = FieldHelper.getInstance().get(Row.class, fieldName).getType();
				else if(CellValueSource.ROW_PROPERTIES_MAP.equals(cellValueSource))
					fieldType = Object.class;
				else
					fieldType = FieldHelper.getInstance().get(object.getClass(), fieldName).getType();
				if(FileHelper.getListener().getModelClass().equals(fieldType))
					cellValueType = Cell.ValueType.FILE;
				else
					cellValueType = Cell.ValueType.DEFAULT;
				getPropertiesMap().setLinked(ClassHelper.getInstance().isIdentified(fieldType));
			}
			return this;
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
		
		/**/
		
		public static interface Listener {
			
			public static class Adapter extends AbstractBean implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
				
				}				
			}
		}
	}

	public static class Columns extends Component implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/
		
		public Column addColumn(String labelStringIdentifier,String fieldName){
			DataTable.Column column = Column.instanciateOne(labelStringIdentifier, fieldName);
			//column.setLabelFromIdentifier(labelStringIdentifier);
			//column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
			//column.getPropertiesMap().setFieldName(fieldName);
			
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
		
		public Column getColumn(String fieldName){
			@SuppressWarnings("unchecked")
			Collection<Column> collection = (Collection<Column>) getPropertiesMap().getValue();
			if(collection!=null)
				for(Column column : collection)
					if(fieldName==null && column.getPropertiesMap().getFieldName()==null || fieldName.equals(column.getPropertiesMap().getFieldName()))
						return column;
			return null;
		}
		
		public static void build(Component component) {
			Columns columns = (Columns) component.getPropertiesMap().getColumns();
			if(columns!=null){
				CollectionHelper.getInstance().sort((Collection<?>) columns.getPropertiesMap().getValue());
			}
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

	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Row extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
				
		/**/
		
		public Row() {
			// TODO Auto-generated constructor stub
		}
		
		public Row _setObject(Object object){
			getPropertiesMap().setValue(object);
			Menu menu = new Menu().setRenderType(Menu.RenderType.BAR);
			getPropertiesMap().setMainMenu(menu);
			addOneChild(menu);
			menu.addNode("read")._setPropertyUrl(Constant.Action.READ,object)._setLabelPropertyRendered(Boolean.FALSE)._setPropertyTitleFromLabel()
				._setPropertyIcon(IconHelper.Icon.FontAwesome.EYE);
			menu.addNode("update")._setPropertyUrl(Constant.Action.UPDATE,object)._setLabelPropertyRendered(Boolean.FALSE)._setPropertyTitleFromLabel()
				._setPropertyIcon(IconHelper.Icon.FontAwesome.PENCIL);
			menu.addNode("delete")._setPropertyUrl(Constant.Action.DELETE,object)._setLabelPropertyRendered(Boolean.FALSE)._setPropertyTitleFromLabel()
				._setPropertyIcon(IconHelper.Icon.FontAwesome.TRASH);
			return this;
		}
		
		/**/
		
		@SuppressWarnings("unchecked")
		public static Collection<Row> instanciateMany(Collection<?> collection,Component component,Collection<Row> rows){
			//Collection<Row> rows = new ArrayList<>();
			/*if(CollectionHelper.getInstance().isNotEmpty(collection))
				for(Object object : collection){
					Row row = new Row()._setObject(object);
					rows.add(row);
					row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows),0l));
				}
			*/
			Columns columns = (Columns) component.getPropertiesMap().getColumns();
			
			if(CollectionHelper.getInstance().isNotEmpty(collection)){
				if(rows == null)
					rows = new ArrayList<>();
				for(Object object : collection){
					Row row = new Row()._setObject(object);
					rows.add(row);
					row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows),0l));
					component.addOneChild(row);
					
					for(Column column : (Collection<Column>)columns.getPropertiesMap().getValue())
						column.computeCellValueType(object);
				}
			}
			
			return rows;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Cell extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static enum ValueType {TEXT,LINK,MENU,FILE,IMAGE; public static ValueType DEFAULT = ValueType.TEXT;}
		
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
