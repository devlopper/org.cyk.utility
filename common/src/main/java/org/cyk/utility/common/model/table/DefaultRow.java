package org.cyk.utility.common.model.table;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultRow<DATA> extends AbstractRow<DATA,DefaultCell,String> implements Serializable {

	private static final long serialVersionUID = -3855944230298132423L;

	public static String FORMAT = "%15s |";
	
	public DefaultRow(DATA data, String title) {
		super(data, title);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(DefaultCell cell : cells)
			s.append(String.format(FORMAT, cell.getValue()));
		s.append("\r\n"+StringUtils.repeat('-', s.length()));
		return s.toString();
	}
	
}
