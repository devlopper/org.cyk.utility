package org.cyk.utility.__kernel__.field.container;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface FieldContainerCollectionScopes<SCOPE> extends FieldContainerCollection {

	@SuppressWarnings("unchecked")
	default Class<SCOPE> getScopeClass() {
		return (Class<SCOPE>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	default FieldContainerCollectionScopes<SCOPE> setScopes(Collection<SCOPE> scopes) {
		FieldHelper.write(this, FIELD_SCOPES, scopes);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default Collection<SCOPE> getScopes() {
		return (Collection<SCOPE>) FieldHelper.read(this, FIELD_SCOPES);
	}
	
	default Collection<SCOPE> getScopes(Boolean injectIfNull) {
		Collection<SCOPE> scopes = getScopes();
		if(scopes == null && Boolean.TRUE.equals(injectIfNull))
			setScopes(scopes = new ArrayList<>());
		return scopes;
	}
	
	default FieldContainerCollectionScopes<SCOPE> addScopes(Collection<SCOPE> scopes) {
		if(CollectionHelper.isEmpty(scopes))
			return this;
		getScopes(Boolean.TRUE).addAll(scopes);
		return this;
	}
	
	default FieldContainerCollectionScopes<SCOPE> addScopes(@SuppressWarnings("unchecked") SCOPE...scopes) {
		if(ArrayHelper.isEmpty(scopes))
			return this;
		addScopes(CollectionHelper.listOf(scopes));
		return this;
	}
	
	default FieldContainerCollectionScopes<SCOPE> addScopesByIdentifiers(Collection<String> identifiers) {
		return addScopes(fetchBySystemIdentifiers(identifiers, getScopeClass()));
	}
	
	default FieldContainerCollectionScopes<SCOPE> addScopesByIdentifiers(String... identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return this;
		addScopesByIdentifiers(CollectionHelper.listOf(identifiers));
		return this;
	}
	
	default FieldContainerCollectionScopes<SCOPE> addScopesByCodes(Collection<String> codes) {
		return addScopes(fetchByBusinessIdentifiers(codes, getScopeClass()));
	}
	
	default FieldContainerCollectionScopes<SCOPE> addScopesByCodes(String... codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		addScopesByCodes(CollectionHelper.listOf(codes));
		return this;
	}
	
	String FIELD_SCOPES = "scopes";
	
}
