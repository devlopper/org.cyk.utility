package org.cyk.utility.client.controller.data;

public interface RowData<DATA extends Data> extends Row {

	DATA getData();
	RowData<DATA> setData(DATA data);
	
	Class<DATA> getDataClass();
	RowData<DATA> setDataClass(Class<DATA> dataClass);
	
	String PROPERTY_DATA = "data";
	String PROPERTY_DATA_CLASS = "dataClass";
}
