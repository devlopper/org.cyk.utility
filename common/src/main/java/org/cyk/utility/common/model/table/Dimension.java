package org.cyk.utility.common.model.table;

import java.util.List;

public interface Dimension<DATA,CELL extends Cell<VALUE>,VALUE> {

	Long getIndex();
	void setIndex(Long anIndex);
	
	DATA getData();
	void setData(DATA aData);
	
	String getTitle();
	void setTitle(String aTitle);
	
	List<CELL> getCells();
	
}
