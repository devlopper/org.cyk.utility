package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.klass.PersistableClassesGetter;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.entities.MyEntityDetail;
import org.cyk.utility.server.persistence.entities.Namable;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;

@ApplicationScoped
public class ApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListenerEntities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		PersistableClassesGetter.COLLECTION.set(List.of(NodeHierarchy.class,Node.class,MyEntityDetail.class,MyEntity.class,Namable.class));
		super.__initialize__(object);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
}