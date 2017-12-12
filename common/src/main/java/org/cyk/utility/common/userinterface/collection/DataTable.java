package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

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
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.Watermark;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputFile;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class DataTable extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	//private Class<?> actionOnClass;
	//private CollectionHelper.Instance<Row> rows = new CollectionHelper.Instance<Row>();
	
	private Boolean onPrepareAddMenu;
	private Boolean onPrepareAddColumnOrderNumber = Boolean.TRUE;
	private Boolean onPrepareAddColumnAction;
	
	/**/
	
	/*public DataTable(Class<?> actionOnClass) {
		this.actionOnClass = actionOnClass;
	}
	
	public DataTable() {
		this(null);
	}*/
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		InputText inputText = new InputText();
		Watermark watermark = new Watermark();
		watermark.getPropertiesMap().setValue("global search...");
		inputText.getPropertiesMap().setWatermark(watermark);
		propertiesMap.setFilterInputComponent(inputText);
		
		Command command = new Command();
		command.setLabelFromIdentifier("search")._setLabelPropertyRendered(Boolean.FALSE);
		command.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.SEARCH);
		propertiesMap.setFilterCommandComponent(command);
		
		super.listenPropertiesInstanciated(propertiesMap);
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
			if(getPropertiesMap().getActionOnClass()!=null){
				menu.addNode("add")._setPropertyUrl(Constant.Action.CREATE, getPropertiesMap().getActionOnClass())
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
		
		if(getPropertiesMap().getAction() instanceof Constant.Action)
			getPropertiesMap().setFilterable(!Constant.Action.isCreateOrUpdateOrDelete((Constant.Action)getPropertiesMap().getAction()));
		((Component)getPropertiesMap().getFilterInputComponent()).getPropertiesMap().setRendered(getPropertiesMap().getFilterable());
		((Component)getPropertiesMap().getFilterCommandComponent()).getPropertiesMap().setRendered(getPropertiesMap().getFilterable());
		
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
		if(getPropertiesMap().getActionOnClass()!=null)
			addManyRow(InstanceHelper.getInstance().get((Class<?>) getPropertiesMap().getActionOnClass()));
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
	public static class Column extends Dimension implements Serializable {
		private static final long serialVersionUID = 1L;

		public static enum CellValueSource {ROW_PROPERTIES_MAP,ROW_PROPERTY_VALUE,ROW; public static CellValueSource DEFAULT = CellValueSource.ROW_PROPERTY_VALUE;}
		//public static enum CellValueType {TEXT,MENU,FILE,IMAGE; public static CellValueType DEFAULT = CellValueType.TEXT;}

		//private Collection<Listener> listeners = new ArrayList<>();
		private CellValueSource cellValueSource = CellValueSource.DEFAULT;
		private Cell.ValueType cellValueType;
		
		/**/
		
		public Cell getCell(Row row){
			if(row==null)
				return null;
			return Cell.getOne(this, row);
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
				
				if(Cell.ValueType.TEXT.equals(cellValueType)){
					if(CellValueSource.ROW_PROPERTY_VALUE.equals(cellValueSource))
						getPropertiesMap().setSortable(Boolean.TRUE);
				}
			}
			return this;
		}

		@SuppressWarnings("unchecked")
		public int sort(Object o1,Object o2){
			return ((Comparable<Object>)o1).compareTo(o2);
		}
		
		public boolean filter(Object value,Object filter,Locale locale){
			return ((String)value).startsWith((String) filter);
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
		
		@Override
		public Component addOneChild(Component component) {
			if(component instanceof Column){
				if(component.getPropertiesMap().getSortable()==null && FieldHelper.getInstance().getIsContainSeparator((String)component.getPropertiesMap().getFieldName())){
					//component.getPropertiesMap().setSortable(Boolean.TRUE);
					//component.getPropertiesMap().setFilterable(Boolean.TRUE);
				}
			}
			return super.addOneChild(component);
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
	public static class Row extends Dimension implements Serializable {
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
		
		public Cell getCell(Column column){
			return Cell.getOne(column, this);
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
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper=false,of={Cell.FIELD_COLUMN,Cell.FIELD_ROW})
	public static class Cell extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static enum ValueType {TEXT,LINK,MENU,FILE,IMAGE; public static ValueType DEFAULT = ValueType.TEXT;}
		
		private Column column;
		private Row row;
		
		/**/
		
		public static Cell instanciateOne(Column column,Row row){
			Cell cell = new Cell().setColumn(column).setRow(row);
			Output output = null;
			/*if(row==null){
				value = "ROW IS NULL";
			}*/
			if(row!=null){
				String fieldName = (String) column.getPropertiesMap().getFieldName();
				if(StringHelper.getInstance().isBlank(fieldName)){
					output = new OutputText();
					output.getPropertiesMap().setValue("NO FIELD NAME");
				}else {
					if(column.cellValueSource==null)
						;
					else
						switch(column.cellValueSource){
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
			column.computeCellValueType(row.getPropertiesMap().getValue());
			cell.getPropertiesMap().setValue(output);
			if(output instanceof OutputFile){
				
			}else{
				cell.getPropertiesMap().setSortBy(output.getPropertiesMap().getValue());
				cell.getPropertiesMap().setFilterBy(output.getPropertiesMap().getValue());
			}
			
			return cell;
		}
		
		public static Cell getOne(Column column,Row row){
			Cell cell = null;
			if(CollectionHelper.getInstance().isNotEmpty(column.cells))
				for(Cell index : column.cells)
					if(index.column.equals(column) && index.row.equals(row)) {
						cell = index;
						break;
					}
			if(cell==null){
				column.addOneCell(cell = Cell.instanciateOne(column, row));
				row.addOneCell(cell);
			}
			return cell;
		}
		
		/**/
		
		public static final String FIELD_COLUMN = "column";
		public static final String FIELD_ROW = "row";
	}

	/**/
	  
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	private static class Dimension extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Collection<Cell> cells;
		
		public Dimension addOneCell(Cell cell){
			if(cells == null)
				cells = new ArrayList<>();
			cells.add(cell);
			return this;
		}
		
		public Cell getCellByIndex(Integer index){
			System.out.println("DataTable.Dimension.getCellByIndex() : "+index);
			if(index==null)
				return null;
			
			return CollectionHelper.getInstance().getElementAt(cells, index);
		}
		
	}
	
	/**/
	
	public static <T> DataTable instanciateOne(Class<T> actionOnClass,String[] fieldNames,Collection<T> collection,Integer page,Boolean lazy){
		DataTable dataTable = new DataTable();
		
		dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
		dataTable.addColumnsByFieldNames(fieldNames);
		if(page!=null){
			dataTable.getPropertiesMap().setRows(page);
			dataTable.getPropertiesMap().setPaginator(Boolean.TRUE);
		}
		if(lazy!=null){
			dataTable.getPropertiesMap().setLazy(lazy);
		}
		dataTable.getPropertiesMap().setAction(Constant.Action.READ);
		dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
		dataTable.prepare();
		dataTable.build();
		if(collection!=null){
			dataTable.addManyRow(collection);	
		}
		return dataTable;
	}
	
	/**/
	
	public static interface Listener {
		
		void setLazyDataModel(DataTable dataTable);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			
			}
			
			@Override
			public void setLazyDataModel(DataTable dataTable) {
				// TODO Auto-generated method stub
				
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
