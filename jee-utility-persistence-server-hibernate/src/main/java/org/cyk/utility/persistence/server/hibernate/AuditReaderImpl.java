package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.server.audit.AuditReader;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.hibernate.envers.AuditReaderFactory;

@Hibernate
public class AuditReaderImpl extends AuditReader.AbstractImpl implements Serializable {

	@Override
	protected <T> Collection<T> __readByIdentifiers__(Class<T> klass, Arguments arguments) {
		Collection<T> collection = null;
		org.hibernate.envers.AuditReader reader = AuditReaderFactory.get(EntityManagerGetter.getInstance().get());
		for(Object identifier : arguments.getIdentifiers()) {
			//Get all revisions numbers by identifier
			List<Number> numbers = reader.getRevisions(klass, identifier);
			if(CollectionHelper.isEmpty(numbers))
				continue;
			for(Number number : numbers) {
				//Get instance by identifier and revision number
				T instance = reader.find(klass, identifier, number);
				if(instance == null)
					continue;
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(instance);
			}
		}
		return collection;
	}
}