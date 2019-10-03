package org.cyk.utility.__kernel__.field.container;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface FieldContainerCollectionChildren<CHILD> extends FieldContainerCollection {

	@SuppressWarnings("unchecked")
	default Class<CHILD> getChildrenClass() {
		return (Class<CHILD>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	default FieldContainerCollectionChildren<CHILD> setChildren(Collection<CHILD> children) {
		FieldHelper.write(this, FIELD_CHIDLREN, children);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default Collection<CHILD> getChildren() {
		return (Collection<CHILD>) FieldHelper.read(this, FIELD_CHIDLREN);
	}
	
	default Collection<CHILD> getChildren(Boolean injectIfNull) {
		Collection<CHILD> children = getChildren();
		if(children == null && Boolean.TRUE.equals(injectIfNull))
			setChildren(children = new ArrayList<>());
		return children;
	}
	
	default FieldContainerCollectionChildren<CHILD> addChildren(Collection<CHILD> children) {
		if(CollectionHelper.isEmpty(children))
			return this;
		getChildren(Boolean.TRUE).addAll(children);
		return this;
	}
	
	default FieldContainerCollectionChildren<CHILD> addChildren(@SuppressWarnings("unchecked") CHILD...children) {
		if(ArrayHelper.isEmpty(children))
			return this;
		addChildren(CollectionHelper.listOf(children));
		return this;
	}
	
	default FieldContainerCollectionChildren<CHILD> addChildrenByCodes(Collection<String> codes) {
		return addChildren(fetchByBusinessIdentifiers(codes, getChildrenClass()));
	}
	
	default FieldContainerCollectionChildren<CHILD> addChildrenByCodes(String... codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		addChildrenByCodes(CollectionHelper.listOf(codes));
		return this;
	}
	
	default FieldContainerCollectionChildren<CHILD> addChildrenByIdentifiers(Collection<String> identifiers) {
		return addChildren(fetchBySystemIdentifiers(identifiers, getChildrenClass()));
	}
	
	default FieldContainerCollectionChildren<CHILD> addChildrenByIdentifiers(String... identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return this;
		addChildrenByIdentifiers(CollectionHelper.listOf(identifiers));
		return this;
	}
	
	String FIELD_CHIDLREN = "children";
	
}
