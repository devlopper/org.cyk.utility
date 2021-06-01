package org.cyk.utility.__kernel__.object.marker;

import java.util.Collection;
import java.util.UUID;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

/**
 * Capable of being system identified.
 * @author CYK
 *
 * @param <IDENTIFIER>
 */
public interface IdentifiableSystem<IDENTIFIER> extends Identifiable<IDENTIFIER> {

	IDENTIFIER getSystemIdentifier();
	IdentifiableSystem<IDENTIFIER> setSystemIdentifier(IDENTIFIER identifier);
	
	/**/
	
	static void setManyIfNull(Collection<?> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		objects.stream().forEach(object -> {
			setOneIfNull(object);
		});
	}
	
	static void setOneIfNull(Object object) {
		if(!(object instanceof IdentifiableSystem))
			return;
		Object identifier = FieldHelper.readSystemIdentifier(object);
		if(identifier != null)
			return;			
		//if business identifier is not null then we assign business identifier to system identifier else we generate one			
		identifier = FieldHelper.readBusinessIdentifier(object);
		if(identifier == null)
			identifier = generateRandomly();			
		((IdentifiableSystem<Object>)object).setSystemIdentifier(identifier);				
	}
	
	static String generateRandomly() {
		return UUID.randomUUID().toString();					
	}
	
	static Boolean areIdentifiersEqual(IdentifiableSystem<?> instance1,IdentifiableSystem<?> instance2) {
		Object identifier1 = FieldHelper.readSystemIdentifier(instance1);
		Object identifier2 = FieldHelper.readSystemIdentifier(instance2);
		if(identifier1 == null)
			return identifier2 == null;
		return identifier1.equals(identifier2);		
	}
}