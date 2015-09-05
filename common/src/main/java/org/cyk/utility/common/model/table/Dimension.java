package org.cyk.utility.common.model.table;

import java.util.List;

public interface Dimension<DATA,CELL extends Cell<VALUE>,VALUE> {

	public enum DimensionType{HEADER,FOOTER,SUMMARY,DETAIL}
	
	Long getIndex();
	void setIndex(Long anIndex);
	
	Long getUiIndex();
	void setUiIndex(Long anIndex);
	
	DATA getData();
	void setData(DATA aData);
	
	String getTitle();
	void setTitle(String aTitle);
	/*
	Boolean getIsSummary();
	void setIsSummary(Boolean value);
	*/
	
	DimensionType getType();
	void setType(DimensionType type);
	
	List<CELL> getCells();
	
	Boolean getIsDetail();
	
	Boolean getOpenable();
	void setOpenable(Boolean value);
	
	Boolean getUpdatable();
	void setUpdatable(Boolean value);
	
	Boolean getDeletable();
	void setDeletable(Boolean value);
	
	Boolean getCountable();
	void setCountable(Boolean value);
}
