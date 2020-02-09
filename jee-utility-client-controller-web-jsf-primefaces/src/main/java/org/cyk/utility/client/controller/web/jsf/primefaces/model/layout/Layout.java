package org.cyk.utility.client.controller.web.jsf.primefaces.model.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell.WidthUnit;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Layout extends OutputPanel implements Serializable {

	private Collection<Cell> cells;
	private WidthUnit cellWidthUnit;
	private Integer numberOfRows,numberOfColumns;
	
	public Collection<Cell> getCells(Boolean injectIfNull) {
		if(cells == null && Boolean.TRUE.equals(injectIfNull))
			cells = new ArrayList<>();
		return cells;
	}
	
	public Layout addCells(Collection<Cell> cells) {
		if(CollectionHelper.isEmpty(cells))
			return this;
		for(Cell cell : cells)
			cell.setLayout(this);
		getCells(Boolean.TRUE).addAll(cells);
		return this;
	}
	
	public Layout addCells(Cell...cells) {
		if(ArrayHelper.isEmpty(cells))
			return this;
		addCells(CollectionHelper.listOf(cells));
		return this;
	}
	
	public Layout addRow(Collection<Cell> cells) {
		if(CollectionHelper.isEmpty(cells))
			return this;
		return addCells(cells);
	}
	
	public Layout addRow(Cell...cells) {
		if(ArrayHelper.isEmpty(cells))
			return this;
		addRow(CollectionHelper.listOf(cells));
		return this;
	}
	
	/**/
	
	public static final String FIELD_CELLS = "cells";
	public static final String FIELD_CELL_WIDTH_UNIT = "cellWidthUnit";
	public static final String FIELD_NUMBER_OF_ROWS = "numberOfRows";
	public static final String FIELD_NUMBER_OF_COLUMNS = "numberOfColumns";
	
	/**/
	
	public static class ConfiguratorImpl extends OutputPanel.AbstractConfiguratorImpl<Layout> implements Serializable {

		@Override
		public void configure(Layout layout, Map<Object, Object> arguments) {
			super.configure(layout, arguments);
			if(layout.getNumberOfRows() == null) {
				if(CollectionHelper.isNotEmpty(layout.cells))
					layout.setNumberOfRows(1);
			}
			if(layout.getNumberOfColumns() == null) {
				layout.setNumberOfColumns(CollectionHelper.getSize(layout.cells));
			}
			if(layout.getNumberOfRows() == null || NumberHelper.isLessThanOrEqualZero(layout.getNumberOfRows()))
				LogHelper.logWarning(String.format("layout identified by %s has no valid number of rows : %s", layout.identifier,layout.getNumberOfRows()) , ConfiguratorImpl.class);
			if(layout.getNumberOfColumns() == null || NumberHelper.isLessThanOrEqualZero(layout.getNumberOfColumns()))
				LogHelper.logWarning(String.format("layout identified by %s has no valid number of columns : %s", layout.identifier,layout.getNumberOfColumns()) , ConfiguratorImpl.class);
		}
		
		@Override
		protected Collection<String> __getFieldsNames__() {
			return CollectionHelper.concatenate(super.__getFieldsNames__(),List.of(FIELD_CELLS,FIELD_CELL_WIDTH_UNIT,FIELD_NUMBER_OF_ROWS,FIELD_NUMBER_OF_COLUMNS));
		}

	}

	static {
		Configurator.set(Layout.class, new ConfiguratorImpl());
	}
}
