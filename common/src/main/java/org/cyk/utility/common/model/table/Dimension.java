package org.cyk.utility.common.model.table;

import java.util.List;

public interface Dimension<DATA,CELL extends Cell<VALUE>,VALUE> {

	Long getIndex();
	void setIndex(Long anIndex);
	
	Long getUiIndex();
	void setUiIndex(Long anIndex);
	
	DATA getData();
	void setData(DATA aData);
	
	String getTitle();
	void setTitle(String aTitle);
	
	Boolean getIsSummary();
	void setIsSummary(Boolean value);
	
	List<CELL> getCells();
	
}
