package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.object.Objects;

public interface RowBuilder extends FunctionWithPropertiesAsInput<Row> {

	RowBuilder setData(Data data);
	Data getData();
	
	RowBuilder setDataClass(Class<? extends Data> dataClass);
	Class<? extends Data> getDataClass();
	
	RowBuilder setGrid(GridBuilder grid);
	GridBuilder getGrid();
	
	RowBuilder setRows(Objects rows);
	Objects getRows();
	
	Integer getOrderNumberOffset();
	RowBuilder setOrderNumberOffset(Integer orderNumberOffset);
	
	/*
	RowBuilder setClazz(Class<?> clazz);
	Class<?> getClazz();
	
	RowBuilder setNavigationsParametersMap(ObjectByClassMap navigationsParametersMap);
	ObjectByClassMap getNavigationsParametersMap();
	*/
	
}
