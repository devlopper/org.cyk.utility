package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Arguments<T> extends AbstractObject implements Serializable {
	private Class<?> klass;
	private Boolean isReadableByIdentifiers,isReadableByDates;
	private Collection<Object> identifiers;
	private Collection<T> identifiables;
	private Boolean auditsRecordsCollectionSettable;
	private String auditsRecordsCollectionFieldName;
	private Boolean auditRecordIdentityComputable;
	private LocalDateTime fromDate,toDate;
	
	private QueryExecutorArguments queryExecutorArguments;
	
	public Collection<Object> computeIdentifiers() {
		Collection<Object> result = null;
		if(CollectionHelper.isNotEmpty(identifiers)) {
			if(result == null)
				result = new ArrayList<>();
			result.addAll(identifiers);
		}
		if(CollectionHelper.isNotEmpty(identifiables)) {
			Collection<Object> _identifiers_ = FieldHelper.readSystemIdentifiers(identifiables);
			if(CollectionHelper.isNotEmpty(_identifiers_)) {
				if(result == null)
					result = new ArrayList<>();
				result.addAll(_identifiers_);
			}				
		}
		return result;
	}
}