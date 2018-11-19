package org.cyk.utility.client.controller.entities;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.system.action.SystemAction;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MyEntityEditPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__() {
		SystemAction systemAction = __inject__(RequestParameterValueMapper.class).setParameterName(SystemAction.class).execute().getOutputAs(SystemAction.class);
		return __injectControllerLayer__().injectWindowBuilderClassFromEntityClass(
				__inject__(RequestParameterValueMapper.class).setParameterName(Class.class).execute().getOutputAs(Class.class)
				, systemAction)
				.execute().getOutput();
	}
	
}
