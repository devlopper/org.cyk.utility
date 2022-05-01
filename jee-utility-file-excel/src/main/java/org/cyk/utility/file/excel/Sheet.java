package org.cyk.utility.file.excel;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Sheet implements Serializable {

	private WorkBook workBook;
	private Object value;
	private Map<Integer,Map<Integer,Exception>> cellsEvaluationExceptionsMap;

	public Sheet(WorkBook workBook,Object value) {
		this.workBook = workBook;
		this.value = value;
	}
	
	public Sheet setCellEvaluationException(Integer rowIndex,Integer columnIndex,Exception exception) {
		if(rowIndex == null || columnIndex == null || exception == null)
			return this;
		if(cellsEvaluationExceptionsMap == null)
			cellsEvaluationExceptionsMap = new LinkedHashMap<>();
		Map<Integer,Exception> map = cellsEvaluationExceptionsMap.get(rowIndex);
		if(map == null)
			cellsEvaluationExceptionsMap.put(rowIndex, new LinkedHashMap<>());
		map.put(columnIndex, exception);
		return this;
	}
}