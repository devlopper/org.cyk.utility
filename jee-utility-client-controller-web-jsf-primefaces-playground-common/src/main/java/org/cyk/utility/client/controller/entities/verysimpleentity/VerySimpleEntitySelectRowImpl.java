package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractRowDataImpl;

public class VerySimpleEntitySelectRowImpl extends AbstractRowDataImpl<VerySimpleEntity> implements VerySimpleEntitySelectRow,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public VerySimpleEntitySelectRow setData(VerySimpleEntity data) {
		return (VerySimpleEntitySelectRow) super.setData(data);
	}
	
}
