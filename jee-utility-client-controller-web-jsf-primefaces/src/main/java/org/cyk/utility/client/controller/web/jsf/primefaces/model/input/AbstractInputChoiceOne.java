package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractInputChoiceOne extends AbstractInputChoice<Object> implements Serializable {

	/**/
	
	public AbstractInputChoiceOne enableAjaxListener(String event,Ajax.Listener listener,Collection<Object> updatables) {
		if(StringHelper.isBlank(event))
			throw new RuntimeException("Ajax event name is required");
		Ajax ajax = getAjaxes().get(event);
		ajax.setDisabled(Boolean.FALSE).setListener(listener);
		if(CollectionHelper.isNotEmpty(updatables))
			for(Object updatable : updatables) {
				if(updatable == null)
					continue;
				String string = null;
				if(updatable instanceof AbstractObject)
					string = ((AbstractObject)updatable).getIdentifier();
				else
					string = updatable.toString();
				if(!string.startsWith(":form:"))
					string = ":form:"+string;
				ajax.addUpdates(string);
			}
		return this;
	}
	
	public AbstractInputChoiceOne enableValueChangeListener(Collection<Object> updatables) {
		enableAjaxListener("valueChange", new Ajax.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				select(FieldHelper.read(action.get__argument__(), "source.value"));
			}
		},updatables);
		return this;
	}
	
	public AbstractInputChoiceOne enableChangeListener(Collection<Object> updatables) {
		enableAjaxListener("change", new Ajax.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				select(FieldHelper.read(action.get__argument__(), "source.value"));
			}
		},updatables);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractInputChoiceOne select(Object choice) {
		if(choice != null && CollectionHelper.isEmpty(choices))
			return this;
		((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).select(this, choice);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractInputChoiceOne selectBySystemIdentifier(Object systemIdentifier) {
		if(CollectionHelper.isEmpty(choices) || systemIdentifier == null)
			return this;
		Object choice = null;
		for(Object index : choices)
			if(systemIdentifier.equals(FieldHelper.readSystemIdentifier(index))) {
				choice = index;
				break;
			}
		if(choice == null) {
			LogHelper.logWarning("No choice with system identifier "+systemIdentifier+" found in choices", getClass());
			return this;
		}
		((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).select(this, choice);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractInputChoiceOne selectByBusinessIdentifier(Object businessIdentifier) {
		if(CollectionHelper.isEmpty(choices) || businessIdentifier == null)
			return this;
		Object choice = null;
		for(Object index : choices)
			if(businessIdentifier.equals(FieldHelper.readBusinessIdentifier(index))) {
				choice = index;
				break;
			}
		if(choice == null) {
			LogHelper.logWarning("No choice with business identifier "+businessIdentifier+" found in choices", getClass());
			return this;
		}
		((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).select(this, choice);
		return this;
	}
	
	public AbstractInputChoiceOne selectFirstChoice() {
		select(CollectionHelper.getFirst(choices));
		return this;
	}
	
	/**/
	
	
	/**/
	
	public static interface Listener<VALUE> extends AbstractInputChoice.Listener<VALUE>  {
		
		void select(AbstractInputChoiceOne input,VALUE choice);
		//Boolean isFirstChoiceSelectable(AbstractInputChoice<VALUE> input,Collection<VALUE> values);
		
		public static abstract class AbstractImpl<VALUE> extends AbstractInputChoice.Listener.AbstractImpl<VALUE> implements Listener<VALUE>,Serializable {
			
			@Override
			public void select(AbstractInputChoiceOne input, VALUE choice) {
				input.setValue(choice);
			}
			/*
			@Override
			public Boolean isFirstChoiceSelectable(AbstractInputChoice<VALUE> input, Collection<VALUE> values) {
				return Boolean.TRUE;
			}*/
			/*
			@Override
			protected Collection<VALUE> __computeChoices__(AbstractInputChoice<VALUE> input, Class<?> entityClass) {
				Collection<VALUE> values = super.__computeChoices__(input, entityClass);
				if(Boolean.TRUE.equals(isFirstChoiceSelectable(input, values)))
					((AbstractInputChoiceOne)input).selectFirstChoice();
				return values;
			}*/
			
			public static class DefaultImpl extends Listener.AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}

	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInputChoiceOne> extends AbstractInputChoice.AbstractConfiguratorImpl<INPUT,Object> implements Serializable {

		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_IS_FIRST_CHOICE_SELECTABLE))) {
				input.selectFirstChoice();
			}	
		}
		
		public static final String FIELD_IS_FIRST_CHOICE_SELECTABLE = "isFirstChoiceSelectable";
	}
}