package org.cyk.utility.client.controller.web.jsf.primefaces.model.layout;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Cell extends OutputPanel implements Serializable {

	private Layout layout;
	private Integer columnIndex,rowIndex;
	private Integer width;
	private WidthUnit widthUnit;
	private Boolean isWidthFixed;
	private Object control;
	
	/**/
	
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_COLUMN_INDEX = "columnIndex";
	public static final String FIELD_ROW_INDEX = "rowIndex";
	public static final String FIELD_WIDTH = "width";
	public static final String FIELD_WIDTH_UNIT = "widthUnit";
	public static final String FIELD_IS_WIDTH_FIXED = "isWidthFixed";
	public static final String FIELD_CONTROL = "control";
	
	/**/
	
	public static enum WidthUnit {
		UI_G,FLEX,PIXEL
	}
	
	public static class ConfiguratorImpl extends OutputPanel.AbstractConfiguratorImpl<Cell> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(Cell cell, Map<Object, Object> arguments) {
			super.configure(cell, arguments);
			WidthUnit widthUnit = cell.widthUnit;
			if(widthUnit == null && cell.getLayout() != null)
				widthUnit = cell.getLayout().getCellWidthUnit();
			if(widthUnit == null)
				widthUnit = WidthUnit.UI_G;
			if(WidthUnit.UI_G.equals(widthUnit)) {
				if(cell.getWidth() == null || NumberHelper.isLessThanOrEqualZero(cell.getWidth()))
					LogHelper.logWarning(String.format("layout cell identified by %s has no valid width : %s", cell.identifier,cell.getWidth()) , ConfiguratorImpl.class);
				else
					//cell.addStyleClasses("ui-g-"+cell.width+" ui-sm-12 ui-g-nopad");
					cell.addStyleClasses("ui-g-"+cell.width+" ui-sm-12");
			} else if(WidthUnit.FLEX.equals(widthUnit)) {
				cell.addStyleClasses("p-col-"+cell.width);
			}else {
				
			}
			if(cell.control == null) {
				Map<Object,Object> commandButtonArguments = (Map<Object, Object>) MapHelper.readByKey(arguments, FIELD_CONTROL_COMMAND_BUTTON_ARGUMENTS);
				if(commandButtonArguments != null) {
					CommandButton commandButton = CommandButton.build(commandButtonArguments);
					commandButton.addUpdatables(cell.layout);
					cell.control = commandButton;
				}	
			}			
		}
		
		@Override
		protected Class<Cell> __getClass__() {
			return Cell.class;
		}
		
		public static final String FIELD_CONTROL_COMMAND_BUTTON_ARGUMENTS = "commandButtonArguments";
	}

	public static Cell build(Map<Object,Object> map) {
		return Builder.build(Cell.class,map);
	}
	
	public static interface Listener {
		
		void addCell(Cell cell);
		
		default void addCell(Map<Object,Object> map) {
			if(MapHelper.isEmpty(map))
				return;
			
		}
		
	}
	
	static {
		Configurator.set(Cell.class, new ConfiguratorImpl());
	}
}
