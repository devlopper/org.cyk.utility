package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface AuditReader {

	<T> Collection<T> read(Class<T> klass,Arguments<T> arguments);
	<T> Collection<T> read(Class<T> klass,Collection<Object> identifiers);
	<T> Collection<T> readByIdentifier(Class<T> klass,Object identifier);
	<T> Collection<T> readByDates(Class<T> klass,Object fromDate,Object toDate);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements AuditReader,Serializable {
		
		@Override
		public <T> Collection<T> read(Class<T> klass,Arguments<T> arguments) {
			if(klass == null || arguments == null)
				return null;
			Collection<T> collection = __read__(klass, arguments);
			if(CollectionHelper.isNotEmpty(collection)) {
				Boolean auditRecordIdentityComputable = ValueHelper.defaultToIfNull(arguments.getAuditRecordIdentityComputable(), Boolean.TRUE);
				if(Boolean.TRUE.equals(auditRecordIdentityComputable))
					computeIdentities(klass, arguments, collection);
				if(CollectionHelper.isNotEmpty(arguments.getIdentifiables())) {
					String auditsRecordsCollectionFieldName = arguments.getAuditsRecordsCollectionFieldName();
					if(StringHelper.isBlank(auditsRecordsCollectionFieldName))
						auditsRecordsCollectionFieldName = AuditableWhoDoneWhatWhen.FIELD___AUDIT_RECORDS__;
					if(Boolean.TRUE.equals(arguments.getAuditsRecordsCollectionSettable()))
						__setAuditsRecordsCollection__(klass, arguments, collection,auditsRecordsCollectionFieldName);
					__process__(klass, arguments.getIdentifiables(),auditsRecordsCollectionFieldName);
				}
			}			
			return collection;
		}
		
		protected <T> Collection<T> __read__(Class<T> klass, Arguments<T> arguments) {
			Collection<T> collection = null;
			Collection<Object> identifiers = arguments.computeIdentifiers();
			if(arguments.getIsReadableByIdentifiers() == null)
				arguments.setIsReadableByIdentifiers(CollectionHelper.isNotEmpty(identifiers));
			if(arguments.getIsReadableByDates() == null)
				arguments.setIsReadableByDates(arguments.getFromDate() != null || arguments.getToDate() != null);
			
			if(Boolean.TRUE.equals(arguments.getIsReadableByIdentifiers()) && !Boolean.TRUE.equals(arguments.getIsReadableByDates())) {
				Collection<T> result = __readByIdentifiers__(klass, arguments,identifiers);
				if(CollectionHelper.isNotEmpty(result)) {
					if(collection == null)
						collection = new ArrayList<>();
					collection.addAll(result);
				}
			}
			
			if(Boolean.TRUE.equals(arguments.getIsReadableByDates()) && !Boolean.TRUE.equals(arguments.getIsReadableByIdentifiers())) {
				Collection<T> result = __readByDates__(klass, arguments,arguments.getFromDate(),arguments.getToDate());
				if(CollectionHelper.isNotEmpty(result)) {
					if(collection == null)
						collection = new ArrayList<>();
					collection.addAll(result);
				}
			}
			
			if(arguments.getAuditsRecordsCollectionSettable() == null && !Boolean.TRUE.equals(arguments.getIsReadableByIdentifiers()) 
					&& Boolean.TRUE.equals(arguments.getIsReadableByDates()))
				arguments.setAuditsRecordsCollectionSettable(Boolean.FALSE);
			
			if(CollectionHelper.isNotEmpty(collection)) {
				for(T index : collection) {
					if(index instanceof AuditableWhoDoneWhatWhen) {
						AuditableWhoDoneWhatWhen auditableWhoDoneWhatWhen = (AuditableWhoDoneWhatWhen) index;
						if(auditableWhoDoneWhatWhen.get__auditWhen__() != null) {
							auditableWhoDoneWhatWhen.set__auditWhenAsString__(TimeHelper.formatLocalDateTimeTillMillisecond(auditableWhoDoneWhatWhen.get__auditWhen__()));
						}
					}
				}
			}
			
			return collection;
		}
		
		protected abstract <T> Collection<T> __readByIdentifiers__(Class<T> klass, Arguments<T> arguments,Collection<Object> identifiers);
		
		protected abstract <T> Collection<T> __readByDates__(Class<T> klass, Arguments<T> arguments,LocalDateTime fromDate,LocalDateTime toDate);
		
		protected <T> void computeIdentities(Class<T> klass, Arguments<T> arguments,Collection<T> records) {
			Collection<String> identifiers = null;
			for(T record : records) {
				String identifier = null;
				if(record instanceof AuditableWhoDoneWhatWhen)
					identifier = ((AuditableWhoDoneWhatWhen)record).get__auditWho__();
				if(StringHelper.isNotBlank(identifier)) {
					if(identifiers == null)
						identifiers = new ArrayList<>();
					identifiers.add(identifier);
				}
			}
			if(CollectionHelper.isEmpty(identifiers))
				return;
			Collection<AuditIdentity> identities =  computeIdentities(identifiers);			
			for(T record : records) {
				String identifier = null;
				if(record instanceof AuditableWhoDoneWhatWhen)
					identifier = ((AuditableWhoDoneWhatWhen)record).get__auditWho__();
				if(StringHelper.isNotBlank(identifier)) {
					AuditIdentity identity = AuditIdentity.getByIdentifier(identities, identifier);
					String who = identity == null ? "???"+identifier+"???" : identity.toString();
					if(record instanceof AuditableWhoDoneWhatWhen)
						((AuditableWhoDoneWhatWhen)record).set__auditWho__(who);
				}
			}
		}
		
		protected Collection<AuditIdentity> computeIdentities(Collection<String> identifiers) {
			return identifiers.stream().map(identifier -> new AuditIdentity().setIdentifier(identifier)).collect(Collectors.toList());
		}
		
		protected <T> void __setAuditsRecordsCollection__(Class<T> klass, Arguments<T> arguments,Collection<T> collection,String auditsRecordsCollectionFieldName) {
			for(T identifiable : arguments.getIdentifiables()) {
				Object identifier = FieldHelper.readSystemIdentifier(identifiable);
				if(identifier == null)
					continue;
				Collection<T> records = collection.stream().filter(record -> identifier.equals(FieldHelper.readSystemIdentifier(record))).collect(Collectors.toList());
				if(CollectionHelper.isEmpty(records))
					continue;
				FieldHelper.write(identifiable, auditsRecordsCollectionFieldName, records);
			}
		}
		
		protected <T> void __process__(Class<T> klass,Collection<T> identifiables,String auditsRecordsCollectionFieldName) {
			if(CollectionHelper.isEmpty(identifiables))
				return;
			for(T identifiable : identifiables) {
				if(identifiable == null)
					continue;
				Collection<T> auditsRecords = (Collection<T>) FieldHelper.read(identifiable, auditsRecordsCollectionFieldName);
				if(CollectionHelper.isEmpty(auditsRecords))
					continue;
				try {
					__process__(klass, identifiable,auditsRecords);
				} catch (Exception exception) {
					LogHelper.log(exception, getClass());
				}
			}
		}
		
		protected <T> void __process__(Class<T> klass,T identifiable,Collection<T> auditsRecords) {
			
		}
		
		@Override
		public <T> Collection<T> read(Class<T> klass, Collection<Object> identifiers) {
			if(klass == null || CollectionHelper.isEmpty(identifiers))
				return null;
			return read(klass, new Arguments<T>().setIdentifiers(identifiers));
		}
		
		@Override
		public <T> Collection<T> readByIdentifier(Class<T> klass, Object identifier) {
			if(klass == null || identifier == null)
				return null;
			return read(klass, List.of(identifier));
		}

		@Override
		public <T> Collection<T> readByDates(Class<T> klass, Object fromDate, Object toDate) {
			if(klass == null)
				return null;
			return read(klass, new Arguments<T>().setFromDate(ValueConverter.getInstance().convert(fromDate, LocalDateTime.class))
					.setToDate(ValueConverter.getInstance().convert(toDate, LocalDateTime.class)).setIsReadableByDates(Boolean.TRUE));
		}
	}
	
	/**/
	
	/**/
	
	static AuditReader getInstance() {
		return Helper.getInstance(AuditReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}