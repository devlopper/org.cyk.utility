package org.cyk.utility.client.controller.component.layout;

public interface LayoutTypeGrid extends LayoutType {

	Integer getColumnCount();
	LayoutTypeGrid setColumnCount(Integer columnCount);
	
	Integer getRowCount();
	LayoutTypeGrid setRowCount(Integer rowCount);
	
	Boolean getIsHasHeader();
	LayoutTypeGrid setIsHasHeader(Boolean isHasHeader);
	
	Boolean getIsHasFooter();
	LayoutTypeGrid setIsHasFooter(Boolean isHasFooter);
	
	Boolean getIsHasOrderNumberColumn();
	LayoutTypeGrid setIsHasOrderNumberColumn(Boolean isHasOrderNumberColumn);
	
	Boolean getIsHasCommandablesColumn();
	LayoutTypeGrid setIsHasCommandablesColumn(Boolean isHasCommandablesColumn);
}
