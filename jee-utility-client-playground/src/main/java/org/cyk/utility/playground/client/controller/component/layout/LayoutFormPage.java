package org.cyk.utility.playground.client.controller.component.layout;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
		Form form = new Form();
		form.setData(new Data());
		form.getData().setCode("001");
		
		InputText code = InputText.build(InputText.ConfiguratorImpl.FIELD_OBJECT,form.getData(),InputText.ConfiguratorImpl.FIELD_FIELD_NAME,"code");
		InputText name = InputText.build(InputText.ConfiguratorImpl.FIELD_OBJECT,form.getData(),InputText.ConfiguratorImpl.FIELD_FIELD_NAME,"name");
		AutoComplete namable = AutoComplete.build(AutoComplete.ConfiguratorImpl.FIELD_OBJECT,form.getData(),AutoComplete.ConfiguratorImpl.FIELD_FIELD_NAME,"namable"
				,AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,2
				,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(3),1,new Cell().setWidth(9))
				,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
					MapHelper.instantiate(Cell.FIELD_CONTROL,code.getOutputLabel()),MapHelper.instantiate(Cell.FIELD_CONTROL,code)
					,MapHelper.instantiate(Cell.FIELD_CONTROL,name.getOutputLabel()),MapHelper.instantiate(Cell.FIELD_CONTROL,name)
					,MapHelper.instantiate(Cell.FIELD_CONTROL,namable.getOutputLabel()),MapHelper.instantiate(Cell.FIELD_CONTROL,namable)
					
					,MapHelper.instantiate(Cell.ConfiguratorImpl.FIELD_CONTROL_COMMAND_BUTTON_ARGUMENTS,MapHelper.instantiate(CommandButton.ConfiguratorImpl.FIELD_OBJECT,form
							,CommandButton.ConfiguratorImpl.FIELD_METHOD_NAME,"process"))					
				));

	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data implements Serializable {
		private String code;
		private String name;
		private Namable namable;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractObject implements Serializable {
		private Data data;
		
		public void process() {
			MessageRenderer.getInstance().render("Data has been processed : "+data);
		}
	}
}