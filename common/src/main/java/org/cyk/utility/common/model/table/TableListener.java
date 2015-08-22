package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface TableListener< 
ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>, 
COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> {

	Table<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> getTable();
	
	void beforeBuild();
	
	void afterBuild();
	
	Collection<ROW_DATA> fetchData(FetchDataOptions options);
	Long count(FetchDataOptions options);
	
	/**/
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class FetchDataOptions implements Serializable{

		private static final long serialVersionUID = 6248718022428462935L;
		private Integer first,pageSize;
		private String sortField;
		private Boolean ascendingOrder;
		private Map<String, Object> filters;
		private String globalFilter;
	}
	
}
