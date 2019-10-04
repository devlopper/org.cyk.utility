package org.cyk.utility.__kernel__.field.container;
import java.util.ArrayList;
import java.util.Collection;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface FieldContainerCollectionFunctionScopes<FUNCTION_SCOPE> extends FieldContainerCollection {

	@SuppressWarnings("unchecked")
	@JsonbTransient
	@XmlTransient
	default Class<FUNCTION_SCOPE> getFunctionScopeClass() {
		return (Class<FUNCTION_SCOPE>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	default FieldContainerCollectionFunctionScopes<FUNCTION_SCOPE> setFunctionScopes(Collection<FUNCTION_SCOPE> functionScopes) {
		FieldHelper.write(this, FIELD_FUNCTION_SCOPES, functionScopes);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default Collection<FUNCTION_SCOPE> getFunctionScopes() {
		return (Collection<FUNCTION_SCOPE>) FieldHelper.read(this, FIELD_FUNCTION_SCOPES);
	}
	
	default Collection<FUNCTION_SCOPE> getFunctionScopes(Boolean injectIfNull) {
		Collection<FUNCTION_SCOPE> functionScopes = getFunctionScopes();
		if(functionScopes == null && Boolean.TRUE.equals(injectIfNull))
			setFunctionScopes(functionScopes = new ArrayList<>());
		return functionScopes;
	}
	
	default FieldContainerCollectionFunctionScopes<FUNCTION_SCOPE> addFunctionScopes(Collection<FUNCTION_SCOPE> functionScopes) {
		if(CollectionHelper.isEmpty(functionScopes))
			return this;
		getFunctionScopes(Boolean.TRUE).addAll(functionScopes);
		return this;
	}
	
	default FieldContainerCollectionFunctionScopes<FUNCTION_SCOPE> addFunctionScopes(@SuppressWarnings("unchecked") FUNCTION_SCOPE...functionScopes) {
		if(ArrayHelper.isEmpty(functionScopes))
			return this;
		addFunctionScopes(CollectionHelper.listOf(functionScopes));
		return this;
	}
	
	default FieldContainerCollectionFunctionScopes<FUNCTION_SCOPE> addFunctionScopesByCodes(Collection<String> codes) {
		return addFunctionScopes(fetchByBusinessIdentifiers(codes, getFunctionScopeClass()));
	}
	
	default FieldContainerCollectionFunctionScopes<FUNCTION_SCOPE> addFunctionScopesByCodes(String... codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		addFunctionScopesByCodes(CollectionHelper.listOf(codes));
		return this;
	}
	
	String FIELD_FUNCTION_SCOPES = "functionScopes";
	
}
