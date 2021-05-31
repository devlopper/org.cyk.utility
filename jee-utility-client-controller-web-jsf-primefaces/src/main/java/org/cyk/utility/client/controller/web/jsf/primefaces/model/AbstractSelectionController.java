package org.cyk.utility.client.controller.web.jsf.primefaces.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSelectionController<T> implements Serializable {

	protected Class<T> klass;
	protected Boolean isMultiple;
	protected Dialog dialog;
	protected CommandButton showDialogCommandButton;
	
	protected AutoComplete autoComplete;
	protected Redirector.Arguments onSelectRedirectorArguments;
	
	protected CommandButton selectCommandButton;
	
	protected Layout layout;
	
	@SuppressWarnings("unchecked")
	public AbstractSelectionController() {
		klass = (Class<T>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	public AbstractSelectionController<T> build() {
		buildDialog();
		buildShowDialogCommandButton();
		
		buildAutoComplete();
		if(Boolean.TRUE.equals(getIsMultiple()))
			buildSelectCommandButton();
		buildLayout();
		return this;
	}
	
	public Redirector.Arguments getOnSelectRedirectorArguments(Boolean injectIfNull) {
		if(onSelectRedirectorArguments == null && Boolean.TRUE.equals(injectIfNull))
			onSelectRedirectorArguments = new Redirector.Arguments();
		return onSelectRedirectorArguments;
	}
	
	protected void buildDialog() {
		dialog = Dialog.build(Dialog.FIELD_HEADER,"Recherche",Dialog.FIELD_MODAL,Boolean.TRUE
				,Dialog.ConfiguratorImpl.FIELD_COMMAND_BUTTONS_BUILDABLE,Boolean.FALSE);
		dialog.addStyleClasses("cyk-min-width-90-percent");
		if(Boolean.TRUE.equals(isMultiple)) {
			dialog.setHeight(150);
			dialog.setResizable(Boolean.TRUE);
		}
	}
	
	protected void buildShowDialogCommandButton() {
		showDialogCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Rechercher",CommandButton.FIELD_ICON,"fa fa-search"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.SHOW_DIALOG,CommandButton.FIELD___DIALOG__,dialog);		
	}
	
	protected void buildAutoComplete() {
		autoComplete = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,klass,AutoComplete.FIELD_MULTIPLE,getIsMultiple()
				,AutoComplete.FIELD_PLACEHOLDER,"Veuillez saisir une partie du code ou du libellé");
		
		autoComplete.enableAjaxItemSelect();
		autoComplete.getAjaxes().get("itemSelect").setListener(new Ajax.Listener.AbstractImpl() {
			@Override
			protected void run(AbstractAction action) {
				if(Boolean.TRUE.equals(getIsMultiple())) {
					
				}else {
					@SuppressWarnings("unchecked")
					T choice = (T) FieldHelper.read(action.get__argument__(), "source.value");
					if(choice != null && onSelectRedirectorArguments != null) {
						onSelectRedirectorArguments.addParameters(Map.of(ParameterName.stringify(klass),List.of((String)FieldHelper.readSystemIdentifier(choice))));
						Redirector.getInstance().redirect(onSelectRedirectorArguments);				
					}
				}				
			}
		});
		autoComplete.getAjaxes().get("itemSelect").setDisabled(Boolean.FALSE);
	}
	
	protected void buildSelectCommandButton() {
		selectCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Sélectionner",CommandButton.FIELD_ICON,"fa fa-check"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION,CommandButton.FIELD_LISTENER
				,new AbstractAction.Listener.AbstractImpl() {
					@SuppressWarnings("unchecked")
					@Override
					protected Object __runExecuteFunction__(AbstractAction action) {
						if(onSelectRedirectorArguments == null || autoComplete.getValue() == null)
							return null;
						Collection<T> choices = Boolean.TRUE.equals(getIsMultiple()) ? (Collection<T>) autoComplete.getValue() : List.of((T) autoComplete.getValue());					
						if(CollectionHelper.isEmpty(choices))
							return null;						
						if(choices.size() == 1) {
							onSelectRedirectorArguments.addParameters(Map.of(ParameterName.stringify(klass)
									,List.of((String)FieldHelper.readSystemIdentifier(CollectionHelper.getFirst(choices)))));
							Redirector.getInstance().redirect(onSelectRedirectorArguments);							
						}else {
							List<String> identifiers  = choices.stream().map(choice ->StringHelper.get(FieldHelper.readSystemIdentifier(choice))).collect(Collectors.toList());								
							onSelectRedirectorArguments.addParameters(Map.of(ParameterName.stringifyMany(klass),identifiers));								
							Redirector.getInstance().redirect(onSelectRedirectorArguments);				
						}			
						return null;
					}
				});		
	}
	
	protected void buildLayout() {
		Collection<Map<Object,Object>> cellsMaps = new ArrayList<>();		
		if(selectCommandButton == null) {
			cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,autoComplete,Cell.FIELD_WIDTH,12));
		}else {
			cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,autoComplete,Cell.FIELD_WIDTH,10));
			cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,selectCommandButton,Cell.FIELD_WIDTH,2));
		}
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps);
	}
}