package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.CascadeStyleSheetHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.JavaServerFacesHelper;
import org.cyk.utility.common.userinterface.collection.Column.CellValueSource;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.Watermark;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCombo;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputText;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class DataTable extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private FormDetail form;
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
	
	public CollectionHelper.Instance<?> getPropertyRowsCollectionInstance(Boolean createIfNull){
		CollectionHelper.Instance<?> result = (CollectionHelper.Instance<?>) getPropertiesMap().getRowsCollectionInstance();
		if(result == null && Boolean.TRUE.equals(createIfNull)){
			result = new CollectionHelper.Instance<>();
			getPropertiesMap().setRowsCollectionInstance(result);
		}
		return result;
	}
	
	public CollectionHelper.Instance<?> getPropertyRowsCollectionInstance(){
		return getPropertyRowsCollectionInstance(Boolean.TRUE);
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
		
		for(Column index : Columns.getPropertyValue(this)){
			if(Boolean.TRUE.equals(index.getPropertiesMap().getIsFooterShowable()))
				index.__setPropertyFooterPropertyValueBasedOnMaster__();
		}
		
		if(Boolean.TRUE.equals(onPrepareCallLoad)){
			load(); //can be trigger by callback to enabled fast rendering of table structure	
		}
		
		logTrace("columns({},{})", CollectionHelper.getInstance().getSize(getColumnsFieldNames()),getColumnsFieldNames());
		
		return this;
	}
	
	protected void addMenu(){
		addMenu(this);
	}
	
	public String getChoiceValueClassMasterFieldName(){
		/*
		String fieldName = (String) getPropertiesMap().getMasterFieldName();
		if(StringHelper.getInstance().isBlank(fieldName)){
			if(getPropertiesMap().getChoiceValueClass()!=null){
				fieldName = ClassHelper.getInstance().getVariableName((Class<?>)getPropertiesMap().getChoiceValueClass());
			}
		}
		
		
		String fieldName = null;		
		if(getPropertiesMap().getChoiceValueClass()!=null){
			fieldName = ClassHelper.getInstance().getVariableName((Class<?>)getPropertiesMap().getChoiceValueClass());
			if(StringHelper.getInstance().isBlank(fieldName)){
				fieldName = (String) getPropertiesMap().getMasterFieldName();
			}
		}
		
		return fieldName;
		*/
		
		String fieldName = (String) getPropertiesMap().getChoiceValueClassMasterFieldName();		
		if(StringHelper.getInstance().isBlank(fieldName)){
			fieldName = ClassHelper.getInstance().getVariableName((Class<?>)getPropertiesMap().getChoiceValueClass());
			if(StringHelper.getInstance().isBlank(fieldName)){
				if(getPropertiesMap().getChoiceValueClass()!=null){
					fieldName = (String) getPropertiesMap().getMasterFieldName();
				}
			}
		}
		
		return fieldName;
	}
	
	public static void addFirstColumns(Component component){
		Column orderNumberColumn = null;
		if(Boolean.TRUE.equals(component.getPropertiesMap().getOnPrepareAddColumnOrderNumber())){
			orderNumberColumn =Columns.addColumn(component,"userinterface.column.order.number", FIELD___ORDER_NUMBER__);
			orderNumberColumn.setCellValueSource(CellValueSource.ROW).set__orderNumber__(Long.MIN_VALUE);	
		}
		
		if(component instanceof DataTable){
			String fieldName = ((DataTable)component).getChoiceValueClassMasterFieldName();
			if(StringHelper.getInstance().isNotBlank(fieldName)){
				Column choiceValueColumn = ((DataTable)component).addColumnByFieldName(fieldName);
				if(orderNumberColumn!=null)
					choiceValueColumn.set__orderNumber__(orderNumberColumn.get__orderNumber__()+1);
				choiceValueColumn.setCellValueType(Cell.ValueType.TEXT);	
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
			inputChoiceOneCombo.getChoices().setIsSourceDisjoint(Boolean.TRUE.equals(InstanceHelper.getInstance().getIfNotNullElseDefault(getPropertiesMap()
					.getChoicesIsSourceDisjoint(),Boolean.TRUE)));
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
				String choiceValueClassMasterFieldName = getChoiceValueClassMasterFieldName();
				Column choiceColumn = StringHelper.getInstance().isBlank(choiceValueClassMasterFieldName) ? null : getColumn(choiceValueClassMasterFieldName);
				outputText.getPropertiesMap().setValue(choiceColumn == null ? StringHelper.getInstance().getClazz(choiceValueClass) : choiceColumn.getPropertiesMap()
						.get(Properties.HEADER, OutputText.class).getPropertiesMap().getValue());
				
				Boolean addCoices = (Boolean) getPropertiesMap().getIsAutomaticallyAddChoiceValues();
				if(addCoices == null)
					addCoices = Boolean.TRUE;
				if(addCoices && ClassHelper.getInstance().isIdentified(choiceValueClass)){
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
		Collection<?> instances = null;
		if(Boolean.TRUE.equals(getPropertiesMap().getIsInstancesLoadableFromCollection())){
			instances = getFormMasterObjectActionOnClassCollectionInstance().getElements();
		}else{
			if(getPropertiesMap().getActionOnClass()!=null){
				Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
				
				if(getPropertiesMap().getMaster()==null)
					instances = InstanceHelper.getInstance().get((Class<?>) getPropertiesMap().getActionOnClass());
				else{
					System.out.println("DataTable.load() LOAD FROM FILTER");
					FilterHelper.Filter<Object> filter = (FilterHelper.Filter<Object>) ClassHelper.getInstance().instanciateOne(FilterHelper.Filter.ClassLocator.getInstance()
							.locate(actionOnClass));
					filter.addMaster(getPropertiesMap().getMaster());
					instances = InstanceHelper.getInstance().get((Class<Object>) getPropertiesMap().getActionOnClass(),filter, new DataReadConfiguration(null, null));
					if(Constant.Action.isCreateOrUpdate((Constant.Action) getPropertiesMap().getAction())){
						getFormMasterObjectActionOnClassCollectionInstance().addMany(instances);
					}
				}
			}	
		}
		addManyRow(instances);
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
	
	/*
	public Column addColumnByFieldName(String fieldName){
		String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
		Column column = addColumn(labelStringIdentifier, fieldName);
		
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(actionOnClass != null){
			java.lang.reflect.Field field = FieldHelper.getInstance().get(actionOnClass, fieldName);
			if(field == null){
				column._setLabelPropertyValue("??"+actionOnClass.getSimpleName()+"."+fieldName+"??");
			}else
				column._setLabelPropertyValue(StringHelper.getInstance().getField(field));
		}
		
		return column;
	}
	*/
	
	public Column addColumnByFieldName(String fieldName){
		String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
		Column column = Columns.addColumn(this, labelStringIdentifier, fieldName);
		
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(actionOnClass != null){
			java.lang.reflect.Field field = FieldHelper.getInstance().get(actionOnClass, fieldName);
			if(field == null){
				column._setLabelPropertyValue("??"+actionOnClass.getSimpleName()+"."+fieldName+"??");
			}else
				column._setLabelPropertyValue(StringHelper.getInstance().getField(field));
		}
		/*
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.setPropertyField(FieldHelper.getInstance().get(MyClass.class, "code01"));
		builder.getPropertiesMap().setActionOnClass(MyClass.class);
		Column column = builder.execute();
		*/
		return column;
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
						if(cell.getInput()!=null)
							inputs.add(cell.getInput());
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
	
	public CollectionHelper.Instance<?> getFormMasterObjectActionOnClassCollectionInstance(){
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		loggingMessageBuilder.addManyParameters("get form master object action on class collection instance");
		CollectionHelper.Instance<?> collection = (CollectionHelper.Instance<?>) getPropertiesMap().getFormMasterObjectActionOnClassCollectionInstance();
		if(collection == null){
			if(getPropertiesMap().getFormMasterObjectActionOnClassCollectionInstanceFieldName()!=null)
				collection = (CollectionHelper.Instance<?>) FieldHelper.getInstance().read(getPropertiesMap().getMaster(), (String)getPropertiesMap().getFormMasterObjectActionOnClassCollectionInstanceFieldName());
			if(collection == null){
				if(getPropertiesMap().getActionOnClass()!=null){
					String[] names = new String[]{ClassHelper.getInstance().getVariableName((Class<?>) getPropertiesMap().getActionOnClass(),Boolean.TRUE),"items"};
					loggingMessageBuilder.addNamedParameters("field names",StringHelper.getInstance().concatenate(names,Constant.CHARACTER_SPACE));
					java.lang.reflect.Field field = FieldHelper.getInstance().getByFirstNotNull(getPropertiesMap().getMaster().getClass(),names);
					if(field != null)
						collection = (CollectionHelper.Instance<?>) FieldHelper.getInstance().read(getPropertiesMap().getMaster(), field);
				}
				
				if(collection == null && getPropertiesMap().getFormMasterObjectActionOnClassCollectionInstanceFieldName()!=null){
					Class<?> fieldType = FieldHelper.getInstance().getType(getPropertiesMap().getMaster().getClass(), (String)getPropertiesMap().getFormMasterObjectActionOnClassCollectionInstanceFieldName());
					collection = (CollectionHelper.Instance<?>) ClassHelper.getInstance().instanciateOne(fieldType);
					FieldHelper.getInstance().set(getPropertiesMap().getMaster(), collection, (String)getPropertiesMap().getFormMasterObjectActionOnClassCollectionInstanceFieldName());
				}
					
			}
		}
		loggingMessageBuilder.addNamedParameters("collection instance is not null",collection!=null);
		logTrace(loggingMessageBuilder);
		return collection;
	}
	
	/**/
	
	/**/
	
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
	
	/**/
	
}
