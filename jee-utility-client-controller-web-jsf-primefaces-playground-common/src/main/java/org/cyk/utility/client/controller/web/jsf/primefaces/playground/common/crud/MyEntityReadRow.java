package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import org.cyk.utility.client.controller.data.RowData;

public interface MyEntityReadRow extends RowData<MyEntity> {

	@Override MyEntityReadRow setData(MyEntity data);
	
}
