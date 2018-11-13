package org.cyk.utility.client.controller.data;

public interface RowData<DATA extends Data> extends Row {

	DATA getData();
	RowData<DATA> setData(DATA data);
	
	String PROPERTY_DATA = "data";
}
