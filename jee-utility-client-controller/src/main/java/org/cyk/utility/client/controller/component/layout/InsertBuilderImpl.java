package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInvisibleComponentBuilderImpl;

public class InsertBuilderImpl extends AbstractInvisibleComponentBuilderImpl<Insert> implements InsertBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@Override
	protected void __execute__(Insert insert) {
		super.__execute__(insert);
		String name = getName();
		insert.setName(name);
	}
	
	@Override
	public InsertBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

}
