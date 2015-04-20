package org.cyk.utility.common.model.table;

import org.apache.commons.lang3.StringUtils;

public class DefaultTable<DATA> extends Table<DefaultRow<DATA>, DefaultColumn, DATA, String, DefaultCell, String> {

	@SuppressWarnings("unchecked")
	public DefaultTable(Class<DATA> rowDataClass) {
		super((Class<? extends DefaultRow<DATA>>) DefaultRow.class, rowDataClass, DefaultColumn.class, DefaultCell.class);
	}

	private static final long serialVersionUID = 581883275700805955L;

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(DefaultColumn column : columns)
			s.append(String.format(DefaultRow.FORMAT, column.getTitle()));
		
		s.append("\r\n"+StringUtils.repeat('#', s.length())+"\r\n");
		
		for(DefaultRow<DATA> row : rows)
			s.append(row.toString()+"\r\n");
		return s.toString();
	}
	
}
