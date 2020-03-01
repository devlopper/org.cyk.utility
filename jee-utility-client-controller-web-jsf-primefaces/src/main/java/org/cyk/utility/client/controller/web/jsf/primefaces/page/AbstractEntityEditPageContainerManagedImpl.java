package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationPhrase;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputNumber;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityEditPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Action action;
	protected Class<ENTITY> entityClass;
	protected ENTITY entity;
	protected ControllerEntity<ENTITY> controllerEntity;
	
	protected Layout layout;
	protected Collection<String> entityFieldsNames;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = __getEntityClass__();
		controllerEntity = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(entityClass);
		action = __getActionIdentifier__(Faces.getRequestParameter(ParameterName.ACTION_IDENTIFIER.getValue()));
		entity = __getEntity__(Faces.getRequestParameter(ParameterName.ENTITY_IDENTIFIER.getValue()));
		entityFieldsNames = __getEntityFieldsNames__();
		__buildLayout__();
	}
	
	public void record() {
		if(Action.CREATE.equals(action))
			controllerEntity.create(entity);
		else if(Action.UPDATE.equals(action))			 
			controllerEntity.update(entity,new Properties().setFields(StringHelper.concatenate(entityFieldsNames, ",")));
	}
	
	@Override
	protected String __processWindowDialogOkCommandableGetUrl__(WindowBuilder window,CommandableBuilder commandable) {
		return UniformResourceIdentifierHelper.build(__getRequest__(), SystemActionList.class, null, entityClass, null, null, null, null);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		InternationalizationPhrase internationalizationPhrase = new InternationalizationPhrase().setKase(Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER);
		internationalizationPhrase.addNoun(action.name()).addString("of").addString(entityClass);
		InternationalizationHelper.processPhrases(internationalizationPhrase);
		return internationalizationPhrase.getValue();
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__() {
		return (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	protected Action __getActionIdentifier__(String actionIdentifier) {
		if(StringHelper.isEmpty(actionIdentifier))
			return Action.CREATE;
		return Action.getByNameCaseInsensitive(actionIdentifier);
	}
	
	protected ENTITY __getEntity__(String entityIdentifier) {
		if(Action.CREATE.equals(action))
			return ClassHelper.instanciate(entityClass);
		return controllerEntity.readBySystemIdentifier(entityIdentifier);
	}
	
	protected void __buildLayout__() {
		Object[] arguments = __getLayoutArguments__();
		layout = Layout.build(arguments);
	}
	
	protected Object[] __getLayoutArguments__() {
		Collection<AbstractInput<?>> inputs = __buildInputs__();		
		Collection<Map<Object,Object>> cells = __getLayoutCellsArgumentsMaps__(inputs);
		if(CollectionHelper.isEmpty(cells))
			return null;
		return new Object[] {
				Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,2
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(3),1,new Cell().setWidth(9))
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cells
		};
	}
	
	protected Map<Object,Object> __getLayoutCellSubmitCommandControlArguments__() {
		return MapHelper.instantiate(CommandButton.ConfiguratorImpl.FIELD_OBJECT,this,CommandButton.ConfiguratorImpl.FIELD_METHOD_NAME,METHOD_NAME);
	}
	
	protected Map<Object,Object> __getLayoutCellSubmitCommandArguments__() {
		return MapHelper.instantiate(Cell.ConfiguratorImpl.FIELD_CONTROL_COMMAND_BUTTON_ARGUMENTS,__getLayoutCellSubmitCommandControlArguments__());
	}
	
	protected abstract Collection<String> __getEntityFieldsNames__();
	
	protected Collection<Map<Object,Object>> __getLayoutCellsArgumentsMaps__(Collection<AbstractInput<?>> inputs) {
		if(CollectionHelper.isEmpty(inputs))
			return null;
		Collection<Map<Object,Object>> cells = new ArrayList<>();
		inputs.forEach(new Consumer<AbstractInput<?>>() {
			@Override
			public void accept(AbstractInput<?> input) {
				cells.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input.getOutputLabel()));
				cells.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input));
			}			
		});
		Map<Object,Object> submitCommandArguments = __getLayoutCellSubmitCommandArguments__();
		if(MapHelper.isNotEmpty(submitCommandArguments))
			cells.add(submitCommandArguments);
		return cells;
	}

	protected Collection<AbstractInput<?>> __buildInputs__() {
		if(CollectionHelper.isEmpty(entityFieldsNames))
			return null;		
		Collection<AbstractInput<?>> inputs = entityFieldsNames.stream().map(fieldName -> __buildInput__(fieldName)).filter(input -> input != null).collect(Collectors.toList());
		return inputs;
	}
	
	protected AbstractInput<?> __buildInput__(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return null;
		Field field = FieldHelper.getByName(entityClass, fieldName);
		Class<?> fieldType = (Class<?>) FieldHelper.getType(field, entityClass);
		if(String.class.equals(fieldType))
			return InputText.build(InputText.FIELD_OBJECT,entity,InputText.FIELD_FIELD,field);
		if(ClassHelper.isInstanceOfNumber(fieldType))
			return InputNumber.build(InputNumber.FIELD_OBJECT,entity,InputNumber.FIELD_FIELD,field);
		if(!ClassHelper.isBelongsToJavaPackages(fieldType))
			return AutoComplete.build(AutoComplete.FIELD_OBJECT,entity,AutoComplete.FIELD_FIELD,field);
		LogHelper.logWarning(String.format("input not built for field %s.%s",entity.getClass().getSimpleName(),fieldName),getClass());
		return null;
	}
	
	/**/
	
	protected static final String METHOD_NAME = "record";
}
