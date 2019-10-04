package org.cyk.utility.__kernel__.field.container;
import java.util.ArrayList;
import java.util.Collection;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface FieldContainerCollectionFunctions<FUNCTION> extends FieldContainerCollection {

	@SuppressWarnings("unchecked")
	@JsonbTransient
	@XmlTransient
	default Class<FUNCTION> getFunctionClass() {
		return (Class<FUNCTION>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> setFunctions(Collection<FUNCTION> functions) {
		FieldHelper.write(this, FIELD_FUNCTIONS, functions);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default Collection<FUNCTION> getFunctions() {
		return (Collection<FUNCTION>) FieldHelper.read(this, FIELD_FUNCTIONS);
	}
	
	default Collection<FUNCTION> getFunctions(Boolean injectIfNull) {
		Collection<FUNCTION> functions = getFunctions();
		if(functions == null && Boolean.TRUE.equals(injectIfNull))
			setFunctions(functions = new ArrayList<>());
		return functions;
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> addFunctions(Collection<FUNCTION> functions) {
		if(CollectionHelper.isEmpty(functions))
			return this;
		getFunctions(Boolean.TRUE).addAll(functions);
		return this;
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> addFunctions(@SuppressWarnings("unchecked") FUNCTION...functions) {
		if(ArrayHelper.isEmpty(functions))
			return this;
		addFunctions(CollectionHelper.listOf(functions));
		return this;
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> addFunctionsByCodes(Collection<String> codes) {
		return addFunctions(fetchByBusinessIdentifiers(codes, getFunctionClass()));
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> addFunctionsByCodes(String... codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		addFunctionsByCodes(CollectionHelper.listOf(codes));
		return this;
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> addFunctionsByIdentifiers(Collection<String> identifiers) {
		return addFunctions(fetchBySystemIdentifiers(identifiers, getFunctionClass()));
	}
	
	default FieldContainerCollectionFunctions<FUNCTION> addFunctionsByIdentifiers(String... identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return this;
		addFunctionsByIdentifiers(CollectionHelper.listOf(identifiers));
		return this;
	}
	
	String FIELD_FUNCTIONS = "functions";
	
}
