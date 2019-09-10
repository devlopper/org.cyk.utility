package org.cyk.utility.client.controller.component.grid;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.cell.Cells;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.grid.row.Rows;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;

public class GridImpl extends AbstractVisibleComponentImpl implements Grid,Serializable {
	private static final long serialVersionUID = 1L;

	private Columns columns;
	private Rows rows;
	private View view;
	private Objects objects;
	private ViewMap viewMap;
	private Boolean isLazyLoadable;
	
	@Override
	public Boolean getIsLazyLoadable() {
		return isLazyLoadable;
	}
	
	@Override
	public Grid setIsLazyLoadable(Boolean isLazyLoadable) {
		this.isLazyLoadable = isLazyLoadable;
		return this;
	}
	
	@Override
	public Columns getColumns() {
		return columns;
	}

	@Override
	public Grid setColumns(Columns columns) {
		this.columns = columns;
		return this;
	}
	
	@Override
	public Rows getRows() {
		return rows;
	}
	
	@Override
	public Grid setRows(Rows rows) {
		this.rows = rows;
		return this;
	}
	
	@Override
	public Cell getCell(Column column, Row row) {
		Cell cell = null;
		if(column!=null && row!=null) {
			Rows rows = getRows();
			if(__inject__(CollectionHelper.class).isNotEmpty(rows))
				for(Row indexRow : rows.get())
					if(indexRow == row) {
						Cells cells = row.getCells();
						if(__inject__(CollectionHelper.class).isNotEmpty(cells))
							cell = cells.getAt(column.getOrderNumber());
						break;
					}	
		}
		return cell;
	}
	
	@Override
	public View getView() {
		return view;
	}
	@Override
	public Grid setView(View view) {
		this.view = view;
		return this;
	}
	
	@Override
	public ViewMap getViewMap() {
		return viewMap;
	}

	@Override
	public Grid setViewMap(ViewMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}
	
	@Override
	public View getView(String key) {
		ViewMap viewMap = getViewMap();
		return viewMap == null ? null : viewMap.get(key);
	}
	
	@Override
	public Objects getObjects() {
		return objects;
	}
	
	@Override
	public Objects getObjects(Boolean injectIfNull) {
		if(objects == null && Boolean.TRUE.equals(injectIfNull))
			objects = __inject__(Objects.class);
		return objects;
	}
	
	@Override
	public Grid setObjects(Objects objects) {
		this.objects = objects;
		return this;
	}
	
	/**/

}
