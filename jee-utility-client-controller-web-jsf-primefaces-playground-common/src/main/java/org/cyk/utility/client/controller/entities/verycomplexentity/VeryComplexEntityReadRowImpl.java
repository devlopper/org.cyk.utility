package org.cyk.utility.client.controller.entities.verycomplexentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractRowDataImpl;

public class VeryComplexEntityReadRowImpl extends AbstractRowDataImpl<VeryComplexEntity> implements VeryComplexEntityReadRow,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public VeryComplexEntityReadRow setData(VeryComplexEntity data) {
		return (VeryComplexEntityReadRow) super.setData(data);
	}
	
}
