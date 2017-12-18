package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.JavaServerFacesHelper;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueSource;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.Watermark;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCombo;
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
	
	private Form.Detail form;
	
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
		addFirstColumns();
		__prepare__();
		addLastColumns();
		if(Boolean.TRUE.equals(onPrepareCallLoad)){
			load(); //can be trigger by callback to enabled fast rendering of table structure	
		}
		
		return this;
	}
	
	protected void addFirstColumns(){
		Class<?> choiceValueClass = (Class<?>)getPropertiesMap().getChoiceValueClass();
		Column orderNumberColumn = null,choiceValueColumn = null;
		if(Boolean.TRUE.equals(onPrepareAddColumnOrderNumber)){
			orderNumberColumn = addColumn("order.number", FIELD___ORDER_NUMBER__);
			orderNumberColumn.setCellValueSource(CellValueSource.ROW).set__orderNumber__(Long.MIN_VALUE);	
		}
		if(choiceValueClass!=null){
			choiceValueColumn = addColumnByFieldName(ClassHelper.getInstance().getVariableName(choiceValueClass));
			if(orderNumberColumn!=null)
				choiceValueColumn.set__orderNumber__(orderNumberColumn.get__orderNumber__()+1);
			choiceValueColumn.setCellValueType(DataTable.Cell.ValueType.TEXT);
		}
	}
	
	protected void addLastColumns(){
		if(Boolean.TRUE.equals(onPrepareAddColumnAction)){
			addColumn("action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP).setCellValueType(Cell.ValueType.MENU).set__orderNumber__(Long.MAX_VALUE);	
		}
	}
	
	protected void __prepare__(){
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		Boolean isCreateOrUpdateAction = Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction());
		if(isCreateOrUpdateAction){
						
			Command command = new Command();
			command.setLabelFromIdentifier("add")._setLabelPropertyRendered(Boolean.FALSE);
			command.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.PLUS);
			getPropertiesMap().setAddCommandComponent(command);
			if(JavaServerFacesHelper.Library.PRIMEFACES.equals(Component.JAVA_SERVER_FACES_LIBRARY))
				command.getPropertiesMap().setUpdate("@(."+propertiesMap.getIdentifierAsStyleClass()+")");
			
			@SuppressWarnings("unchecked")
			Class<AddCommandComponentActionAdapter> actionAdapterClass = InstanceHelper.getInstance().getIfNotNullElseDefault(
					(Class<AddCommandComponentActionAdapter>) getPropertiesMap().getAddCommandComponentActionAdapterClass(),AddCommandComponentActionAdapter.class);
			
			AddCommandComponentActionAdapter actionAdapter = ClassHelper.getInstance().instanciateOne(actionAdapterClass);
			actionAdapter.setDataTable(this);
			command.setAction(actionAdapter);
			
			OutputText outputText = new OutputText();
			getPropertiesMap().setAddTextComponent(outputText);
			
			InputChoiceOneCombo inputChoiceOneCombo = new InputChoiceOneCombo();
			//watermark = new Watermark();
			//watermark.getPropertiesMap().setValue(StringHelper.getInstance().get("search", new Object[]{}));
			//inputChoice.getPropertiesMap().setWatermark(watermark);
			getPropertiesMap().setAddInputComponent(inputChoiceOneCombo);
			if(getPropertiesMap().getChoiceValueClass()!=null){
				Class<?> choiceValueClass = (Class<?>)getPropertiesMap().getChoiceValueClass();	
				outputText.getPropertiesMap().setValue(StringHelper.getInstance().getClazz(choiceValueClass));
				
				if(ClassHelper.getInstance().isIdentified(choiceValueClass)){
					inputChoiceOneCombo.getChoices().addMany(InstanceHelper.getInstance().get(choiceValueClass));
										
				}
			}
			
			((Command)getPropertiesMap().getAddCommandComponent()).getPropertiesMap().setInputValueIsNotRequired(Boolean.TRUE);
			
			//((Component)getPropertiesMap().getFilterInputComponent()).getPropertiesMap().setRendered(Boolean.FALSE);
			//((Component)getPropertiesMap().getFilterCommandComponent()).getPropertiesMap().setRendered(Boolean.FALSE);
			
			getPropertiesMap().setRowsCollectionInstance(new CollectionHelper.Instance<>());
		}else{
			if(actionOnClass!=null){
				
				if(getPropertiesMap().getFilterable()==null && Boolean.TRUE.equals(ClassHelper.getInstance().isFilterable(actionOnClass))){
					if(getPropertiesMap().getAction() instanceof Constant.Action)
						getPropertiesMap().setFilterable(!Constant.Action.isCreateOrUpdateOrDelete((Constant.Action)getPropertiesMap().getAction()));
				}
				
				if(getPropertiesMap().getLazy()==null){
					getPropertiesMap().setLazy(ClassHelper.getInstance().isLazy(actionOnClass));
				}
				
				if(getPropertiesMap().getPaginator()==null){
					getPropertiesMap().setPaginator(ClassHelper.getInstance().isPaginated(actionOnClass));
				}
				
				if(getPropertiesMap().getPaginator()!=null && getPropertiesMap().getRows()==null){
					getPropertiesMap().setRows(ClassHelper.getInstance().getPageSize(actionOnClass));
				}
				
				if(Boolean.TRUE.equals(getPropertiesMap().getLazy())){
					//getPropertiesMap().setValue();
					/*Command command = (Command) getPropertiesMap().getFilterCommandComponent();
					if(JavaServerFacesHelper.Library.PRIMEFACES.equals(Component.JAVA_SERVER_FACES_LIBRARY)){
						command.getPropertiesMap().setType("button");
						command.getPropertiesMap().setOnClick(JavaServerFacesHelper.Primefaces.Script.getInstance().getMethodCallFilter(this));	
					}*/
				}
			}
			//InputChoiceOneCombo inputChoiceOneCombo = (InputChoiceOneCombo) getPropertiesMap().getAddInputComponent();
			//inputChoiceOneCombo.getPropertiesMap().setRendered(Boolean.FALSE);
			
			if(Boolean.TRUE.equals(getPropertiesMap().getFilterable())){
				InputText inputText = new InputText();
				Watermark watermark = new Watermark();
				watermark.getPropertiesMap().setValue(StringHelper.getInstance().get("search", new Object[]{}));
				inputText.getPropertiesMap().setWatermark(watermark);
				getPropertiesMap().setFilterInputComponent(inputText);
				
				Command command = new Command();
				command.setLabelFromIdentifier("search")._setLabelPropertyRendered(Boolean.FALSE);
				command.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.SEARCH);
				if(JavaServerFacesHelper.Library.PRIMEFACES.equals(Component.JAVA_SERVER_FACES_LIBRARY)){
					command.getPropertiesMap().setType("button");
					command.getPropertiesMap().setOnClick(JavaServerFacesHelper.Primefaces.Script.getInstance().getMethodCallFilter(this));	
				}
				getPropertiesMap().setFilterCommandComponent(command);
				
				//((Component)getPropertiesMap().getFilterInputComponent()).getPropertiesMap().setRendered(getPropertiesMap().getFilterable());
				//((Component)getPropertiesMap().getFilterCommandComponent()).getPropertiesMap().setRendered(getPropertiesMap().getFilterable());
			}
		}
	
	}
	
	public Column addColumn(String labelStringIdentifier,String fieldName){
		if(getPropertiesMap().getColumns()==null){
			Columns columns = new Columns();
			columns.getPropertiesMap().setAction(getPropertiesMap().getAction());
			getPropertiesMap().setColumns(columns);
		}
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
			CollectionHelper.Instance<Row> rows = (CollectionHelper.Instance<Row>) getPropertiesMap().getRowsCollectionInstance();
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
			
			getPropertiesMap().setRowsCollectionInstance(rows);
			
			if(rows==null)
				getPropertiesMap().setValue(null);
			else
				getPropertiesMap().setValue(rows.getElements());
		}
		return this;
	}
	
	public DataTable addManyRow(CollectionHelper.Instance<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			addManyRow(collection.getElements());
		return this;
	}
	
	public DataTable addOneRow(Object object){
		if(object!=null)
			addManyRow(Arrays.asList(object));
		return this;
	}
	
	public DataTable addOneRow(){
		Class<?> aClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(aClass!=null)
			addOneRow(ClassHelper.getInstance().instanciateOne(aClass));
		return this;
	}
	
	/**/
	
	public DataTable removeManyRow(Collection<Row> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection)){
			@SuppressWarnings("unchecked")
			CollectionHelper.Instance<Row> rows = (CollectionHelper.Instance<Row>) getPropertiesMap().getRowsCollectionInstance();
			if(rows!=null)
				rows.removeMany(collection);
		}
		return this;
	}
	
	public DataTable removeManyRow(CollectionHelper.Instance<Row> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			removeManyRow(collection.getElements());
		return this;
	}
	
	public DataTable removeOneRow(Row row){
		if(row!=null)
			removeManyRow(Arrays.asList(row));
		return this;
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	public DataTable addRowsCollectionInstanceListener(CollectionHelper.Instance.Listener<Object> listener){
		((CollectionHelper.Instance<Object>)getPropertiesMap().getRowsCollectionInstance()).addListener(listener);
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
	
	public Collection<Input<?>> getInputs(){
		Collection<Input<?>> inputs = new ArrayList<Input<?>>();
		if(children!=null && children.getElements()!=null)
			for(Component component : children.getElements())
				if(component instanceof Row && ((Row)component).cells!=null)
					for(Cell cell : ((Row)component).cells)
						if(cell.input!=null)
							inputs.add(cell.input);
		return inputs;
	}
	
	public DataTable read(){
		Input.read(getInputs());
		return this;
	}
	
	public DataTable write(){
		Input.write(getInputs());
		return this;
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
		private Class<Input<?>> inputClass;
		
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
				else{
					fieldType = FieldHelper.getInstance().get(object.getClass(), fieldName).getType();
					//inputClass = (Class<Input<?>>) Input.getListener().getClass(null, object, FieldHelper.getInstance().get(object.getClass(), fieldName));
					if( Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction()) )
						cellValueType = Cell.ValueType.INPUT;
				}
				if( cellValueType==null ){
					if(FileHelper.getListener().getModelClass().equals(fieldType))
						cellValueType = Cell.ValueType.FILE;
					else
						cellValueType = Cell.ValueType.DEFAULT;
				}
				if( Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction()) )
					;
				else
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
			column.getPropertiesMap().setAction(getPropertiesMap().getAction());
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
		public static CollectionHelper.Instance<Row> instanciateMany(Collection<?> collection,Component component,CollectionHelper.Instance<Row> rows){
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
					rows = new CollectionHelper.Instance<>();
				for(Object object : collection){
					Row row = new Row()._setObject(object);
					rows.addOne(row);
					row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows.getElements()),0l));
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
		
		public static enum ValueType {TEXT,LINK,MENU,FILE,IMAGE,INPUT; public static ValueType DEFAULT = ValueType.TEXT;}
		
		private Column column;
		private Row row;
		private Input<?> input;
		/**/
		
		public static Cell instanciateOne(Column column,Row row){
			column.computeCellValueType(row.getPropertiesMap().getValue());
			
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
							java.lang.reflect.Field field = FieldHelper.getInstance().getLast(object.getClass(),fieldName);
							output = Output.getListener().get(null,object, field); 
							
							if(column.getPropertiesMap().getAction() instanceof Constant.Action){
								Constant.Action action = (Action) column.getPropertiesMap().getAction();
								if(Constant.Action.isCreateOrUpdate(action) && ValueType.INPUT.equals(column.cellValueType)){
									cell.input = Input.get(null, object, field);
									cell.input.getPropertiesMap().setWatermark(null);
								}
							}
							/*
							if(column.inputClass == null){
								column.inputClass = (Class<Input<?>>) Input.getListener().getClass(null, object, FieldHelper.getInstance()
										.get(row.getPropertiesMap().getValue().getClass(), fieldName));
							}
							
							if(column.getInputClass()!=null){
								cell.input = ClassHelper.getInstance().instanciateOne(column.getInputClass());
							}
							*/
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
			if(index==null)
				return null;
			
			return CollectionHelper.getInstance().getElementAt(cells, index);
		}
		
	}
	
	/**/
	
	public static <T> DataTable instanciateOne(Class<T> actionOnClass,String[] fieldNames,Collection<T> collection,Integer page,Boolean lazy){
		DataTable dataTable = new DataTable();
		
		dataTable.addColumnsByFieldNames(fieldNames);
		if(page!=null){
			dataTable.getPropertiesMap().setRows(page);
			dataTable.getPropertiesMap().setPaginator(Boolean.TRUE);
		}
		dataTable.getPropertiesMap().setLazy(lazy!=null && lazy);
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
		
		void listenPrepare(DataTable dataTable);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			
			}
			
			@Override
			public void listenPrepare(DataTable dataTable) {}
			
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
	
	/**/
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class AddCommandComponentActionAdapter extends CommandHelper.Command.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		protected DataTable dataTable;
		
		@Override
		protected Object __execute__() {
			InputChoiceOneCombo inputChoiceOneCombo = (InputChoiceOneCombo) dataTable.getPropertiesMap().getAddInputComponent();
			Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
			if(actionOnClass==null){
				
			}else{
				Object object = ClassHelper.getInstance().instanciateOne(actionOnClass);
				setObjectSource(object,inputChoiceOneCombo.getValue());
				listenObjectCreated(object,inputChoiceOneCombo.getValue());
				dataTable.addOneRow(object);
				CollectionHelper.Instance<?> collection = getDestinationCollection();
				if(collection!=null)
					collection.addOne(object);
				
				if(inputChoiceOneCombo.getValue()!=null)
					inputChoiceOneCombo.getChoices().removeOne(inputChoiceOneCombo.getValue());
					
			}
			
			return null;
		}
		
		protected void setObjectSource(Object object,Object source){
			if(dataTable.getPropertiesMap().getChoiceValueClass()!=null)
				FieldHelper.getInstance().set(object, source,ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
		}
		
		protected void listenObjectCreated(Object object,Object source){}
		
		protected CollectionHelper.Instance<?> getDestinationCollection(){
			if(dataTable.getPropertiesMap().getActionOnClass()!=null)
				return (CollectionHelper.Instance<?>) FieldHelper.getInstance().read(dataTable.getForm().getMaster().getObject()
					, ClassHelper.getInstance().getVariableName((Class<?>) dataTable.getPropertiesMap().getActionOnClass(),Boolean.TRUE));// ((Order)).getOrderItems();
			return null;
		}
		
	}
	
	/**/
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class RemoveCommandComponentActionAdapter extends CommandHelper.Command.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		protected DataTable dataTable;
		
		@Override
		protected Object __execute__() {
			InputChoiceOneCombo inputChoiceOneCombo = (InputChoiceOneCombo) dataTable.getPropertiesMap().getAddInputComponent();
			Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
			if(actionOnClass==null){
				
			}else{
				Object object = ClassHelper.getInstance().instanciateOne(actionOnClass);
				setObjectSource(object,inputChoiceOneCombo.getValue());
				listenObjectCreated(object,inputChoiceOneCombo.getValue());
				dataTable.addOneRow(object);
				CollectionHelper.Instance<?> collection = getDestinationCollection();
				if(collection!=null)
					collection.addOne(object);
				
				if(inputChoiceOneCombo.getValue()!=null)
					inputChoiceOneCombo.getChoices().removeOne(inputChoiceOneCombo.getValue());
					
			}
			
			return null;
		}
		
		protected void setObjectSource(Object object,Object source){
			if(dataTable.getPropertiesMap().getChoiceValueClass()!=null)
				FieldHelper.getInstance().set(object, source,ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
		}
		
		protected void listenObjectCreated(Object object,Object source){}
		
		protected CollectionHelper.Instance<?> getDestinationCollection(){
			if(dataTable.getPropertiesMap().getActionOnClass()!=null)
				return (CollectionHelper.Instance<?>) FieldHelper.getInstance().read(dataTable.getForm().getMaster().getObject()
					, ClassHelper.getInstance().getVariableName((Class<?>) dataTable.getPropertiesMap().getActionOnClass(),Boolean.TRUE));// ((Order)).getOrderItems();
			return null;
		}
		
	}
}
