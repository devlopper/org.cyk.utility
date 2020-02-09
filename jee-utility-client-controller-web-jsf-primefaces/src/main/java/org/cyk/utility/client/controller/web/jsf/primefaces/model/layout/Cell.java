package org.cyk.utility.client.controller.web.jsf.primefaces.model.layout;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Cell extends OutputPanel implements Serializable {

	private Layout layout;
	private Integer width;
	private WidthUnit widthUnit;
	private Boolean isWidthFixed;
	
	/**/
	
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_WIDTH = "width";
	public static final String FIELD_WIDTH_UNIT = "widthUnit";
	public static final String FIELD_IS_WIDTH_FIXED = "isWidthFixed";
	
	/**/
	
	public static enum WidthUnit {
		UI_G,FLEX,PIXEL
	}
	
	public static class ConfiguratorImpl extends OutputPanel.AbstractConfiguratorImpl<Cell> implements Serializable {

		@Override
		public void configure(Cell cell, Map<Object, Object> arguments) {
			super.configure(cell, arguments);
			WidthUnit widthUnit = cell.widthUnit;
			if(widthUnit == null)
				widthUnit = WidthUnit.UI_G;
			if(WidthUnit.UI_G.equals(widthUnit)) {
				if(cell.getWidth() == null || NumberHelper.isLessThanOrEqualZero(cell.getWidth()))
					LogHelper.logWarning(String.format("layout cell identified by %s has no valid width : %s", cell.identifier,cell.getWidth()) , ConfiguratorImpl.class);
				else
					cell.addStyleClasses("ui-g-"+cell.width);
			} else if(WidthUnit.FLEX.equals(widthUnit)) {
				cell.addStyleClasses("p-col-"+cell.width);
			}else {
				
			}
		}
		
		@Override
		protected Collection<String> __getFieldsNames__() {
			return CollectionHelper.concatenate(super.__getFieldsNames__(),List.of(FIELD_LAYOUT,FIELD_WIDTH,FIELD_WIDTH_UNIT,FIELD_IS_WIDTH_FIXED));
		}

	}

	public static Cell build(Map<Object,Object> map) {
		return Builder.build(Cell.class,map);
	}
	
	static {
		Configurator.set(Cell.class, new ConfiguratorImpl());
	}
}
