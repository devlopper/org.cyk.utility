package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractRowDataImpl;

public class VerySimpleEntityDetailsReadRowImpl extends AbstractRowDataImpl<VerySimpleEntityDetails> implements VerySimpleEntityDetailsReadRow,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public VerySimpleEntityDetailsReadRow setData(VerySimpleEntityDetails data) {
		return (VerySimpleEntityDetailsReadRow) super.setData(data);
	}
	
}
