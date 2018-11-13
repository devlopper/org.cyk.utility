package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractRowDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierStringBuilder;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.system.action.SystemAction;

public class MyEntityReadRowImpl extends AbstractRowDataImpl<MyEntity> implements MyEntityReadRow,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MyEntityReadRow setData(MyEntity data) {
		return (MyEntityReadRow) super.setData(data);
	}
	
	@Override
	public String getUrlBySystemActionClass(Class<? extends SystemAction> aClass) {
		String url = null;
		Data data = getData();
		SystemAction systemAction = __inject__(aClass);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		systemAction.getEntities(Boolean.TRUE).add(getData());
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		url = __inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier(identifier).execute().getOutput();
		UniformResourceIdentifierStringBuilder uniformResourceIdentifierStringBuilder = __inject__(UniformResourceIdentifierStringBuilder.class);
		uniformResourceIdentifierStringBuilder.setString(url).setParameters("action",systemAction.getIdentifier().toString().toLowerCase(),"identifier",data.getIdentifier().toString());
		url = uniformResourceIdentifierStringBuilder.execute().getOutput();
		return url;
	}
	
}
