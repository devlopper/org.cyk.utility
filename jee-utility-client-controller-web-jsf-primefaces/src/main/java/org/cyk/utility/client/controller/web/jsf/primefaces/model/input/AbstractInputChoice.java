package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.controller.EntityReader;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractInputChoice<VALUE> extends AbstractInput<VALUE> implements Serializable {

	protected Collection<Object> choices;
	protected Integer columns;
	protected Boolean disabled;
	protected String layout;
	
	/**/
	
	public Object getChoiceValue(Object choice) {
		return choice;
	}
	
	public Object getChoiceLabel(Object choice) {
		return choice;
	}
	
	public Object getChoiceDescription(Object choice) {
		return null;
	}
	
	public Object getIsChoiceDisabled(Object choice) {
		return Boolean.FALSE;
	}
	
	public Boolean getIsChoiceLabelEscaped(Object choice) {
		return Boolean.FALSE;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getChoices() {
		return ((Listener<VALUE>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getChoices(this);
	}
	
	/**/
	
	public static final String FIELD_CHOICES = "choices";
	public static final String FIELD_COLUMNS = "columns";
	public static final String FIELD_DISBALED = "disabled";
	public static final String FIELD_LAYOUT = "layout";
	
	/**/
	
	public static interface Listener<VALUE> extends AbstractInput.Listener {
		
		Collection<Object> getChoices(AbstractInputChoice<VALUE> input);
		
		public static abstract class AbstractImpl<VALUE> extends AbstractInput.Listener.AbstractImpl implements Listener<VALUE>,Serializable {
			@SuppressWarnings("unchecked")
			@Override
			public Collection<Object> getChoices(AbstractInputChoice<VALUE> input) {
				Class<?> entityClass = null;
				if(input.choices == null) {
					if(ClassHelper.isInstanceOf(input.field.getType(),Collection.class)) {
						entityClass = ClassHelper.getParameterAt(input.field.getType(), 0);
					}else {
						entityClass = input.field.getType();
					}
					input.choices = (Collection<Object>) EntityReader.getInstance().readMany(entityClass);
					if(input.choices == null)
						input.choices = new ArrayList<>();
				}
				/*if(input.converter == null) {
					if(!ClassHelper.isBelongsToJavaPackages(entityClass))
						input.converter = DependencyInjection.inject(ObjectConverter.class);
				}*/
				return input.choices;
			}
			
			public static class DefaultImpl extends Listener.AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}

	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInputChoice<VALUE>,VALUE> extends AbstractInput.AbstractConfiguratorImpl<INPUT> implements Serializable {

		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
			if(StringHelper.isBlank(input.layout))
				input.layout = "responsive";
			if(input.columns == null) {
				if("responsive".equals(input.layout))
					input.columns = 4;
				else
					input.columns = 0;
			}
			if(input.converter == null) {
				Class<?> entityClass = null;
				if(CollectionHelper.isEmpty(input.choices)) {
					if(input.field != null) {
						if(ClassHelper.isInstanceOf(input.field.getType(),Collection.class)) {
							entityClass = ClassHelper.getParameterAt(input.field.getType(), 0);
						}else {
							entityClass = input.field.getType();
						}		
					}				
				}else {
					entityClass = input.choices.iterator().next().getClass();
				}				
				if(!ClassHelper.isBelongsToJavaPackages(entityClass))
					input.converter = DependencyInjection.inject(ObjectConverter.class);
			}
		}
	}
}