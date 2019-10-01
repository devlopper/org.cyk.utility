package org.cyk.utility.client.controller.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractRowDataImpl<DATA extends Data> extends AbstractRowImpl implements RowData<DATA>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<DATA> dataClass;
	private DATA data;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataClass = (Class<DATA>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	@Override
	public DATA getData() {
		return data;
	}
	
	@Override
	public RowData<DATA> setData(DATA data) {
		this.data = data;
		return this;
	}
	
	@Override
	public String getUrlBySystemActionClass(Object request,Class<? extends SystemAction> aClass) {		
		return UniformResourceIdentifierHelper.build(request, aClass, null, dataClass, (Collection<DATA>)List.of(data), null, null, null);
	}
	
	@Override
	public Class<DATA> getDataClass() {
		return dataClass;
	}
	
	@Override
	public RowData<DATA> setDataClass(Class<DATA> dataClass) {
		this.dataClass = dataClass;
		return this;
	}

}
