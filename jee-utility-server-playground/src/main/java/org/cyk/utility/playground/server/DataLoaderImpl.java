package org.cyk.utility.playground.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.cyk.utility.playground.server.business.api.NodeBusiness;
import org.cyk.utility.playground.server.business.api.PersonBusiness;
import org.cyk.utility.playground.server.business.api.PersonTypeBusiness;
import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.representation.AbstractDataLoaderImpl;

@org.cyk.utility.playground.server.System
public class DataLoaderImpl extends AbstractDataLoaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response load() {
		PersonType personTypeManager = new PersonType().setCode("MAN").setName("Manager");
		PersonType personTypeEmployee = new PersonType().setCode("EMP").setName("Employee");
		__inject__(PersonTypeBusiness.class).createMany(List.of(personTypeManager,personTypeEmployee));
		
		Collection<Person> persons = new ArrayList<>();
		String codePrefix = RandomHelper.getAlphanumeric(4);
		for(Integer index = 0 ; index < 1000 ; index = index + 1) {
			Person person = new Person();
			person.setCode("P_"+codePrefix+"_"+index);
			person.setFirstName(RandomHelper.getAlphabetic(5));
			person.setLastNames(RandomHelper.getAlphabetic(8));
			person.setType(RandomHelper.getNumeric(1).intValue() % 2 == 0 ? personTypeManager : personTypeEmployee);
			persons.add(person);
		}
		__logInfo__("Persisting "+persons.size()+" persons");
		__inject__(PersonBusiness.class).createByBatch(persons, 100);
		
		Collection<Node> nodes = new ArrayList<>();
		for(Integer index = 0 ; index < 7 ; index = index + 1) {
			String code = index.toString();
			String name = RandomHelper.getAlphanumeric(4);
			Node node = new Node().setCode(code).setName(name);
			nodes.add(node);
			__instanciateChildren__(node, 3, nodes);
		}		
		java.lang.System.out.println("Creating "+nodes.size()+" nodes");
		__inject__(NodeBusiness.class).createMany(nodes);
		
		return Response.ok().build();
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