package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.bean.AbstractPropertyValueGetterImpl;
import org.cyk.utility.bean.Property;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.CommandButtonBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.MenuBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.OrganigramNodeBuilder;
import org.cyk.utility.throwable.ThrowableHelper;

@Default
public class PropertyValueGetterImpl extends AbstractPropertyValueGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __derive__(Property property) {
		if(property.getValue() instanceof Component) {
			if(property.getValue() instanceof Menu)
				return __inject__(MenuBuilder.class).setModel((Menu) property.getValue()).execute().getOutput();
			if(property.getValue() instanceof Commandable)
				return __inject__(CommandButtonBuilder.class).setModel((Commandable) property.getValue()).execute().getOutput();
			if(property.getValue() instanceof Tree)
				// TODO : we must decide based on node type
				return __inject__(OrganigramNodeBuilder.class).setHierarchyNode(((Tree)property.getValue()).getRoot().getHierarchyNode()).execute().getOutput();
			if(property.getValue() instanceof Grid)
				//TODO : we must write a builder for it
				return ComponentTargetModelBuilderFunctionRunnableImpl.__build__( (Grid)property.getValue() );
			__inject__(ThrowableHelper.class).throwRuntimeException("Derivation of component "+property.getValue()+" not yet handled.");
		}
		
		return super.__derive__(property);
	}
	
}
