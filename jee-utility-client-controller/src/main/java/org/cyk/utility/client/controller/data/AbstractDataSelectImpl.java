package org.cyk.utility.client.controller.data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceMany;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyAutoComplete;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneAutoComplete;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

import lombok.ToString;

@ToString
public abstract class AbstractDataSelectImpl<DATA> extends AbstractDataImpl implements DataSelect<DATA>,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputChoice @InputChoiceOne @InputChoiceOneAutoComplete
	private DATA one;
	
	@Input @InputChoice @InputChoiceMany @InputChoiceManyAutoComplete
	private Collection<DATA> many;

	@Override
	public DATA getOne() {
		return one;
	}

	@Override
	public DataSelect<DATA> setOne(DATA one) {
		this.one = one;
		return this;
	}

	@Override
	public Collection<DATA> getMany() {
		return many;
	}

	@Override
	public DataSelect<DATA> setMany(Collection<DATA> many) {
		this.many = many;
		return this;
	}
	
	@Override
	public Collection<Object> getSystemIdentifiers() {
		Collection<Object> identifiers = null;
		DATA one = getOne();
		if(one != null) {
			if(identifiers == null)
				identifiers = new ArrayList<Object>();
			identifiers.add(FieldHelper.readSystemIdentifier(one));
		}
		Collection<DATA> many = getMany();
		if(Boolean.TRUE.equals( CollectionHelper.isNotEmpty(many))) {
			if(identifiers == null)
				identifiers = new ArrayList<Object>();
			for(Object index : many) {
				identifiers.add(FieldHelper.readSystemIdentifier(index));
			}
		}
		return identifiers;
	}
}
