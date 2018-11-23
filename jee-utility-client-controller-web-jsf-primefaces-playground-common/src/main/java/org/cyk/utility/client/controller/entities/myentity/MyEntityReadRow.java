package org.cyk.utility.client.controller.entities.myentity;

import org.cyk.utility.client.controller.data.RowData;

public interface MyEntityReadRow extends RowData<MyEntity> {

	@Override MyEntityReadRow setData(MyEntity data);
	
}
