package org.cyk.utility.client.controller.window;

import org.cyk.utility.client.controller.data.RowData;

public interface VerySimpleEntityReadRow extends RowData<VerySimpleEntity> {

	@Override VerySimpleEntityReadRow setData(VerySimpleEntity data);
	
}
