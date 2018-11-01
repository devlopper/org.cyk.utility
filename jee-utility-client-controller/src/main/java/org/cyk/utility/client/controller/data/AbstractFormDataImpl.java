package org.cyk.utility.client.controller.data;

import java.io.Serializable;

public abstract class AbstractFormDataImpl<DATA extends Data> extends AbstractFormImpl implements FormData<DATA>,Serializable {
	private static final long serialVersionUID = 1L;

	private DATA data;
	
	@Override
	public DATA getData() {
		return data;
	}
	
	@Override
	public FormData<DATA> setData(DATA data) {
		this.data = data;
		return this;
	}

}
