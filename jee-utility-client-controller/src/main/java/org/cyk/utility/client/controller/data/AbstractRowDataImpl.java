package org.cyk.utility.client.controller.data;

import java.io.Serializable;

public abstract class AbstractRowDataImpl<DATA extends Data> extends AbstractRowImpl implements RowData<DATA>,Serializable {
	private static final long serialVersionUID = 1L;

	private DATA data;
	
	@Override
	public DATA getData() {
		return data;
	}
	
	@Override
	public RowData<DATA> setData(DATA data) {
		this.data = data;
		return this;
	}

}
