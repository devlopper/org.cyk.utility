package org.cyk.utility.playground.server;

import java.io.Serializable;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.deployment.AbstractServletContextListener;

@WebListener
public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(ServletContext context) {
		super.__initialize__(context);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		/*Collection<MyEntity> myEntities = new ArrayList<>();
		for(Integer index = 0;index < 100;index = index + 1) {
			myEntities.add(new MyEntity().setIdentifier(index.toString()).setCode(__inject__(RandomHelper.class).getAlphanumeric(4)).setName(__inject__(RandomHelper.class).getAlphanumeric(10)));
		}
		
		java.lang.System.out.println("Creating "+myEntities.size()+" myEntities");
		__inject__(MyEntityBusiness.class).createMany(myEntities);
		
		
		Collection<Node> nodes = new ArrayList<>();
		for(Integer index = 0 ; index < 7 ; index = index + 1) {
			String code = index.toString();
			String name = __inject__(RandomHelper.class).getAlphanumeric(4);
			Node node = new Node().setCode(code).setName(name);
			nodes.add(node);
			__instanciateChildren__(node, 3, nodes);
		}
		
		java.lang.System.out.println("Creating "+nodes.size()+" nodes");
		__inject__(NodeBusiness.class).createMany(nodes);
		*/
	}
	
	private void __instanciateChildren__(Node parent,Integer level,Collection<Node> nodes) {
		if(level!=null && level>0)
			for(Integer index = 0 ; index < 5 ; index = index + 1) {
				String code = parent.getCode()+"."+index.toString();
				String name = RandomHelper.getAlphanumeric(4);
				Node node = new Node().setCode(code).setName(name).addParents(parent);
				nodes.add(node);
				__instanciateChildren__(node, level-1, nodes);
			}
	}
	
}
