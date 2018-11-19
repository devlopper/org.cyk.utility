package org.cyk.utility.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractRowDataImpl;

public class MyEntityReadRowImpl extends AbstractRowDataImpl<MyEntity> implements MyEntityReadRow,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MyEntityReadRow setData(MyEntity data) {
		return (MyEntityReadRow) super.setData(data);
	}
	
}
