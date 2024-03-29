package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.__kernel__.string.Strings;

@Dependent
public class FieldsImpl extends AbstractCollectionInstanceImpl<Field> implements Fields,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Strings getNames() {
		Strings names = null;
		if(CollectionHelper.isNotEmpty(collection)) {
			names = __inject__(Strings.class);
			for(Field index : collection)
				names.add(index.getName());
		}
		return names;
	}
	
	@Override
	public Field getByName(String name) {
		Field field = null;
		if(CollectionHelper.isNotEmpty(collection)) {
			for(Field index : collection)
				if(index.getName().equals(name)) {
					field = index;
					break;
				}
		}
		return field;
	}
	
	@Override
	public Field getByName(Class<?> klass,FieldName fieldName, ValueUsageType valueUsageType) {
		return getByName(FieldHelper.getName(klass, fieldName, valueUsageType));
	}

	@Override
	public Fields removeByNames(Collection<String> names) {
		if(this.collection!=null)
			this.collection.removeIf(new Predicate<Field>() {
				@Override
				public boolean test(Field field) {
					return names.contains(field.getName());
				}
			});
		return this;
	}
	
	@Override
	public Fields removeByNames(String... names) {
		return removeByNames(List.of(names));
	}
	
	@Override
	public Fields removeModifier(Integer modifier) {
		Collection<Field> collection = new ArrayList<Field>();
		if(this.collection!=null) {
			for(Field index : this.collection)
				if((Modifier.isStatic(modifier) && Modifier.isStatic(index.getModifiers())) 
						|| (Modifier.isFinal(modifier) && Modifier.isFinal(index.getModifiers())) )
					collection.add(index);
			this.collection.removeAll(collection);
		}
		return this;
	}

	@Override
	public Fields removeModifierStatic() {
		return removeModifier(Modifier.STATIC);
	}

	@Override
	public Fields removeModifierFinal() {
		return removeModifier(Modifier.FINAL);
	}

}
