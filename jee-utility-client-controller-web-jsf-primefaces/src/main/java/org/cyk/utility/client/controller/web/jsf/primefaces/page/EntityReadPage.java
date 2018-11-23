package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.throwable.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EntityReadPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder windowBuilder = null;
		//WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = __injectControllerLayer__().injectWindowContainerManagedWindowBuilder();
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = __inject__(WindowContainerManagedWindowBuilderGetter.class).execute().getOutput();
		if(windowContainerManagedWindowBuilder==null)
			__inject__(ThrowableHelper.class).throwRuntimeException("No WindowContainerManagedWindowBuilder found");
		else
			windowBuilder = windowContainerManagedWindowBuilder.execute().getOutput();
		
		return windowBuilder;
	}
	
}
