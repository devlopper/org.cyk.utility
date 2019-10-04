package org.cyk.utility.__kernel__.field.container;
import java.util.ArrayList;
import java.util.Collection;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface FieldContainerCollectionPrivileges<PRIVILEGE> extends FieldContainerCollection {

	@SuppressWarnings("unchecked")
	@JsonbTransient
	@XmlTransient
	default Class<PRIVILEGE> getPrivilegeClass() {
		return (Class<PRIVILEGE>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	default FieldContainerCollectionPrivileges<PRIVILEGE> setPrivileges(Collection<PRIVILEGE> privileges) {
		FieldHelper.write(this, FIELD_PRIVILEGES, privileges);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default Collection<PRIVILEGE> getPrivileges() {
		return (Collection<PRIVILEGE>) FieldHelper.read(this, FIELD_PRIVILEGES);
	}
	
	default Collection<PRIVILEGE> getPrivileges(Boolean injectIfNull) {
		Collection<PRIVILEGE> privileges = getPrivileges();
		if(privileges == null && Boolean.TRUE.equals(injectIfNull))
			setPrivileges(privileges = new ArrayList<>());
		return privileges;
	}
	
	default FieldContainerCollectionPrivileges<PRIVILEGE> addPrivileges(Collection<PRIVILEGE> privileges) {
		if(CollectionHelper.isEmpty(privileges))
			return this;
		getPrivileges(Boolean.TRUE).addAll(privileges);
		return this;
	}
	
	default FieldContainerCollectionPrivileges<PRIVILEGE> addPrivileges(@SuppressWarnings("unchecked") PRIVILEGE...privileges) {
		if(ArrayHelper.isEmpty(privileges))
			return this;
		addPrivileges(CollectionHelper.listOf(privileges));
		return this;
	}
	
	default FieldContainerCollectionPrivileges<PRIVILEGE> addPrivilegesByCodes(Collection<String> codes) {
		return addPrivileges(fetchByBusinessIdentifiers(codes, getPrivilegeClass()));
	}
	
	default FieldContainerCollectionPrivileges<PRIVILEGE> addPrivilegesByCodes(String... codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		addPrivilegesByCodes(CollectionHelper.listOf(codes));
		return this;
	}
	
	String FIELD_PRIVILEGES = "privileges";
	
}
