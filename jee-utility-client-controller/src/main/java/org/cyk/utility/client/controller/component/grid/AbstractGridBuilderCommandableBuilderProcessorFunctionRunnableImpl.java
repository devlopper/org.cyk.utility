package org.cyk.utility.client.controller.component.grid;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.window.WindowRenderType;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeDialog;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeNormal;

@Deprecated
public abstract class AbstractGridBuilderCommandableBuilderProcessorFunctionRunnableImpl extends AbstractFunctionRunnableImpl<GridBuilderCommandableBuilderProcessor> implements Serializable {
	private static final long serialVersionUID = 1L;

	public AbstractGridBuilderCommandableBuilderProcessorFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				GridBuilderCommandableBuilderProcessor function = getFunction();
				GridBuilder gridBuilder = getFunction().getGridBuilder();
				CommandableBuilder commandableBuilder = function.getCommandableBuilder();
				Class<? extends WindowRenderType> windowRenderTypeClass = commandableBuilder.getWindowRenderTypeClass();
				if(windowRenderTypeClass == null || ClassHelper.isInstanceOf(windowRenderTypeClass, WindowRenderTypeNormal.class)) {
					;//commandableBuilder.getNavigation(Boolean.TRUE).setIdentifierBuilderSystemAction(__inject__(SystemActionCreate.class).setEntityClass(gridBuilder.getRowDataClass()));
				}else if(ClassHelper.isInstanceOf(windowRenderTypeClass, WindowRenderTypeDialog.class)){
					__runWithWindowRenderTypeDialog__(gridBuilder, commandableBuilder);
				}
				setOutput(commandableBuilder);
			}
		});
	}
	
	protected void __runWithWindowRenderTypeDialog__(GridBuilder gridBuilder,CommandableBuilder commandableBuilder) {
		
	}
}
