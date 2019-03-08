package org.cyk.utility.client.controller.component.grid.column;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;

public class ColumnBuildersImpl extends AbstractCollectionInstanceImpl<ColumnBuilder> implements ColumnBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ColumnBuilder getByFieldNameStrings(Collection<String> fieldNameStrings) {
		if(__inject__(CollectionHelper.class).isNotEmpty(fieldNameStrings) && __inject__(CollectionHelper.class).isNotEmpty(this.collection))
			for(ColumnBuilder index : this.collection) {
				Strings indexFieldNameStrings = index.getFieldNameStrings();
				if(indexFieldNameStrings!=null) {
					String name = __inject__(FieldHelper.class).join(fieldNameStrings);
					if(__inject__(StringHelper.class).isNotBlank(name) && name.equals( __inject__(FieldHelper.class).join(indexFieldNameStrings.get())))
						return index;
				}
			}
		return null;
	}

	@Override
	public ColumnBuilder getByFieldNameStrings(String... fieldNameStrings) {
		return getByFieldNameStrings(__inject__(CollectionHelper.class).instanciate(fieldNameStrings));
	}

}
