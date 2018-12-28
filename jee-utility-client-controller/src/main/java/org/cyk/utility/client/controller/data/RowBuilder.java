package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.object.Objects;

public interface RowBuilder extends FunctionWithPropertiesAsInput<Row> {

	RowBuilder setData(Data data);
	Data getData();
	
	RowBuilder setGrid(GridBuilder grid);
	GridBuilder getGrid();
	
	RowBuilder setRows(Objects rows);
	Objects getRows();
	
	/*
	RowBuilder setClazz(Class<?> clazz);
	Class<?> getClazz();
	
	RowBuilder setNavigationsParametersMap(ObjectByClassMap navigationsParametersMap);
	ObjectByClassMap getNavigationsParametersMap();
	*/
	
}
