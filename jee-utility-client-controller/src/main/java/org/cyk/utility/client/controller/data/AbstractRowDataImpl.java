package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierStringBuilder;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractRowDataImpl<DATA extends Data> extends AbstractRowImpl implements RowData<DATA>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<DATA> dataClass;
	private DATA data;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataClass = (Class<DATA>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Data.class);
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
	public String getUrlBySystemActionClass(Class<? extends SystemAction> aClass) {
		String url = null;
		Data data = getData();
		SystemAction systemAction = __inject__(aClass);
		systemAction.getEntities(Boolean.TRUE).setElementClass(dataClass);
		systemAction.getEntities(Boolean.TRUE).add(getData());
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		url = __inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier(identifier).execute().getOutput();
		UniformResourceIdentifierStringBuilder uniformResourceIdentifierStringBuilder = __inject__(UniformResourceIdentifierStringBuilder.class);
		uniformResourceIdentifierStringBuilder.setString(url).setParameters(
				"class",dataClass.getSimpleName().toLowerCase()
				,"action",systemAction.getIdentifier().toString().toLowerCase()
				,"identifier",data.getIdentifier().toString());
		url = uniformResourceIdentifierStringBuilder.execute().getOutput();
		return url;
	}

}
