package org.cyk.utility.__kernel__.object.__static__.representation;

import java.util.ArrayList;
import java.util.List;

import org.cyk.utility.__kernel__.object.Objectable;

/**
 * Collection of element
 * @author CYK
 *
 * @param <ELEMENT>
 */
public interface Collection<ELEMENT> extends Objectable {

	java.util.Collection<ELEMENT> getElements();
	Collection<ELEMENT> setElements(java.util.Collection<ELEMENT> elements);
	
	default Collection<ELEMENT> add(java.util.Collection<ELEMENT> elements) {
		if(elements != null && !elements.isEmpty()) {
			java.util.Collection<ELEMENT> __elements__ = getElements();
			if(__elements__ == null)
				setElements(__elements__ = new ArrayList<ELEMENT>());
			__elements__.addAll(elements);
		}
		return this;
	}
	
	default Collection<ELEMENT> add(@SuppressWarnings("unchecked") ELEMENT...elements) {
		if(elements != null && elements.length > 0)
			add(List.of(elements));
		return this;
	}

}
