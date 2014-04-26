package org.cyk.utility.common.model.table;

import java.io.Serializable;

public class DefaultTableRow<DATA> extends DefaultDimension<DATA> implements Serializable {

	private static final long serialVersionUID = -3855944230298132423L;

	public DefaultTableRow(DATA data, String title) {
		super(data, title);
	}

	
	
}
