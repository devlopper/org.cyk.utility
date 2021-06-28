package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueConverter;

public interface AuditCounter {

	<T> Long count(Class<T> klass,Arguments<T> arguments);
	<T> Long count(Class<T> klass,Collection<Object> identifiers);
	<T> Long countByIdentifier(Class<T> klass,Object identifier);
	<T> Long countByDates(Class<T> klass,Object fromDate,Object toDate);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements AuditCounter,Serializable {
		
		@Override
		public <T> Long count(Class<T> klass,Arguments<T> arguments) {
			if(klass == null || arguments == null)
				return null;
			return __count__(klass, arguments);
		}
		
		protected <T> Long __count__(Class<T> klass, Arguments<T> arguments) {
			Long count = null;
			Collection<Object> identifiers = arguments.computeIdentifiers();
			if(arguments.getIsReadableByIdentifiers() == null)
				arguments.setIsReadableByIdentifiers(CollectionHelper.isNotEmpty(identifiers));
			if(arguments.getIsReadableByDates() == null)
				arguments.setIsReadableByDates(arguments.getFromDate() != null || arguments.getToDate() != null);			
			if(Boolean.TRUE.equals(arguments.getIsReadableByIdentifiers()) && !Boolean.TRUE.equals(arguments.getIsReadableByDates()))
				return __countByIdentifiers__(klass, arguments,identifiers);			
			if(Boolean.TRUE.equals(arguments.getIsReadableByDates()) && !Boolean.TRUE.equals(arguments.getIsReadableByIdentifiers()))
				return __countByDates__(klass, arguments,arguments.getFromDate(),arguments.getToDate());
			return count;
		}
		
		protected abstract <T> Long __countByIdentifiers__(Class<T> klass, Arguments<T> arguments,Collection<Object> identifiers);
		
		protected abstract <T> Long __countByDates__(Class<T> klass, Arguments<T> arguments,LocalDateTime fromDate,LocalDateTime toDate);
		
		@Override
		public <T> Long count(Class<T> klass, Collection<Object> identifiers) {
			if(klass == null || CollectionHelper.isEmpty(identifiers))
				return null;
			return count(klass, new Arguments<T>().setIdentifiers(identifiers));
		}
		
		@Override
		public <T> Long countByIdentifier(Class<T> klass, Object identifier) {
			if(klass == null || identifier == null)
				return null;
			return count(klass, List.of(identifier));
		}

		@Override
		public <T> Long countByDates(Class<T> klass, Object fromDate, Object toDate) {
			if(klass == null)
				return null;
			return count(klass, new Arguments<T>().setFromDate(ValueConverter.getInstance().convert(fromDate, LocalDateTime.class))
					.setToDate(ValueConverter.getInstance().convert(toDate, LocalDateTime.class)).setIsReadableByDates(Boolean.TRUE));
		}
		
		/**/
		
		protected Class<?> getAuditClass(Class<?> klass) {
			if(klass == null)
				return null;
			return ClassHelper.getByName(klass.getName()+"Audit",Boolean.FALSE);
		}
	}
	
	/**/
	
	/**/
	
	static AuditCounter getInstance() {
		return Helper.getInstance(AuditCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}