package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.klass.PersistableClassesGetter;
import org.cyk.utility.server.persistence.AbstractApplicationScopeLifeCycleListenerEntities;

@ApplicationScoped
public class ApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListenerEntities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		super.__initialize__(object);
		PersistableClassesGetter.COLLECTION.set(List.of(MyEntity.class,NodeHierarchy.class,SelectedNode.class,Node.class,Person.class,PersonType.class));
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
}