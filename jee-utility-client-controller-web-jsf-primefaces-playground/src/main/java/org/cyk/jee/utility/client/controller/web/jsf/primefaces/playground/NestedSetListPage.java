package org.cyk.jee.utility.client.controller.web.jsf.primefaces.playground;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named @RequestScoped
public class NestedSetListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	/*private List<NestedSet> nestedSets;
	
	public List<NestedSet> getNestedSets() {
		if(nestedSets == null){
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8081"));
			
			NestedSetRepresentation nestedSetRepresentation = target.proxy(NestedSetRepresentation.class);
			Collection<NestedSetDto> dtos = nestedSetRepresentation.getMany().readEntity(new GenericType<Collection<NestedSetDto>>(){});
			nestedSets = (List<NestedSet>) DependencyInjection.inject(InstanceHelper.class).buildMany(NestedSet.class, dtos);
		}
		return nestedSets;
	}*/
}