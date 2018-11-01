package org.cyk.utility.client.controller.data;

public interface FormData<DATA extends Data> extends Form {

	DATA getData();
	FormData<DATA> setData(DATA data);
	
}
