package org.cyk.utility.client.controller.web.jsf.primefaces.model;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.session.SessionManager;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInputChoiceOne;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.Calendar;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Panel;
import org.omnifaces.util.Ajax;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractFilterController extends AbstractObject implements Serializable {

	private static final String AUTO_COMPLETE_SUFFIX = "AutoComplete";
	private static final String SELECT_ONE_SUFFIX = "SelectOne";
	private static final String TEXT_SUFFIX = "InputText";
	private static final String INITIAL_SUFFIX = "Initial";
	
	protected Map<String,String> inputsFieldsNamesIntialsFieldsNamesMap;
	
	protected Dialog dialog;
	protected CommandButton showDialogCommandButton;
	protected Map<String,Boolean> ignorables;
	protected Map<BooleanProperty,Map<String,Boolean>> booleanProperties;
	protected Map<String,SortOrder> sortOrders;
	protected Boolean redirectable;
	protected Redirector.Arguments onSelectRedirectorArguments;
	protected String parameterTabIdentifier;
	protected CommandButton filterCommandButton;
	protected Layout layout;
	protected String layoutIdentifier = RandomHelper.getAlphabetic(4);
	protected RenderType renderType;
	protected Boolean isNotBuildable;
	protected Class<?> pageClass;
	protected Object page;
	protected Boolean isUsedForLoggedUser;
	protected Listener listener;
	protected Boolean isLayoutContainerCollapsed = Boolean.TRUE;
	protected Boolean isInitialValue;
	protected AbstractCollection collection;
	
	protected Map<String,String> getInputsFieldsNamesIntialsFieldsNamesMap(Boolean createIfNull) {
		if(inputsFieldsNamesIntialsFieldsNamesMap == null && Boolean.TRUE.equals(createIfNull))
			inputsFieldsNamesIntialsFieldsNamesMap = new HashMap<>();
		return inputsFieldsNamesIntialsFieldsNamesMap;
	}
	
	public void addInputTextByBaseFieldName(String fieldName) {
		getInputsFieldsNamesIntialsFieldsNamesMap(Boolean.TRUE).put(fieldName+TEXT_SUFFIX, fieldName+INITIAL_SUFFIX);
	}
	
	public void addInputSelectOneByBaseFieldName(String fieldName) {
		getInputsFieldsNamesIntialsFieldsNamesMap(Boolean.TRUE).put(fieldName+SELECT_ONE_SUFFIX, fieldName+INITIAL_SUFFIX);
	}
	
	public void addInputAutoCompleteByBaseFieldName(String fieldName) {
		getInputsFieldsNamesIntialsFieldsNamesMap(Boolean.TRUE).put(fieldName+AUTO_COMPLETE_SUFFIX, fieldName+INITIAL_SUFFIX);
	}
	
	protected Boolean isFieldNameOfInputText(String fieldName) {
		return fieldName.endsWith(TEXT_SUFFIX);
	}
	
	protected Boolean isFieldNameOfInputSelectOne(String fieldName) {
		return fieldName.endsWith(SELECT_ONE_SUFFIX);
	}
	
	protected Boolean isFieldNameOfInputAutoComplete(String fieldName) {
		return fieldName.endsWith(AUTO_COMPLETE_SUFFIX);
	}
	
	protected Boolean isInputText(String fieldName) {
		return isFieldNameOfInputText(fieldName) && inputsFieldsNamesIntialsFieldsNamesMap != null && inputsFieldsNamesIntialsFieldsNamesMap.containsKey(fieldName);
	}
	
	protected Boolean isInputSelectOne(String fieldName) {
		return isFieldNameOfInputSelectOne(fieldName) && inputsFieldsNamesIntialsFieldsNamesMap != null && inputsFieldsNamesIntialsFieldsNamesMap.containsKey(fieldName);
	}
	
	protected Boolean isInputAutoComplete(String fieldName) {
		return isFieldNameOfInputAutoComplete(fieldName) && inputsFieldsNamesIntialsFieldsNamesMap != null && inputsFieldsNamesIntialsFieldsNamesMap.containsKey(fieldName);
	}
	
	protected Object getControllerByInputFieldName(String fieldName) {
		Object controller = __getControllerByInputFieldName__(fieldName);
		if(controller == null)
			throw new RuntimeException(String.format("Controller of field %s is required", fieldName));
		return controller;
	}
	
	protected Object __getControllerByInputFieldName__(String fieldName) {
		return null;
	}
	
	protected Object getByIdentifier(Object controller,String fieldName,String identifier) {
		throw new RuntimeException(String.format("get %s by identifier %s using controller %s not yet implemented", fieldName,identifier,controller));
	}
	
	public AbstractFilterController initialize() {
		if(redirectable == null) {
			String redirectableAsString = WebController.getInstance().getRequestParameter(REQUEST_PARAMETER_NAME_REDIRECTABLE);
			if(StringHelper.isNotBlank(redirectableAsString))
				redirectable = ValueHelper.convertToBoolean(redirectableAsString);
			if(redirectable == null)
				redirectable = Boolean.TRUE;
		}
		
		if(isInitialValue == null && Boolean.TRUE.equals(redirectable))
			isInitialValue = Boolean.TRUE;
		
		if(isInitialValue == null && !Boolean.TRUE.equals(redirectable))
			isInitialValue = Boolean.FALSE;
		
		__addInputsByBasedOnFieldsNames__();
		if(inputsFieldsNamesIntialsFieldsNamesMap != null) {
			inputsFieldsNamesIntialsFieldsNamesMap.entrySet().forEach(entry -> {
				Object initial = FieldHelper.read(this, entry.getValue());
				if(initial == null) {
					Class<?> inputValueType = getInputValueTypeByFieldName(entry.getKey());
					if(ClassHelper.isBelongsToJavaPackages(inputValueType)) {
						Object value = WebController.getInstance().getRequestParameter(buildParameterName(entry.getKey()));
						if(Boolean.class.equals(inputValueType))
							value = ValueConverter.getInstance().convertToBoolean(value);
						FieldHelper.write(this, entry.getValue(), value);
					}else
						FieldHelper.write(this, entry.getValue(), getByIdentifier(getControllerByInputFieldName(entry.getKey()), entry.getValue(),WebController.getInstance().getRequestParameter(buildParameterName(entry.getKey()))));
				}
			});
		}
		__initialize__();
		return this;
	}
	
	protected Class<?> getInputValueTypeByFieldName(String fieldName) {
		return (Class<?>) FieldHelper.getType(getClass(), inputsFieldsNamesIntialsFieldsNamesMap.get(fieldName));
	}
	
	public void __addInputsByBasedOnFieldsNames__() {}
	
	public void __initialize__() {}
	
	public Map<String,SortOrder> getSortOrders(Boolean injectIfNull) {
		if(sortOrders == null && Boolean.TRUE.equals(injectIfNull))
			sortOrders = new LinkedHashMap<>();
		return sortOrders;
	}
	
	public AbstractFilterController order(String fieldName,SortOrder sortOrder) {
		if(StringHelper.isBlank(fieldName) || sortOrder == null)
			return this;
		getSortOrders(Boolean.TRUE).put(fieldName, sortOrder);
		return this;
	}
	
	public Map<BooleanProperty,Map<String,Boolean>> getBooleanProperties(Boolean injectIfNull) {
		if(booleanProperties == null && Boolean.TRUE.equals(injectIfNull))
			booleanProperties = new HashMap<>();
		return booleanProperties;
	}
	
	public AbstractFilterController setBooleanProperty(BooleanProperty property,Collection<String> fieldNames) {
		if(property == null || CollectionHelper.isEmpty(fieldNames))
			return this;
		Map<String,Boolean> map = getBooleanProperties(Boolean.TRUE).get(property);
		if(map == null)
			booleanProperties.put(property, map = new HashMap<>());
		for(String fieldName : fieldNames)
			map.put(fieldName, Boolean.TRUE);
		return this;
	}
	
	public AbstractFilterController setBooleanProperty(BooleanProperty property,String...fieldNames) {
		if(ArrayHelper.isEmpty(fieldNames))
			return this;
		return setBooleanProperty(property,CollectionHelper.listOf(fieldNames));
	}
	
	public AbstractFilterController setReadOnlyByFieldsNames(Collection<String> fieldsNames) {
		if(CollectionHelper.isEmpty(fieldsNames))
			return this;
		return setBooleanProperty(BooleanProperty.READ_ONLY, fieldsNames);
	}
	
	public AbstractFilterController setReadOnlyByFieldsNames(String...fieldsNames) {
		if(ArrayHelper.isEmpty(fieldsNames))
			return this;
		return setReadOnlyByFieldsNames(CollectionHelper.listOf(fieldsNames));
	}
	
	public Boolean isBooleanPropertyTrue(BooleanProperty property,String fieldName) {
		if(booleanProperties == null || booleanProperties.isEmpty() || StringHelper.isBlank(fieldName))
			return Boolean.FALSE;
		Map<String,Boolean> map = booleanProperties.get(property);
		if(map == null || map.isEmpty())
			return Boolean.FALSE;
		return map.get(fieldName);
	}
	
	public Map<String,Boolean> getIgnorables(Boolean injectIfNull) {
		if(ignorables == null && Boolean.TRUE.equals(injectIfNull))
			ignorables = new HashMap<>();
		return ignorables;
	}
	
	public AbstractFilterController ignore(Collection<String> fieldNames) {
		if(CollectionHelper.isEmpty(fieldNames))
			return this;
		for(String fieldName : fieldNames)
			getIgnorables(Boolean.TRUE).put(fieldName, Boolean.TRUE);
		return this;
	}
	
	public AbstractFilterController ignore(String...fieldNames) {
		if(ArrayHelper.isEmpty(fieldNames))
			return this;
		return ignore(CollectionHelper.listOf(fieldNames));
	}
	
	public Boolean isIgnored(String fieldName) {
		if(ignorables == null || ignorables.isEmpty())
			return Boolean.FALSE;
		if(StringHelper.isBlank(fieldName))
			return null;		
		return ignorables.containsKey(fieldName);
	}
	
	protected Boolean isBuildable(String fieldName) {
		return ignorables == null || !ignorables.containsKey(fieldName) || !Boolean.TRUE.equals(ignorables.get(fieldName));
	}
	
	public AbstractFilterController build() {
		if(Boolean.TRUE.equals(isNotBuildable))
			return this;
		buildDialog();
		buildShowDialogCommandButton();		
		buildInputs();
		buildFilterCommandButton();
		buildLayout();
		return this;
	}
	
	public Redirector.Arguments getOnSelectRedirectorArguments(Boolean injectIfNull) {
		if(onSelectRedirectorArguments == null && Boolean.TRUE.equals(injectIfNull))
			onSelectRedirectorArguments = new Redirector.Arguments();
		return onSelectRedirectorArguments;
	}
	
	private void buildDialog() {
		dialog = Dialog.build(Dialog.FIELD_HEADER,"Filtre",Dialog.FIELD_MODAL,Boolean.TRUE
				,Dialog.ConfiguratorImpl.FIELD_COMMAND_BUTTONS_BUILDABLE,Boolean.FALSE);
		dialog.addStyleClasses("cyk-min-width-90-percent");
	}
	
	private void buildShowDialogCommandButton() {
		showDialogCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Filtrer",CommandButton.FIELD_ICON,"fa fa-filter"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.SHOW_DIALOG,CommandButton.FIELD___DIALOG__,dialog);		
	}
	
	/* Build Input */
	
	protected void buildInputs() {
		if(inputsFieldsNamesIntialsFieldsNamesMap != null)
			inputsFieldsNamesIntialsFieldsNamesMap.entrySet().forEach(entry -> {
				if(isInputSelectOne(entry.getKey()) || isInputAutoComplete(entry.getKey()))
					buildInputSelectOne(entry.getKey(),(Class<?>)FieldHelper.getType(getClass(), entry.getValue()));
				else if(isInputText(entry.getKey()))
					buildInputText(entry.getKey());
			});
		__buildInputs__();
		enableValueChangeListeners();
		selectByValueSystemIdentifier();
	}
	
	protected void __buildInputs__() {}
	
	protected void enableValueChangeListeners() {
		
	}
	
	protected void selectByValueSystemIdentifier() {
		
	}
	
	protected void buildInputText(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return;
		if(!Boolean.TRUE.equals(isBuildable(fieldName)))
			return;
		String string = getInputTextInitialValue(fieldName);
		AbstractInput<?> input = buildInput(fieldName, string);
		processInput(fieldName, string, input);
		FieldHelper.write(this, fieldName, input);
	}
	
	protected String getInputTextInitialValue(String fieldName) {
		if(isInputText(fieldName))
			return (String) FieldHelper.read(this, inputsFieldsNamesIntialsFieldsNamesMap.get(fieldName));
		String parameterName =  buildParameterName(fieldName);
		if(StringHelper.isBlank(parameterName)) {
			LogHelper.logSevere(String.format("Request parameter name has not been defined for field named <<%s>>", fieldName), getClass());
			return null;
		}
		return WebController.getInstance().getRequestParameter(parameterName);
	}
	
	protected void buildInputCalendar(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return;
		if(!Boolean.TRUE.equals(isBuildable(fieldName)))
			return;
		Date date = getInputCalendarInitialValue(fieldName);
		AbstractInput<?> input = buildInput(fieldName, date);
		processInput(fieldName, date, input);
		FieldHelper.write(this, fieldName, input);
	}
	
	protected Date getInputCalendarInitialValue(String fieldName) {
		String parameterName =  buildParameterName(fieldName);
		if(StringHelper.isBlank(parameterName)) {
			LogHelper.logSevere(String.format("Request parameter name has not been defined for field named <<%s>>", fieldName), getClass());
			return null;
		}
		Long timestamp = ValueConverter.getInstance().convertToLong(WebController.getInstance().getRequestParameter(parameterName));
		if(timestamp == null)
			return null;
		return TimeHelper.getDateFromMilliseconds(timestamp);
	}
		
	protected void buildInputSelectOne(String fieldName,Class<?> klass) {
		if(StringHelper.isBlank(fieldName))
			return;
		if(!Boolean.TRUE.equals(isBuildable(fieldName)))
			return;
		Object instance = getInputSelectOneInitialValue(fieldName, klass);
		AbstractInput<?> input = buildInput(fieldName, instance);
		processInput(fieldName, instance, input);
		FieldHelper.write(this, fieldName, input);
	}
	
	protected Object getInputSelectOneInitialValue(String fieldName,Class<?> klass) {
		if(isInputSelectOne(fieldName) || isInputAutoComplete(fieldName))
			return FieldHelper.read(this, inputsFieldsNamesIntialsFieldsNamesMap.get(fieldName));
		return WebController.getInstance().getRequestParameterEntityAsParentBySystemIdentifier(klass, null);
	}
	
	/**/
	
	protected abstract AbstractInput<?> buildInput(String fieldName,Object value);
	
	protected void processInput(String fieldName,Object value,AbstractInput<?> input) {
		if(Boolean.TRUE.equals(isBooleanPropertyTrue(BooleanProperty.READ_ONLY, fieldName)))
			input.setReadOnly(Boolean.TRUE);
	}
	
	protected List<AbstractInput<?>> getInputs() {
		Collection<Field> staticsFields = FieldHelper.filter(getClass(), "^FIELD_", null);
		if(CollectionHelper.isEmpty(staticsFields))
			return null;
		List<AbstractInput<?>> values = null;
		for(Field staticField : staticsFields) {
			String fieldName = (String)FieldHelper.readStatic(staticField);
			if(StringHelper.isBlank(fieldName))
				continue;
			Field field = FieldHelper.getByName(getClass(), fieldName);
			if(field == null)
				continue;
			if(ClassHelper.isInstanceOf(field.getType(), AbstractInput.class)) {
				AbstractInput<?> fieldValue = (AbstractInput<?>) FieldHelper.read(this,fieldName);
				if(fieldValue == null)
					continue;
				if(values == null)
					values = new ArrayList<>();
				values.add(fieldValue);
			}
			
		}
		return values;
	}
	
	protected void buildFilterCommandButton() {
		filterCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Filtrer",CommandButton.FIELD_ICON,"fa fa-filter"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION,CommandButton.FIELD_LISTENER
				,new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				if(redirectable == null || redirectable) {
					Collection<AbstractInputChoiceOne> inputsChoicesOne = CollectionHelper.filterByInstanceOf(AbstractInputChoiceOne.class, getInputs());
					if(CollectionHelper.isNotEmpty(inputsChoicesOne)) {
						for(AbstractInputChoiceOne input : inputsChoicesOne) {
							if(Boolean.TRUE.equals(isInputValueNotNull(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(input.getChoiceClass(), input)))
								getOnSelectRedirectorArguments(Boolean.TRUE).addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
						}
					}
					
					Collection<AutoComplete> autoCompletes = CollectionHelper.filterByInstanceOf(AutoComplete.class, getInputs());
					if(CollectionHelper.isNotEmpty(autoCompletes)) {
						for(AutoComplete input : autoCompletes) {
							if(Boolean.TRUE.equals(isInputValueNotNull(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(input.getEntityClass(), input)))
								getOnSelectRedirectorArguments(Boolean.TRUE).addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
						}
					}
					
					Collection<InputText> inputTexts = CollectionHelper.filterByInstanceOf(InputText.class, getInputs());
					if(CollectionHelper.isNotEmpty(inputTexts)) {
						for(InputText input : inputTexts) {
							if(Boolean.TRUE.equals(isInputValueNotBlank(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(String.class, input)))
								getOnSelectRedirectorArguments(Boolean.TRUE).addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
						}
					}
					
					Collection<Calendar> calendars = CollectionHelper.filterByInstanceOf(Calendar.class, getInputs());
					if(CollectionHelper.isNotEmpty(calendars)) {
						for(Calendar input : calendars) {
							if(Boolean.TRUE.equals(isInputValueNotBlank(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(Date.class, input)))
								getOnSelectRedirectorArguments(Boolean.TRUE).addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
						}
					}
					Redirector.getInstance().redirect(onSelectRedirectorArguments);
				}else {
					Ajax.oncomplete(String.format("PF('%s').filter();",AbstractFilterController.this.collection.getWidgetVar()));
				}
				return super.__runExecuteFunction__(action);
			}
		});
		filterCommandButton.set__isDialogClosableAutomatically__(redirectable == null || redirectable);
	}
	
	protected Boolean isInputValueNotNull(AbstractInput<?> input) {
		return input != null && input.getValue() != null;
	}
	
	protected Boolean isInputValueNotBlank(AbstractInput<?> input) {
		return input != null && ValueHelper.isNotBlank(input.getValue());
	}
	
	protected Boolean isSelectRedirectorArgumentsParameter(Class<?> klass,AbstractInput<?> input) {
		if(input == null || input.getValue() == null)
			return null;
		return Boolean.TRUE;
	}
	
	protected String buildParameterName(String fieldName,AbstractInput<?> input) {
		if(input instanceof AbstractInputChoiceOne)
			return ParameterName.stringify( ((AbstractInputChoiceOne)input).getChoiceClass());
		if(input instanceof AutoComplete)
			return ParameterName.stringify( ((AutoComplete)input).getEntityClass());
		return null;
	}
	
	protected String buildParameterName(String fieldName) {
		return buildParameterName(fieldName, null);
	}
	
	protected String buildParameterName(AbstractInput<?> input) {
		return buildParameterName(null, input);
	}
	
	protected String buildParameterValue(AbstractInput<?> input) {
		if(input == null || input.getValue() == null)
			return null;
		if(input instanceof AbstractInputChoiceOne) {
			if(input.getValue() instanceof Boolean)
				return input.getValue().toString();
			return StringHelper.get(FieldHelper.readSystemIdentifier(input.getValue()));
		}
		if(input instanceof AutoComplete)
			return StringHelper.get(FieldHelper.readSystemIdentifier(input.getValue()));
		if(input instanceof InputText)
			return (String)input.getValue();
		if(input instanceof Calendar)
			return StringHelper.get(((Date)input.getValue()).getTime());
		return null;
	}
	
	protected void buildLayout() {
		Collection<Map<Object,Object>> cellsMaps = buildLayoutCells();
		if(CollectionHelper.isEmpty(cellsMaps))
			return;
		layout = Layout.build(Layout.FIELD_IDENTIFIER,layoutIdentifier,Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps
				,Layout.FIELD_CONTAINER,Panel.build(Panel.FIELD_HEADER,"Filtre",Panel.FIELD_TOGGLEABLE,Boolean.TRUE,Panel.FIELD_COLLAPSED,isLayoutContainerCollapsed == null ? Boolean.TRUE : isLayoutContainerCollapsed));
	}
	
	protected Collection<Map<Object,Object>> buildLayoutCells() {
		Collection<Map<Object,Object>> cellsMaps = new ArrayList<>();
		List<AbstractInput<?>> inputs = getInputs();
		if(CollectionHelper.isNotEmpty(inputs)) {
			Integer filterCommandButtonWidth = 1;
			for(Integer index = 0 ; index < inputs.size() ; index = index + 1) {
				AbstractInput<?> input = inputs.get(index);
				if(input == null)
					continue;
				cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input.getOutputLabel(),Cell.FIELD_WIDTH,3));
				cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input,Cell.FIELD_WIDTH,9 - (index == inputs.size() - 1 ? filterCommandButtonWidth : 0)));				
			}
			cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,filterCommandButton,Cell.FIELD_WIDTH,filterCommandButtonWidth));
		}
		return cellsMaps;
	}
	
	public Map<String, List<String>> asMap() {
		Map<String, List<String>> map = new HashMap<>();
		if(inputsFieldsNamesIntialsFieldsNamesMap != null)
			inputsFieldsNamesIntialsFieldsNamesMap.entrySet().forEach(entry -> {
				Object value = FieldHelper.read(this, entry.getValue());
				if(value == null)
					return;
				if(!Boolean.TRUE.equals(isAddableIntoMap(entry.getKey())))
					return;
				String parameterName = buildParameterName(entry.getKey());
				if(ClassHelper.isBelongsToJavaPackages(value.getClass()))
					map.put(parameterName, List.of(value.toString()));
				else if(isInputSelectOne(entry.getKey()) || isInputAutoComplete(entry.getKey()))
					map.put(parameterName, List.of((String)FieldHelper.readSystemIdentifier(value)));
				//else if(isInputText(entry.getKey()))
				//	map.put(parameterName, List.of(value.toString()));
			});
		__asMap__(map);
		return map;
	}
	
	protected Boolean isAddableIntoMap(String fieldName) {
		return Boolean.TRUE;
	}
	
	public void __asMap__(Map<String, List<String>> map) {}
	
	protected void addParameter(Map<String, List<String>> map,String name,Object value) {
		if(StringHelper.isBlank(name) || value == null)
			return;
		String string = null;
		if(!(value instanceof String)) {
			if(value.getClass().getPackageName().contains("java."))
				string = value.toString();
			else
				string = (String) FieldHelper.readSystemIdentifier(value);
		}
		if(StringHelper.isBlank(string)) {
			LogHelper.logWarning(String.format("cannot compute parameter %s from %s", name,value), getClass());
			return;
		}
		map.put(name, List.of(string));
	}
	
	/**/
	
	public static enum BooleanProperty {
		IGNORABLE
		,READ_ONLY
		;
	}
	
	public static enum RenderType {
		INLINE
		,DIALOG
		,NONE
		;
	}

	/**/
	
	public static interface Listener {
		void listenBeforeBuildLayoutCells(AbstractFilterController filterController,Collection<Map<Object,Object>> cellsMaps);
		void listenAfterBuildLayoutCells(AbstractFilterController filterController,Collection<Map<Object,Object>> cellsMaps);
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	public static <FILTER_CONTROLLER extends AbstractFilterController> FILTER_CONTROLLER getFromSessionOrInstantiateIfNull(Class<FILTER_CONTROLLER> filterControllerClass,FILTER_CONTROLLER filterController) {
		if(filterController != null)
			return filterController;
		String identifier = WebController.getInstance().getRequestParameter(SESSION_IDENTIFIER_REQUEST_PARAMETER_NAME);
		if(StringHelper.isNotBlank(identifier)) {
			filterController = (FILTER_CONTROLLER) SessionManager.getInstance().readAttribute(identifier);
			if(filterController != null)
				SessionManager.getInstance().writeAttribute(identifier, null);// To free up object
		}
		if(filterController == null)
			filterController = ClassHelper.instanciate(filterControllerClass); 
		return filterController;
	}
	
	public static final String SESSION_IDENTIFIER_REQUEST_PARAMETER_NAME = "fc_sid";
	public static final String REQUEST_PARAMETER_NAME_REDIRECTABLE = "fc_rpn_redirectable";
}