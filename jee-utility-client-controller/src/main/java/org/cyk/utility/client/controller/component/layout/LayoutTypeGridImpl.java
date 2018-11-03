package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentRole;

public class LayoutTypeGridImpl extends AbstractLayoutTypeImpl implements LayoutTypeGrid,Serializable {
	private static final long serialVersionUID = 1L;

	private Integer columnCount,rowCount;
	private Boolean isHasHeader,isHasFooter,isHasOrderNumberColumn,isHasCommandablesColumn;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getRoles(Boolean.TRUE).add(ComponentRole.GRID);
	}
	
	@Override
	public Integer getColumnCount() {
		return columnCount;
	}

	@Override
	public LayoutTypeGrid setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
		return this;
	}

	@Override
	public Integer getRowCount() {
		return rowCount;
	}

	@Override
	public LayoutTypeGrid setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
		return this;
	}

	@Override
	public Boolean getIsHasHeader() {
		return isHasHeader;
	}

	@Override
	public LayoutTypeGrid setIsHasHeader(Boolean isHasHeader) {
		this.isHasHeader = isHasHeader;
		return this;
	}

	@Override
	public Boolean getIsHasFooter() {
		return isHasFooter;
	}

	@Override
	public LayoutTypeGrid setIsHasFooter(Boolean isHasFooter) {
		this.isHasFooter = isHasFooter;
		return this;
	}

	@Override
	public Boolean getIsHasOrderNumberColumn() {
		return isHasOrderNumberColumn;
	}

	@Override
	public LayoutTypeGrid setIsHasOrderNumberColumn(Boolean isHasOrderNumberColumn) {
		this.isHasOrderNumberColumn = isHasOrderNumberColumn;
		return this;
	}

	@Override
	public Boolean getIsHasCommandablesColumn() {
		return isHasCommandablesColumn;
	}

	@Override
	public LayoutTypeGrid setIsHasCommandablesColumn(Boolean isHasCommandablesColumn) {
		this.isHasCommandablesColumn = isHasCommandablesColumn;
		return this;
	}

	
	
}
