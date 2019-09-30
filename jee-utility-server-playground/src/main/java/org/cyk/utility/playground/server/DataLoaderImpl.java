package org.cyk.utility.playground.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.playground.server.business.api.PersonBusiness;
import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.representation.impl.AbstractDataLoaderImpl;

@org.cyk.utility.playground.server.System
public class DataLoaderImpl extends AbstractDataLoaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Response __execute__() throws Exception {
		Collection<Person> persons = new ArrayList<>();
		String codePrefix = RandomHelper.getAlphanumeric(4);
		for(Integer index = 0 ; index < 1000 ; index = index + 1) {
			Person person = new Person();
			person.setCode("P_"+codePrefix+"_"+index);
			person.setFirstName(RandomHelper.getAlphabetic(5));
			person.setLastNames(RandomHelper.getAlphabetic(8));
			persons.add(person);
		}
		__logInfo__("Persisting "+persons.size()+" persons");
		__inject__(PersonBusiness.class).createByBatch(persons, 100);
		return Response.ok().build();
	}
	
}