package org.cyk.utility.client.controller.component.grid.column;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;

public class ColumnBuildersImpl extends AbstractCollectionInstanceImpl<ColumnBuilder> implements ColumnBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ColumnBuilder getByFieldNameStrings(Collection<String> fieldNameStrings) {
		if(CollectionHelper.isNotEmpty(fieldNameStrings) && CollectionHelper.isNotEmpty(this.collection))
			for(ColumnBuilder index : this.collection) {
				Strings indexFieldNameStrings = index.getFieldNameStrings();
				if(indexFieldNameStrings!=null) {
					String name = FieldHelper.join(fieldNameStrings);
					if(StringHelper.isNotBlank(name) && name.equals( FieldHelper.join(indexFieldNameStrings.get())))
						return index;
				}
			}
		return null;
	}

	@Override
	public ColumnBuilder getByFieldNameStrings(String... fieldNameStrings) {
		return getByFieldNameStrings(CollectionHelper.listOf(fieldNameStrings));
	}

}
