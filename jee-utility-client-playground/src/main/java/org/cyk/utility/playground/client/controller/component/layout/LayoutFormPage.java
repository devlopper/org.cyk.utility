package org.cyk.utility.playground.client.controller.component.layout;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LayoutFormPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Layout Form Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		InputText inputTextRequired = InputText.build(InputText.FIELD_REQUIRED,Boolean.TRUE);
		inputTextRequired.getOutputLabel().setValue("Input Text Required");
		InputText inputTextNotRequired = InputText.build();
		inputTextNotRequired.getOutputLabel().setValue("Input Text Not Required");
		
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,2
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(3),1,new Cell().setWidth(9))
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_CONTROL,inputTextRequired.getOutputLabel())
					,MapHelper.instantiate(Cell.FIELD_CONTROL,inputTextRequired)
					,MapHelper.instantiate(Cell.FIELD_CONTROL,inputTextNotRequired.getOutputLabel())
					,MapHelper.instantiate(Cell.FIELD_CONTROL,inputTextNotRequired)
					
					,MapHelper.instantiate(Cell.ConfiguratorImpl.FIELD_CONTROL_COMMAND_BUTTON_ARGUMENTS,Map.of(CommandButton.FIELD_VALUE,"Envoyer",CommandButton.FIELD_LISTENER,new CommandButton.Listener() {
						@Override
						public void listenAction(Object argument) {
							String i01 = ((InputText)layout.getCellAt(1).getControl()).getValue();
							String i02 = ((InputText)layout.getCellAt(3).getControl()).getValue();
							MessageRenderer.getInstance().render("Input 1 : "+i01+" - Input 2 : "+i02);
						}
					}))	
				));
	}
	
}