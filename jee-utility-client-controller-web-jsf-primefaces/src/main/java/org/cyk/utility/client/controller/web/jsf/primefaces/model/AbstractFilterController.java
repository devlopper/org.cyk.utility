package org.cyk.utility.client.controller.web.jsf.primefaces.model;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInputChoiceOne;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Panel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractFilterController extends AbstractObject implements Serializable {

	protected Dialog dialog;
	protected CommandButton showDialogCommandButton;
	protected Map<String,Boolean> ignorables;	
	protected Redirector.Arguments onSelectRedirectorArguments;	
	protected CommandButton filterCommandButton;
	protected Layout layout;
	protected String layoutIdentifier = RandomHelper.getAlphabetic(4);
	protected RenderType renderType;
	protected Boolean isNotBuildable;
	protected Class<?> pageClass;
	protected Boolean isUsedForLoggedUser;
	protected Listener listener;
	
	public AbstractFilterController initialize() {
		return this;
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
		if(layout != null)
			((Panel)layout.getContainer()).setCollapsed(Boolean.TRUE);
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
	
	protected abstract void buildInputs();
	
	/* Build Input */
	
	protected void buildInputText(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return;
		if(!Boolean.TRUE.equals(isBuildable(fieldName)))
			return;
		String string = getInputTextInitialValue(fieldName);
		AbstractInput<?> input = buildInput(fieldName, string);
		FieldHelper.write(this, fieldName, input);
	}
	
	protected String getInputTextInitialValue(String fieldName) {
		String parameterName =  buildParameterName(fieldName);
		if(StringHelper.isBlank(parameterName)) {
			LogHelper.logSevere(String.format("Request parameter name has not been defined for field named <<%s>>", fieldName), getClass());
			return null;
		}
		return WebController.getInstance().getRequestParameter(parameterName);
	}
		
	protected void buildInputSelectOne(String fieldName,Class<?> klass) {
		if(StringHelper.isBlank(fieldName))
			return;
		if(!Boolean.TRUE.equals(isBuildable(fieldName)))
			return;
		Object instance = getInputSelectOneInitialValue(fieldName, klass);
		AbstractInput<?> input = buildInput(fieldName, instance);
		FieldHelper.write(this, fieldName, input);
	}
	
	protected Object getInputSelectOneInitialValue(String fieldName,Class<?> klass) {
		return WebController.getInstance().getRequestParameterEntityAsParentBySystemIdentifier(klass, null);
	}
	
	/**/
	
	protected abstract AbstractInput<?> buildInput(String fieldName,Object value);
	
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
				Collection<AbstractInputChoiceOne> inputsChoicesOne = CollectionHelper.filterByInstanceOf(AbstractInputChoiceOne.class, getInputs());
				if(CollectionHelper.isNotEmpty(inputsChoicesOne)) {
					for(AbstractInputChoiceOne input : inputsChoicesOne) {
						if(Boolean.TRUE.equals(isInputValueNotNull(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(input.getChoiceClass(), input)))
							onSelectRedirectorArguments.addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
					}
				}
				
				Collection<AutoComplete> autoCompletes = CollectionHelper.filterByInstanceOf(AutoComplete.class, getInputs());
				if(CollectionHelper.isNotEmpty(autoCompletes)) {
					for(AutoComplete input : autoCompletes) {
						if(Boolean.TRUE.equals(isInputValueNotNull(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(input.getEntityClass(), input)))
							onSelectRedirectorArguments.addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
					}
				}
				
				Collection<InputText> inputTexts = CollectionHelper.filterByInstanceOf(InputText.class, getInputs());
				if(CollectionHelper.isNotEmpty(inputTexts)) {
					for(InputText input : inputTexts) {
						if(Boolean.TRUE.equals(isInputValueNotBlank(input)) && Boolean.TRUE.equals(isSelectRedirectorArgumentsParameter(String.class, input)))
							onSelectRedirectorArguments.addParameters(Map.of(buildParameterName(input),CollectionHelper.listOf(Boolean.TRUE,buildParameterValue(input))));
					}
				}
				
				Redirector.getInstance().redirect(onSelectRedirectorArguments);		
				return super.__runExecuteFunction__(action);
			}
		});
	}
	
	protected Boolean isInputValueNotNull(AbstractInput<?> input) {
		return input != null && input.getValue() != null;
	}
	
	protected Boolean isInputValueNotBlank(AbstractInput<?> input) {
		return input != null && ValueHelper.isNotBlank(input.getValue());
	}
	
	protected Boolean isSelectRedirectorArgumentsParameter(Class<?> klass,AbstractInput<?> input) {
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
		if(input instanceof AbstractInputChoiceOne)
			return StringHelper.get(FieldHelper.readSystemIdentifier(input.getValue()));
		if(input instanceof AutoComplete)
			return StringHelper.get(FieldHelper.readSystemIdentifier(input.getValue()));
		if(input instanceof InputText)
			return (String)input.getValue();
		return null;
	}
	
	protected void buildLayout() {
		Collection<Map<Object,Object>> cellsMaps = buildLayoutCells();
		if(CollectionHelper.isEmpty(cellsMaps))
			return;
		layout = Layout.build(Layout.FIELD_IDENTIFIER,layoutIdentifier,Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps
				,Layout.FIELD_CONTAINER,Panel.build(Panel.FIELD_HEADER,"Filtre",Panel.FIELD_TOGGLEABLE,Boolean.TRUE));
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
		throw new RuntimeException(getClass().getSimpleName()+" as map not yet implemented");
	}
	
	/**/
	
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
}