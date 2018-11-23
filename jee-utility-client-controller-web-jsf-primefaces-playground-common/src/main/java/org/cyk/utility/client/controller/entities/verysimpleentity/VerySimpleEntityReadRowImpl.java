package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractRowDataImpl;

public class VerySimpleEntityReadRowImpl extends AbstractRowDataImpl<VerySimpleEntity> implements VerySimpleEntityReadRow,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public VerySimpleEntityReadRow setData(VerySimpleEntity data) {
		return (VerySimpleEntityReadRow) super.setData(data);
	}
	
}
