package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.CascadeStyleSheetHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.JavaServerFacesHelper;
import org.cyk.utility.common.userinterface.collection.DataTable.Column.CellValueSource;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;
import org.cyk.utility.common.userinterface.command.RemoteCommand;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.Watermark;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOne;
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
	/*
	private Boolean onPrepareAddMenu;
	private Boolean onPrepareAddMenuAddCommand=Boolean.TRUE;
	private Boolean onPrepareAddColumnOrderNumber = Boolean.TRUE;
	private Boolean onPrepareAddColumnAction;
	*/
	/**/
	
	/*public DataTable(Class<?> actionOnClass) {
		this.actionOnClass = actionOnClass;
	}
	
	public DataTable() {
		this(null);
	}*/
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		propertiesMap.setOnPrepareAddMenuAddCommand(Boolean.TRUE);
		propertiesMap.setOnPrepareAddColumnOrderNumber(Boolean.TRUE);
		if(isJavaServerFacesLibraryPrimefaces())
			propertiesMap.addString(Properties.STYLE_CLASS, Constant.CHARACTER_SPACE.toString(), CascadeStyleSheetHelper.getInstance().getClass(DataTable.class));
	}
	
	public Properties getPropertyRowProperties(Boolean createIfNull){
		Properties properties = (Properties) getPropertiesMap().getRowProperties();
		if(properties == null && Boolean.TRUE.equals(createIfNull))
			getPropertiesMap().setRowProperties(properties = new Properties());
		return properties;
	}
	
	public Properties getPropertyRowProperties(){
		return getPropertyRowProperties(Boolean.TRUE);
	}
	
	public Properties getPropertyRowPropertiesPropertyRemoveCommandProperties(Boolean createIfNull){
		Properties properties = (Properties) getPropertyRowProperties().getRemoveCommandProperties();
		if(properties == null && Boolean.TRUE.equals(createIfNull))
			getPropertyRowProperties().setRemoveCommandProperties(properties = new Properties());
		return properties;
	}
	
	public Properties getPropertyRowPropertiesPropertyRemoveCommandProperties(){
		return getPropertyRowPropertiesPropertyRemoveCommandProperties(Boolean.TRUE);
	}
	
	@Override
	public DataTable build() {
		Columns columns = (Columns) getPropertiesMap().getColumns();
		if(columns!=null){
			CollectionHelper.getInstance().sort((Collection<?>) columns.getPropertiesMap().getValue());
		}
		return (DataTable) super.build();
	}
	
	public MenuNode addMainMenuNode(String labelStringIdentifier,Object icon,UniformResourceLocatorHelper.Stringifier uniformResourceLocatorStringifier){
		Menu menu = (Menu) getPropertiesMap().getMainMenu();
		if(menu == null){
			menu = new Menu().setRenderType(Menu.RenderType.BAR);
			getPropertiesMap().setMainMenu(menu);
		}
		MenuNode menuNode = (MenuNode) menu.addNode(labelStringIdentifier)._setPropertyUrl(uniformResourceLocatorStringifier)._setPropertyIcon(icon);
		return menuNode;
	}
	
	public MenuNode addMainMenuNode(String labelStringIdentifier,Object icon,Constant.Action action,Object object,Object...queryKeyValue){
		Menu menu = (Menu) getPropertiesMap().getMainMenu();
		if(menu == null){
			menu = new Menu().setRenderType(Menu.RenderType.BAR);
			getPropertiesMap().setMainMenu(menu);
		}
		MenuNode menuNode = (MenuNode) menu.addNode(labelStringIdentifier)._setPropertyUrl(action, object,queryKeyValue)._setPropertyIcon(icon);
		return menuNode;
	}
	
	@SuppressWarnings("unchecked")
	public static void addMenu(Component component){
		Properties componentProperties = component.getPropertiesMap();
		if(Boolean.TRUE.equals(componentProperties.getOnPrepareAddMenu())){
			Menu menu = (Menu) componentProperties.getMainMenu();
			if(menu == null){
				menu = new Menu().setRenderType(Menu.RenderType.BAR);
				componentProperties.setMainMenu(menu);
			}
			component.addOneChild(menu);
			if(componentProperties.getActionOnClass()!=null){
				if(Boolean.TRUE.equals(componentProperties.getOnPrepareAddMenuAddCommand())){
					Object[] queryKeyValues = new Object[]{componentProperties.getMaster() == null ? null : componentProperties.getMaster().getClass()
							,componentProperties.getMaster()}; 
					Collection<Object> addCommandQueryKeyValues = (Collection<Object>)componentProperties.getAddCommandQueryKeyValues();
					if(CollectionHelper.getInstance().isNotEmpty(addCommandQueryKeyValues))
						queryKeyValues = ArrayUtils.addAll(queryKeyValues, addCommandQueryKeyValues.toArray());
					
					menu.addNode("add")._setPropertyUrl(Constant.Action.CREATE, componentProperties.getActionOnClass(),queryKeyValues)
						._setPropertyIcon(IconHelper.Icon.FontAwesome.PLUS);
				}		
			}	
		}
	}

	@Override
	public DataTable prepare() {
		super.prepare();
		addMenu();
		addFirstColumns();
		__prepare__();
		addLastColumns();
		if(Boolean.TRUE.equals(onPrepareCallLoad)){
			load(); //can be trigger by callback to enabled fast rendering of table structure	
		}
		
		return this;
	}
	
	protected void addMenu(){
		addMenu(this);
	}
	
	public static void addFirstColumns(Component component){
		Column orderNumberColumn = null;
		if(Boolean.TRUE.equals(component.getPropertiesMap().getOnPrepareAddColumnOrderNumber())){
			orderNumberColumn =Columns.addColumn(component,"userinterface.column.order.number", FIELD___ORDER_NUMBER__);
			orderNumberColumn.setCellValueSource(CellValueSource.ROW).set__orderNumber__(Long.MIN_VALUE);	
		}
		
		if(component instanceof DataTable){
			Class<?> choiceValueClass = (Class<?>)component.getPropertiesMap().getChoiceValueClass();
			if(choiceValueClass!=null){
				Column choiceValueColumn = ((DataTable)component).addColumnByFieldName(ClassHelper.getInstance().getVariableName(choiceValueClass));
				if(orderNumberColumn!=null)
					choiceValueColumn.set__orderNumber__(orderNumberColumn.get__orderNumber__()+1);
				choiceValueColumn.setCellValueType(DataTable.Cell.ValueType.TEXT);	
			}
		}
	}
	
	protected void addFirstColumns(){
		addFirstColumns(this);
		/*
		Class<?> choiceValueClass = (Class<?>)getPropertiesMap().getChoiceValueClass();
		Column orderNumberColumn = null,choiceValueColumn = null;
		if(Boolean.TRUE.equals(getPropertiesMap().getOnPrepareAddColumnOrderNumber())){
			orderNumberColumn = addColumn("userinterface.column.order.number", FIELD___ORDER_NUMBER__);
			orderNumberColumn.setCellValueSource(CellValueSource.ROW).set__orderNumber__(Long.MIN_VALUE);	
		}
		if(choiceValueClass!=null){
			choiceValueColumn = addColumnByFieldName(ClassHelper.getInstance().getVariableName(choiceValueClass));
			if(orderNumberColumn!=null)
				choiceValueColumn.set__orderNumber__(orderNumberColumn.get__orderNumber__()+1);
			choiceValueColumn.setCellValueType(DataTable.Cell.ValueType.TEXT);
		}
		*/
	}
	
	public static void addLastColumns(Component component){
		if(Boolean.TRUE.equals(component.getPropertiesMap().getOnPrepareAddColumnAction())){
			Columns.getProperty(component).addColumn("userinterface.column.action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP)
				.setCellValueType(Cell.ValueType.MENU).set__orderNumber__(Long.MAX_VALUE);	
		}
	}
	
	protected void addLastColumns(){
		addOtherColumns();
		if(Boolean.TRUE.equals(getPropertiesMap().getOnPrepareAddColumnAction())){
			addColumn("userinterface.column.action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP).setCellValueType(Cell.ValueType.MENU).set__orderNumber__(Long.MAX_VALUE);	
		}
	}
	
	protected void addOtherColumns(){
		addColumnsByFieldNames(getColumnsFieldNames());
	}
	
	protected Collection<String> getColumnsFieldNames(){
		Collection<String> collection = ClassHelper.getInstance().instanciateOne(Listener.class).getColumnsFieldNames(this);
		if(collection == null)
			collection = new ArrayList<String>();
		ClassHelper.getInstance().instanciateOne(Listener.class).processColumnsFieldNames(this, collection);
		final List<String> order = ClassHelper.getInstance().instanciateOne(Listener.class).getColumnsFieldNamesOrder(this);
		if(CollectionHelper.getInstance().isNotEmpty(order)){
			//System.out.println("DataTable.getColumnsFieldNames() : "+collection);
			Collections.sort((List<String>) collection, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					Integer i1 = order.indexOf(o1);
					Integer i2 = order.indexOf(o2);
					//System.out.println("DataTable.getColumnsFieldNames().new Comparator() {...}.compare() "+o1+":"+o2);
					return i1 == -1 || i2 == -1 ? 0 : i1.compareTo(i2);
				}
			});
		}
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	protected void __prepare__(){
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		Boolean isCreateOrUpdateAction = Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction());
		if(isCreateOrUpdateAction){
			Command addCommand = new Command();
			addCommand.setLabelFromIdentifier("userinterface.command.add")._setLabelPropertyRendered(Boolean.FALSE);
			addCommand.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.PLUS);
			addCommand.getPropertiesMap().setInputValueIsNotRequired(Boolean.TRUE);
			addCommand.getPropertiesMap().setGetter(Properties.FIELD_RENDERED, new Properties.Getter() {
				@Override
				public Object execute(Properties properties, Object key, Object value, Object nullValue) {
					if(DataTable.this.getPropertiesMap().getAddInputComponent()==null)
						return properties.getRendered();
					else
						return ((InputChoice<?>)DataTable.this.getPropertiesMap().getAddInputComponent()).getPropertiesMap().getRendered();
				}
			});
			getPropertiesMap().setAddCommandComponent(addCommand);
			if(isJavaServerFacesLibraryPrimefaces()){
				//addCommand.getPropertiesMap().setImmediate(Boolean.TRUE);
				//addCommand.getPropertiesMap().setProcess(getPropertiesMap().getIdentifier());
				addCommand.getPropertiesMap().setUpdate("@(."+getPropertiesMap().getIdentifierAsStyleClass()+")");
			}
			
			Class<AddCommandComponentActionAdapter> actionAdapterClass = InstanceHelper.getInstance().getIfNotNullElseDefault(
					(Class<AddCommandComponentActionAdapter>) getPropertiesMap().getAddCommandComponentActionAdapterClass(),AddCommandComponentActionAdapter.class);
			
			AddCommandComponentActionAdapter actionAdapter = ClassHelper.getInstance().instanciateOne(actionAdapterClass);
			actionAdapter.setDataTable(this);
			addCommand.setAction(actionAdapter);
			
			OutputText outputText = new OutputText();
			getPropertiesMap().setAddTextComponent(outputText);
			
			InputChoiceOneCombo inputChoiceOneCombo = new InputChoiceOneCombo();
			inputChoiceOneCombo.getPropertiesMap();//trigger
			//watermark = new Watermark();
			//watermark.getPropertiesMap().setValue(StringHelper.getInstance().get("search", new Object[]{}));
			//inputChoice.getPropertiesMap().setWatermark(watermark);
			getPropertiesMap().setAddInputComponent(inputChoiceOneCombo);
			
			inputChoiceOneCombo.getPropertiesMap().setGetter(Properties.FIELD_RENDERED, new Properties.Getter() {
				@Override
				public Object execute(Properties properties, Object key, Object value, Object nullValue) {
					return CollectionHelper.getInstance().isNotEmpty(((InputChoice<?>)DataTable.this.getPropertiesMap().getAddInputComponent()).getChoices());
				}
			});
			
			if(getPropertiesMap().getChoiceValueClass()!=null){
				Class<?> choiceValueClass = (Class<?>)getPropertiesMap().getChoiceValueClass();	
				outputText.getPropertiesMap().setValue(StringHelper.getInstance().getClazz(choiceValueClass));
				
				if(ClassHelper.getInstance().isIdentified(choiceValueClass)){
					inputChoiceOneCombo.getChoices().addMany(InstanceHelper.getInstance().get(choiceValueClass));
										
				}
			}
			
			//((Command)getPropertiesMap().getAddCommandComponent()).getPropertiesMap().setInputValueIsNotRequired(Boolean.TRUE);
			
			//((Component)getPropertiesMap().getFilterInputComponent()).getPropertiesMap().setRendered(Boolean.FALSE);
			//((Component)getPropertiesMap().getFilterCommandComponent()).getPropertiesMap().setRendered(Boolean.FALSE);
			
			CollectionHelper.Instance<Row> rows = (CollectionHelper.Instance<Row>)getPropertiesMap().getRowsCollectionInstance();
			if(rows==null){
				rows = new CollectionHelper.Instance<>();
				getPropertiesMap().setRowsCollectionInstance(rows);	
			}
			
			if(getPropertiesMap().getRowsCollectionInstanceListenerClass()==null){
				getPropertiesMap().setRowsCollectionInstanceListenerClass(RowsCollectionInstanceListener.class);
			}
			
			if(getPropertiesMap().getRowsCollectionInstanceListenerClass()!=null && rows!=null){
				RowsCollectionInstanceListener rowsCollectionInstanceListener = ClassHelper.getInstance().instanciateOne((Class<? extends RowsCollectionInstanceListener>) getPropertiesMap().getRowsCollectionInstanceListenerClass());
				rowsCollectionInstanceListener.setDataTable(this);
				rows.addListener(rowsCollectionInstanceListener);
			}
			
			if(onPrepareCallLoad == null && Constant.Action.UPDATE.equals(getPropertiesMap().getAction()))
				onPrepareCallLoad = Boolean.TRUE;
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
					
				}
			}
			
			addFilter(this);			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataTable load() {
		super.load();
		if(getPropertiesMap().getActionOnClass()!=null){
			Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
			Collection<?> instances;
			if(getPropertiesMap().getMaster()==null)
				instances = InstanceHelper.getInstance().get((Class<?>) getPropertiesMap().getActionOnClass());
			else{
				FilterHelper.Filter<Object> filter = (FilterHelper.Filter<Object>) ClassHelper.getInstance().instanciateOne(FilterHelper.Filter.ClassLocator.getInstance()
						.locate(actionOnClass));
				filter.addMaster(getPropertiesMap().getMaster());
				instances = InstanceHelper.getInstance().get((Class<Object>) getPropertiesMap().getActionOnClass(),filter, new DataReadConfiguration(null, null));
			}
			addManyRow(instances);
		}
		return this;
	}
	
	public static void addFilter(Component component){
		if(Boolean.TRUE.equals(component.getPropertiesMap().getFilterable())){
			InputText inputText = new InputText();
			Watermark watermark = new Watermark();
			watermark.getPropertiesMap().setValue(StringHelper.getInstance().get("userinterface.input.search.watermark.label", new Object[]{}));
			inputText.getPropertiesMap().setWatermark(watermark);
			component.getPropertiesMap().setFilterInputComponent(inputText);
			
			Command command = new Command();
			command.setLabelFromIdentifier("userinterface.command.search")._setLabelPropertyRendered(Boolean.FALSE);
			command.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.SEARCH);
			if(JavaServerFacesHelper.Library.PRIMEFACES.equals(Component.JAVA_SERVER_FACES_LIBRARY)){
				command.getPropertiesMap().setType("button");
				command.getPropertiesMap().setOnClick(JavaServerFacesHelper.Primefaces.Script.getInstance().getMethodCallFilter(component));	
			}
			component.getPropertiesMap().setFilterCommandComponent(command);
			
			//((Component)getPropertiesMap().getFilterInputComponent()).getPropertiesMap().setRendered(getPropertiesMap().getFilterable());
			//((Component)getPropertiesMap().getFilterCommandComponent()).getPropertiesMap().setRendered(getPropertiesMap().getFilterable());
		}
	}
	
	public Column addColumn(String labelStringIdentifier,String fieldName){
		return Columns.addColumn(this, labelStringIdentifier, fieldName);
	}
	
	public Column addColumnByFieldName(String fieldName){
		String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
		return addColumn(labelStringIdentifier, fieldName);
	}
	
	public DataTable addColumnsByFieldNames(Collection<String> fieldNames){
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames))
			for(String fieldName : fieldNames)
				addColumnByFieldName(fieldName);
		return this;
	}
	
	public DataTable addColumnsByFieldNames(String...fieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(fieldNames))
			addColumnsByFieldNames(Arrays.asList(fieldNames));
		return this;
	}
	
	public DataTable addColumnListener(CollectionHelper.Instance.Listener<Component> listener){
		Columns columns = getPropertyColumns(Boolean.TRUE);
		if(columns.getChildren() == null)
			columns.setChildren(instanciateChildrenCollection());
		columns.getChildren().addListener(listener);
		return this;
	}
	
	public DataTable addManyRow(Collection<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection)){
			@SuppressWarnings("unchecked")
			CollectionHelper.Instance<Row> rows = (CollectionHelper.Instance<Row>) getPropertiesMap().getRowsCollectionInstance();
			rows = Row.instanciateMany(collection,this, rows);
		
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
			if(rows!=null){
				rows.removeMany(collection);
			}
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
	
	public Column getColumn(String fieldName){
		Columns columns = getPropertyColumns(Boolean.FALSE);
		if(columns!=null)
			return columns.getColumn(fieldName);
		return null;
	}
	
	public Columns getPropertyColumns(Boolean createIfNull){
		Columns columns = Columns.getProperty(this);
		if(Boolean.TRUE.equals(createIfNull)){
			getPropertiesMap().setColumns(columns = new Columns(this));
		}
		return columns;
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
			return Cell.getOne(this, row,(Cell.Listener) getPropertiesMap().getCellListener());
		}
			
		public Column computeCellValueType(Object object,Boolean sortable){
			if(object!=null && cellValueType==null){
				LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
				loggingMessageBuilder.addManyParameters("compute cell value type");
				String fieldName = (String) getPropertiesMap().getFieldName();
				loggingMessageBuilder.addNamedParameters("action",getPropertiesMap().getAction(),"field name",fieldName,"cell value source",cellValueSource);
				Class<?> fieldType = null;
				if(CellValueSource.ROW.equals(cellValueSource))
					fieldType = FieldHelper.getInstance().get(Row.class, fieldName).getType();
				else if(CellValueSource.ROW_PROPERTIES_MAP.equals(cellValueSource))
					fieldType = Object.class;
				else{
					fieldType = FieldHelper.getInstance().getType(object.getClass(), FieldHelper.getInstance().get(object.getClass(), fieldName));
					//inputClass = (Class<Input<?>>) Input.getListener().getClass(null, object, FieldHelper.getInstance().get(object.getClass(), fieldName));
					if( Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction()) )
						cellValueType = Cell.ValueType.INPUT;
				}
				loggingMessageBuilder.addNamedParameters("field type",fieldType);
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
						getPropertiesMap().setSortable(sortable);
				}
				loggingMessageBuilder.addNamedParameters("cell value type",cellValueType,"linked",getPropertiesMap().getLinked());
				logTrace(loggingMessageBuilder);
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
			column.getPropertiesMap().setHeader(column.getLabel());
			column.getPropertiesMap().setSortable(Boolean.FALSE);
			
			OutputText footer = new OutputText();
			column.getPropertiesMap().setFooter(footer);
			
			column.getPropertiesMap().setFieldName(fieldName);
			column.addPropertyStyleClass(CascadeStyleSheetHelper.getInstance().getClass(fieldName.toLowerCase()));
			return column;
		}
		
		public static Column instanciateOne(String labelStringIdentifier,String fieldName){
			return instanciateOne(labelStringIdentifier, fieldName, CellValueSource.DEFAULT);
		}
	
		/**/
		
		public void __setPropertyFooterPropertyValueBasedOnMaster__(){
			Column column = this;
			LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
			loggingMessageBuilder.addManyParameters("set column footer value");
			DataTable dataTable = (DataTable) column.getPropertiesMap().getDataTable();
			loggingMessageBuilder.addNamedParameters("field name",column.getPropertiesMap().getFieldName(),"master exist",dataTable.getPropertiesMap().getMaster()!=null);
			if(dataTable.getPropertiesMap().getMaster()!=null){
				java.lang.reflect.Field field = FieldHelper.getInstance().get(dataTable.getPropertiesMap().getMaster().getClass(), (String)column.getPropertiesMap().getFieldName());
				loggingMessageBuilder.addNamedParameters("field exist",field!=null);
				if(field!=null){
					loggingMessageBuilder.addNamedParameters("before set",((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().getValue());
					((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().setValue(FieldHelper.getInstance()
							.read(dataTable.getPropertiesMap().getMaster(), field));
					loggingMessageBuilder.addNamedParameters("after set",((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().getValue());
				}
			}
			logTrace(loggingMessageBuilder);
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
		
		@Override
		protected void listenPropertiesInstanciated(Properties propertiesMap) {
			super.listenPropertiesInstanciated(propertiesMap);
			propertiesMap.setGetter(Properties.FOOTER_RENDERED, new Properties.Getter() {
				@Override
				public Object execute(Properties properties, Object key, Object value, Object nullValue) {
					@SuppressWarnings("unchecked")
					Collection<Column> columns = (Collection<Column>) getPropertiesMap().getValue();
					for(Column column : columns)
						if(column.getPropertiesMap().getFooter()!=null){
							if(column.getPropertiesMap().getFooter() instanceof OutputText && ((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().getValue()!=null)
								return Boolean.TRUE;
						}
					return Boolean.FALSE;
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public Columns(Component component) {
			getPropertiesMap().setAction(component.getPropertiesMap().getAction());
			getPropertiesMap().setCellListener(component.getPropertiesMap().getCellListener());
			getPropertiesMap().setDataTable(component);
			if(component.getPropertiesMap().getColumnsCollectionInstanceListenerClass()!=null){
				if(getChildren() == null)
					setChildren(instanciateChildrenCollection());
				getChildren().addListener((org.cyk.utility.common.helper.CollectionHelper.Instance.Listener<Component>) component.getPropertiesMap().getColumnsCollectionInstanceListenerClass());
			}
			
			component.getPropertiesMap().setColumns(this);
		}
		
		public Column addColumn(String labelStringIdentifier,String fieldName){
			DataTable.Column column = Column.instanciateOne(labelStringIdentifier, fieldName);
			column.getPropertiesMap().setAction(getPropertiesMap().getAction());
			column.getPropertiesMap().setCellListener(getPropertiesMap().getCellListener());
			column.getPropertiesMap().setDataTable(getPropertiesMap().getDataTable());
			//column.setLabelFromIdentifier(labelStringIdentifier);
			//column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
			//column.getPropertiesMap().setFieldName(fieldName);
			
			addOneChild(column);
			
			if(getPropertiesMap().getValue()==null)
				getPropertiesMap().setValue(getChildren().getElements());
			return column;
		}
		
		public static Column addColumn(Component component,String labelStringIdentifier,String fieldName){
			Columns columns = (Columns) component.getPropertiesMap().getColumns();
			if(component.getPropertiesMap().getColumns()==null){
				columns = new Columns(component);
				
			}
			return columns.addColumn(labelStringIdentifier, fieldName);
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
				columns = new Columns(component);
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
		
		public static Columns getProperty(Component component){
			return (Columns) component.getPropertiesMap().getColumns();
		}
		
		@SuppressWarnings("unchecked")
		public static Collection<Column> getPropertyValue(Component component){
			Columns columns = getProperty(component);
			return columns == null ? null : (Collection<Column>) columns.getPropertiesMap().getValue();
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
				._setPropertyIcon(IconHelper.Icon.FontAwesome.PENCIL);
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
				column.computeCellValueType(object,sortable);
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
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper=false,of={Cell.FIELD_COLUMN,Cell.FIELD_ROW})
	public static class Cell extends Component.Visible implements Serializable {
		private static final long serialVersionUID = 1L;
		
		static {
			ClassHelper.getInstance().map(Cell.Listener.class, Cell.Listener.Adapter.Default.class, Boolean.FALSE);
		}
		
		public static enum ValueType {TEXT,LINK,MENU,FILE,IMAGE,INPUT; public static ValueType DEFAULT = ValueType.TEXT;}
		
		private Column column;
		private Row row;
		private Input<?> input;
		/**/
		
		public static Cell instanciateOne(Column column,Row row,Cell.Listener listener){
			return (listener == null ? ClassHelper.getInstance().instanciateOne(Cell.Listener.class) : listener).instanciateOne(column, row);
		}
		
		public static Cell getOne(Column column,Row row,Cell.Listener listener){
			Cell cell = null;
			if(CollectionHelper.getInstance().isNotEmpty(column.cells))
				for(Cell index : column.cells)
					if(index.column.equals(column) && index.row.equals(row)) {
						cell = index;
						break;
					}
			if(cell==null){
				column.addOneCell(cell = Cell.instanciateOne(column, row,listener));
				row.addOneCell(cell);
			}
			return cell;
		}
		
		/**/
		
		public static final String FIELD_COLUMN = "column";
		public static final String FIELD_ROW = "row";
		
		/**/
		
		public static interface Listener {
			
			Cell instanciateOne(Column column,Row row);
			
			public static class Adapter extends AbstractBean implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Cell instanciateOne(Column column, Row row) {
					return null;
				}
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Cell instanciateOne(Column column,Row row){
						column.computeCellValueType(row.getPropertiesMap().getValue(),Boolean.TRUE);
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
										FieldHelper.Constraints constraints = FieldHelper.Field.get(row.getPropertiesMap().getValue().getClass(), fieldName).getConstraints();
										Object object = FieldHelper.getInstance().readBeforeLast(row.getPropertiesMap().getValue(), fieldName);
										java.lang.reflect.Field field = FieldHelper.getInstance().getLast(object.getClass(),fieldName);
										output = Output.getListener().get(null,object, field,constraints); 
										
										if(column.getPropertiesMap().getAction() instanceof Constant.Action){
											Constant.Action action = (Action) column.getPropertiesMap().getAction();
											if(Constant.Action.isCreateOrUpdate(action) && ValueType.INPUT.equals(column.cellValueType)){
												cell.input = Input.get(null, object, field,constraints);
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
					
				}
				
			}
		}
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
		
		Collection<String> getColumnsFieldNames(DataTable dataTable);
		void processColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames);
		List<String> getColumnsFieldNamesOrder(DataTable dataTable);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void processColumnsFieldNames(DataTable dataTable, Collection<String> fieldNames) {
					super.processColumnsFieldNames(dataTable, fieldNames);
					Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
					
					if(ClassHelper.getInstance().isIdentified(actionOnClass))
						__addFieldName__(fieldNames,actionOnClass,ClassHelper.getInstance().getIdentifierFieldName(actionOnClass));
					if(ClassHelper.getInstance().isNamed(actionOnClass))
						__addFieldName__(fieldNames,actionOnClass,ClassHelper.getInstance().getNameFieldName(actionOnClass));
					
					if(ClassHelper.getInstance().isHierarchy(actionOnClass))
						__addFieldName__(fieldNames,actionOnClass,ClassHelper.getInstance().getHierarchyFieldName(actionOnClass));
					if(ClassHelper.getInstance().isTyped(actionOnClass))
						__addFieldName__(fieldNames,actionOnClass,ClassHelper.getInstance().getTypeFieldName(actionOnClass));
				}
				
				protected void __addFieldName__(Collection<String> fieldNames,Class<?> actionOnClass,String fieldName){
					if(Output.isOutputable(actionOnClass, fieldName))
						fieldNames.add(fieldName);
				}
			}
			
			@Override
			public Collection<String> getColumnsFieldNames(DataTable dataTable) {
				return null;
			}
			
			@Override
			public void processColumnsFieldNames(DataTable dataTable, Collection<String> fieldNames) {
				
			}
			
			@Override
			public List<String> getColumnsFieldNamesOrder(DataTable dataTable) {
				return null;
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
	public static class RowsCollectionInstanceListener extends CollectionHelper.Instance.Listener.Adapter<Row> {
		private static final long serialVersionUID = 1L;
		
		protected DataTable dataTable;
		
		@SuppressWarnings("unchecked")
		@Override
		public void addOne(Instance<Row> instance, final Row row, Object source, Object sourceObject) {
			super.addOne(instance, row, source, sourceObject);
			Class<? extends RemoveCommandComponentActionAdapter> removeCommandComponentActionAdapterClass = InstanceHelper.getInstance()
					.getIfNotNullElseDefault((Class<? extends RemoveCommandComponentActionAdapter>)dataTable.getPropertiesMap().getRemoveCommandComponentActionAdapterClass()
					, RemoveCommandComponentActionAdapter.class);
			RemoveCommandComponentActionAdapter removeCommandComponentActionAdapter = ClassHelper.getInstance().instanciateOne(removeCommandComponentActionAdapterClass);
			removeCommandComponentActionAdapter.setDataTable(dataTable);
			removeCommandComponentActionAdapter.setRow(row);
			
			final Command command = new Command();
			command.setLabelFromIdentifier("userinterface.command.remove")._setLabelPropertyRendered(Boolean.FALSE);
			command.setAction(removeCommandComponentActionAdapter);
			command.getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.MINUS);
			command.getPropertiesMap().setInputValueIsNotRequired(Boolean.TRUE);
			command.getPropertiesMap().setImmediate(Boolean.TRUE);
			
			command.getPropertiesMap().copyFrom(((Command)dataTable.getPropertiesMap().getAddCommandComponent()).getPropertiesMap(), Properties.PROCESS,Properties.UPDATE);
			command.getPropertiesMap().copyFrom((Properties)dataTable.getPropertyRowProperties().getRemoveCommandProperties(), Properties.UPDATED_FIELD_NAMES,Properties.UPDATED_COLUMN_FIELD_NAMES);
			
			//command.getPropertiesMap().setProcess(((Command)dataTable.getPropertiesMap().getAddCommandComponent()).getPropertiesMap().getProcess());
			command.getPropertiesMap().setUpdate("@(form)");
			
			//command.usePropertyRemoteCommand();
			RemoteCommand.instanciateOne(command);
			
			row.getMenu().getPropertiesMap().setRendered(Boolean.FALSE);
			
			//row.getDeleteMenuNode().getPropertiesMap().setUrl(null);
			//RemoteCommand.instanciateOne(row.getDeleteMenuNode(),command.getAction(),command.getActionListener());
			
			
			
			new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) row.getPropertiesMap().getUpdatedFieldNames()){
				private static final long serialVersionUID = 1L;

				protected void __executeForEach__(String fieldName) {
					command.getPropertiesMap().addString(Properties.UPDATE,"@(."+dataTable.getForm().getControlByFieldName(fieldName).getPropertiesMap().getIdentifierAsStyleClass()+")");
				}
			}.execute();
			
			if(StringHelper.getInstance().isBlank((String)command.getPropertiesMap().getUpdate())){
				command.getPropertiesMap().setUpdate("@(form)");
			}
			
			row.getPropertiesMap().setRemoveCommandComponent(command);
			
			if(dataTable.getPropertiesMap().getChoiceValueClass()!=null){
				Object choice = FieldHelper.getInstance().read(row.getPropertiesMap().getValue(), ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
				if(choice!=null && Boolean.TRUE.equals( ((InputChoice<?>)dataTable.getPropertiesMap().getAddInputComponent()).getChoices().getIsSourceDisjoint() )){
					((InputChoice<?>)dataTable.getPropertiesMap().getAddInputComponent()).getChoices().removeOne(choice);
				}
			}
			
		}
	}
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class AddRemoveCommandActionAdapter extends Command.ActionAdapter {
		private static final long serialVersionUID = 1L;
		
		protected DataTable dataTable;
		
		@Override
		protected Object __execute__() {
			InputChoiceOne inputChoiceOne = (InputChoiceOne) dataTable.getPropertiesMap().getAddInputComponent();
			Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
			____execute____(actionOnClass, inputChoiceOne);
			return null;
		}
		
		protected void ____execute____(Class<?> actionOnClass,InputChoiceOne inputChoiceOne){
			
		}
		
		protected void setObjectSource(Object object,Object source){
			if(dataTable.getPropertiesMap().getChoiceValueClass()!=null)
				FieldHelper.getInstance().set(object, source,ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
		}
		
		protected void listenObjectCreated(Object object,Object source){}
		
		protected CollectionHelper.Instance<?> getDestinationCollection(){
			if(dataTable.getPropertiesMap().getActionOnClass()!=null){
				java.lang.reflect.Field field = FieldHelper.getInstance().get(dataTable.getForm().getMaster().getObject().getClass()
						, ClassHelper.getInstance().getVariableName((Class<?>) dataTable.getPropertiesMap().getActionOnClass(),Boolean.TRUE));
				if(field == null){
					field = FieldHelper.getInstance().get(dataTable.getForm().getMaster().getObject().getClass(),"items");
				}
				
				if(field != null)
					return (CollectionHelper.Instance<?>) FieldHelper.getInstance().read(dataTable.getForm().getMaster().getObject(), field);
			}
			return null;
		}
	
		protected Object getChoice(Object object){
			if(dataTable.getPropertiesMap().getChoiceValueClass()!=null)
				return FieldHelper.getInstance().read(object, ClassHelper.getInstance().getVariableName((Class<?>)dataTable.getPropertiesMap().getChoiceValueClass()));
			return null;
		}
	}
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class AddCommandComponentActionAdapter extends AddRemoveCommandActionAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void ____execute____(Class<?> actionOnClass, InputChoiceOne inputChoiceOne) {
			if(actionOnClass==null){
				
			}else{
				Object choice = inputChoiceOne == null ? null : inputChoiceOne.getValue();
				if(inputChoiceOne == null){
					
				}else{
					if(choice!=null){
						Object object = ClassHelper.getInstance().instanciateOne(actionOnClass);
						setObjectSource(object,choice);
						listenObjectCreated(object,choice);
						dataTable.addOneRow(object);
						CollectionHelper.Instance<?> collection = getDestinationCollection();
						if(collection!=null)
							collection.addOne(object);
						//inputChoiceOne.getChoices().removeOne(choice);	
					}
						
				}		
			}
		}
	}
	
	/**/
	
	@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
	public static class RemoveCommandComponentActionAdapter extends AddRemoveCommandActionAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Row row;
		
		@SuppressWarnings("unchecked")
		@Override
		protected void ____execute____(Class<?> actionOnClass, InputChoiceOne inputChoiceOne) {
			final LoggingHelper.Message.Builder vLoggingMessageBuilder = loggingMessageBuilder;//new LoggingHelper.Message.Builder.Adapter.Default();
			vLoggingMessageBuilder.addManyParameters("remove datatable row");
			
			if(row.getPropertiesMap().getValue()!=null){
				vLoggingMessageBuilder.addNamedParameters("value id",InstanceHelper.getInstance().getIdentifier(row.getPropertiesMap().getValue()));
				if(inputChoiceOne == null){
					
				}else{
					Object choice = getChoice(row.getPropertiesMap().getValue());
					if(choice==null){
						
					}else{
						vLoggingMessageBuilder.addNamedParameters("choice id",InstanceHelper.getInstance().getIdentifier(choice));
						inputChoiceOne.getChoices().addOne(choice);	
						CollectionHelper.Instance<?> collection = getDestinationCollection();
						if(collection!=null){
							vLoggingMessageBuilder.addManyParameters("destination collection");
							vLoggingMessageBuilder.addNamedParameters("count before remove",CollectionHelper.getInstance().getSize(collection.getElements()));
							collection.removeOne(row.getPropertiesMap().getValue());
							vLoggingMessageBuilder.addNamedParameters("count after remove",CollectionHelper.getInstance().getSize(collection.getElements()));
							if(dataTable.getPropertiesMap().getMaster()!=null){
								vLoggingMessageBuilder.addNamedParameters("master id",InstanceHelper.getInstance().getIdentifier(dataTable.getPropertiesMap().getMaster()));
								Object object = dataTable.getPropertiesMap().getMaster();
								InstanceHelper.getInstance().computeChanges(object);
								Collection<String> updatedFieldNames = (Collection<String>) ((Command)row.getPropertiesMap().getRemoveCommandComponent()).getPropertiesMap().getUpdatedFieldNames();
								vLoggingMessageBuilder.addNamedParameters("updated field names",updatedFieldNames);
								
								new CollectionHelper.Iterator.Adapter.Default<String>((Collection<String>) updatedFieldNames){
									private static final long serialVersionUID = 1L;

									protected void __executeForEach__(String fieldName) {
										Control control = ((Form.Detail)dataTable.getPropertiesMap().getFormDetail()).getControlByFieldName(fieldName);
										Object v1 = control instanceof Input ? ((Input<?>)control).getValue() : ((OutputText)control).getPropertiesMap().getValue();
										control.read();
										Object v2 = control instanceof Input ? ((Input<?>)control).getValue() : ((OutputText)control).getPropertiesMap().getValue();
										vLoggingMessageBuilder.addNamedParameters(fieldName,v1+">"+v2);
									}
								}.execute();
								
								Collection<String> updatedColumnsFieldNames = (Collection<String>) ((Command)row.getPropertiesMap().getRemoveCommandComponent()).getPropertiesMap().getUpdatedFieldNames();
								vLoggingMessageBuilder.addNamedParameters("updated column field names",updatedColumnsFieldNames);
								new CollectionHelper.Iterator.Adapter.Default<String>(updatedColumnsFieldNames){
									private static final long serialVersionUID = 1L;

									protected void __executeForEach__(String columnFieldName) {
										dataTable.getColumn(columnFieldName).__setPropertyFooterPropertyValueBasedOnMaster__();
									}
								}.execute();
								
							}
						}
					}	
				}				
			}
			//all computing are done  we can now remove the row
			dataTable.removeOneRow(row);
		}		
	}
}
