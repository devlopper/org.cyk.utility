package org.cyk.utility.common.model.table;

import java.io.Serializable;

public class DefaultDimension<DATA> extends AbstractDimension<DATA, DefaultCell, String> implements Serializable {

	private static final long serialVersionUID = -3855944230298132423L;

	public DefaultDimension() {
		super();
	}

	public DefaultDimension(DATA data, String title) {
		super(data, title);
	}

	public DefaultDimension(String title) {
		super(title);
	}
	
	

}
