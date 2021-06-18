package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface AuditReader {

	<T> Collection<T> read(Class<T> klass,Arguments arguments);
	<T> Collection<T> read(Class<T> klass,Collection<Object> identifiers);
	<T> Collection<T> readByIdentifier(Class<T> klass,Object identifier);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements AuditReader,Serializable {
		
		@Override
		public <T> Collection<T> read(Class<T> klass, Arguments arguments) {
			if(klass == null || arguments == null)
				return null;
			Collection<T> collection = __read__(klass, arguments);
			return collection;
		}
		
		protected <T> Collection<T> __read__(Class<T> klass, Arguments arguments) {
			Collection<T> collection = null;
			if(CollectionHelper.isNotEmpty(arguments.getIdentifiers())) {
				Collection<T> result = __readByIdentifiers__(klass, arguments);
				if(CollectionHelper.isNotEmpty(result)) {
					if(collection == null)
						collection = new ArrayList<>();
					for(T index : result) {
						if(index instanceof AuditableWhoDoneWhatWhen) {
							AuditableWhoDoneWhatWhen auditableWhoDoneWhatWhen = (AuditableWhoDoneWhatWhen) index;
							if(auditableWhoDoneWhatWhen.get__auditWhen__() != null) {
								auditableWhoDoneWhatWhen.set__auditWhenAsString__(TimeHelper.formatLocalDateTime(auditableWhoDoneWhatWhen.get__auditWhen__()));
							}
						}
					}
					collection.addAll(result);
				}
			}
			return collection;
		}
		
		protected abstract <T> Collection<T> __readByIdentifiers__(Class<T> klass, Arguments arguments);
		
		@Override
		public <T> Collection<T> read(Class<T> klass, Collection<Object> identifiers) {
			if(klass == null || CollectionHelper.isEmpty(identifiers))
				return null;
			return read(klass, new Arguments().setIdentifiers(identifiers));
		}
		
		@Override
		public <T> Collection<T> readByIdentifier(Class<T> klass, Object identifier) {
			if(klass == null || identifier == null)
				return null;
			return read(klass, List.of(identifier));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Collection<Object> identifiers;
	}
	
	/**/
	
	static AuditReader getInstance() {
		return Helper.getInstance(AuditReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}