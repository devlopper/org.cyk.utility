package org.cyk.utility.bean;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.clazz.Classes;

public interface Property extends Objectable {

	@Override Property setParent(Object parent);
	
	Object getValue();
	void setValue(Object value);
	
	Boolean getIsDerivable();
	Property setIsDerivable(Boolean isDerivable);
	
	Boolean getIsDerived();
	Property setIsDerived(Boolean isDerived);
	
	Classes getValueGetterClassQualifiers();
	Classes getValueGetterClassQualifiers(Boolean injectIfNull);
	Property setValueGetterClassQualifiers(Classes valueGetterClassQualifiers);
	Property addValueGetterClassQualifiers(@SuppressWarnings("rawtypes") Collection<Class> valueGetterClassQualifiers);
	Property addValueGetterClassQualifiers(@SuppressWarnings("rawtypes") Class...valueGetterClassQualifiers);
	
	Object read();
}
