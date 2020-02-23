package org.cyk.utility.client.controller.web.jsf.primefaces.model.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell.WidthUnit;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Layout extends OutputPanel implements Serializable {

	private Collection<Cell> cells;
	private Map<Integer,Cell> rowCellModel;
	private WidthUnit cellWidthUnit;
	private Builder.Listener<Cell> cellBuilderListener;
	private Integer numberOfRows,numberOfColumns;
	
	public Cell getCellAt(Integer index) {
		if(index == null)
			return null;
		return CollectionHelper.getElementAt(cells, index);
	}
	
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
	
	public Layout addCellsByMaps(Collection<Map<Object,Object>> cellsMaps) {
		if(CollectionHelper.isEmpty(cellsMaps))
			return this;
		for(Map<Object,Object> cellMap : cellsMaps) {
			cellMap.put(Cell.FIELD_LAYOUT, this);
			cellMap.put(Builder.Listener.class, cellBuilderListener);
			addCells(Cell.build(cellMap));
		}
		return this;
	}
	
	public Layout addCellsByMaps(@SuppressWarnings("unchecked") Map<Object,Object>...cellsMaps) {
		if(ArrayHelper.isEmpty(cellsMaps))
			return this;
		addCellsByMaps(CollectionHelper.listOf(cellsMaps));
		return this;
	}
	
	public Map<Integer,Cell> getRowCellModel(Boolean injectIfNull) {
		if(rowCellModel == null && Boolean.TRUE.equals(injectIfNull))
			rowCellModel = new HashMap<>();
		return rowCellModel;
	}
	
	public Layout setRowCellModels(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return this;
		MapHelper.set(rowCellModel, objects);
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
	public static final String FIELD_CELL_BUILDER_LISTENER = "cellBuilderListener";
	public static final String FIELD_ROW_CELL_MODEL = "rowCellModel";
	public static final String FIELD_CELL_WIDTH_UNIT = "cellWidthUnit";
	public static final String FIELD_NUMBER_OF_ROWS = "numberOfRows";
	public static final String FIELD_NUMBER_OF_COLUMNS = "numberOfColumns";
	
	/**/
	
	public static class ConfiguratorImpl extends OutputPanel.AbstractConfiguratorImpl<Layout> implements Serializable {

		{
			setIsCachable(Boolean.TRUE);
		}
		
		@Override
		public void configure(Layout layout, Map<Object, Object> arguments) {
			super.configure(layout, arguments);
			
			WidthUnit widthUnit = layout.cellWidthUnit;
			if(widthUnit == null)
				widthUnit = WidthUnit.UI_G;
			if(WidthUnit.UI_G.equals(widthUnit)) {
				layout.addStyleClasses("ui-g ui-g-nopad");
			} else if(WidthUnit.FLEX.equals(widthUnit)) {
				layout.addStyleClasses("p-grid");
			}else {
				
			}
			
			@SuppressWarnings("unchecked")
			Collection<Map<Object,Object>> cellsMaps = (Collection<Map<Object, Object>>) MapHelper.readByKey(arguments, FIELD_CELLS_MAPS);
			if(CollectionHelper.isEmpty(cellsMaps)) {
				layout.setNumberOfColumns(CollectionHelper.getSize(layout.cells));
				layout.setNumberOfRows(1);
			}else {
				if(layout.getNumberOfColumns() == null || layout.getNumberOfColumns() <= 0)
					layout.setNumberOfColumns(CollectionHelper.getSize(cellsMaps));
				
				if(layout.getNumberOfRows() == null || layout.getNumberOfRows() <= 0) {
					if(layout.getNumberOfColumns() != null)
						layout.setNumberOfRows(cellsMaps.size() / layout.getNumberOfColumns());
					else
						layout.setNumberOfRows(1);
				}
				
				Integer columnIndex = 0;
				Integer rowIndex = 0;
				for(Map<Object, Object> map : cellsMaps) {
					if(NumberHelper.isGreaterThanZero(layout.getNumberOfColumns()))
						columnIndex = columnIndex % layout.getNumberOfColumns();
					Cell model = MapHelper.readByKey(layout.rowCellModel, columnIndex);
					map.put(Cell.FIELD_LAYOUT, layout);
					map.put(Cell.FIELD_COLUMN_INDEX, columnIndex++);
					map.put(Cell.FIELD_ROW_INDEX, rowIndex);
					MapHelper.copyFromField(map, model, Cell.FIELD_WIDTH, Boolean.FALSE);
				}
				layout.addCellsByMaps(cellsMaps);
			}

			if(layout.getNumberOfRows() == null || NumberHelper.isLessThanOrEqualZero(layout.getNumberOfRows()))
				LogHelper.logWarning(String.format("layout identified by %s has no valid number of rows : %s", layout.identifier,layout.getNumberOfRows()) , ConfiguratorImpl.class);
			if(layout.getNumberOfColumns() == null || NumberHelper.isLessThanOrEqualZero(layout.getNumberOfColumns()))
				LogHelper.logWarning(String.format("layout identified by %s has no valid number of columns : %s", layout.identifier,layout.getNumberOfColumns()) , ConfiguratorImpl.class);
		}
		
		@Override
		protected Class<Layout> __getClass__() {
			return Layout.class;
		}

		/**/
		
		public static final String FIELD_CELLS_MAPS = "cellsMaps";
	}

	public static Layout build(Map<Object,Object> arguments) {
		return Builder.build(Layout.class,arguments);
	}
	
	static {
		Configurator.set(Layout.class, new ConfiguratorImpl());
	}
}
