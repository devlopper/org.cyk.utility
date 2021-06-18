package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPagePreferencesController extends AbstractObject implements Serializable {

	protected Dialog dialog;
	protected CommandButton showDialogCommandButton;
	protected CommandButton saveCommandButton;

	protected Layout layout;
	
	public AbstractPagePreferencesController build() {
		buildDialog();
		buildShowDialogCommandButton();
		buildSaveCommandButton();
		buildLayout();
		/*if(layout != null) {
			if(layout.getContainer() instanceof Panel)
				((Panel)layout.getContainer()).setCollapsed(Boolean.TRUE);
		}*/
		return this;
	}
	
	protected Dialog buildDialog() {
		dialog = Dialog.build(Dialog.FIELD_HEADER,"Préférences",Dialog.FIELD_MODAL,Boolean.TRUE,Dialog.ConfiguratorImpl.FIELD_COMMAND_BUTTONS_BUILDABLE,Boolean.FALSE);
		dialog.addStyleClasses("cyk-min-width-90-percent");		
		dialog.setHeight(150);
		dialog.setResizable(Boolean.TRUE);
		return dialog;
	}
	
	protected CommandButton buildShowDialogCommandButton() {
		showDialogCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Préférences",CommandButton.FIELD_ICON,"fa fa-gear"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.SHOW_DIALOG,CommandButton.FIELD___DIALOG__,dialog);
		//showDialogCommandButton.addUpdates(":form:"+dialog.get__containerIdentifier__());
		return showDialogCommandButton;
	}
	
	protected CommandButton buildSaveCommandButton() {
		saveCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Enregistrer",CommandButton.FIELD_ICON,"fa fa-floppy-o"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,List.of(RenderType.GROWL)
				,CommandButton.FIELD_LISTENER,new CommandButton.Listener.AbstractImpl() {
					@Override
					protected Object __runExecuteFunction__(AbstractAction action) {
						return save();
					}
				}/*,CommandButton.FIELD_STYLE_CLASS,"cyk-float-left"*/);
		return saveCommandButton;
	}
	
	protected Object save() {
		return __save__();
	}

	protected abstract Object __save__();
	
	protected void buildLayout() {
		Collection<Map<Object,Object>> cellsMaps = buildLayoutCells();
		if(CollectionHelper.isEmpty(cellsMaps))
			return;
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps);
	}
	
	protected Collection<Map<Object,Object>> buildLayoutCells() {
		Collection<Map<Object,Object>> cellsMaps = new ArrayList<>();
		buildLayoutCells(cellsMaps);
		return cellsMaps;
	}
	
	protected void buildLayoutCells(Collection<Map<Object,Object>> cellsMaps) {
		List<AbstractInput<?>> inputs = getInputs();
		if(CollectionHelper.isNotEmpty(inputs)) {
			//Integer saveCommandButtonWidth = 1;
			for(Integer index = 0 ; index < inputs.size() ; index = index + 1) {
				AbstractInput<?> input = inputs.get(index);
				if(input == null)
					continue;
				cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input.getOutputLabel(),Cell.FIELD_WIDTH,3));
				cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input,Cell.FIELD_WIDTH,9 /*- (index == inputs.size() - 1 ? saveCommandButtonWidth : 0)*/));				
			}
			cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,saveCommandButton,Cell.FIELD_WIDTH,12/*saveCommandButtonWidth*/));
		}
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
}