package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.controller.EntityReader;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.converter.Converter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractInputChoice<VALUE> extends AbstractInput<VALUE> implements Serializable {

	protected Class<?> choiceClass;
	protected Collection<Object> choices;
	protected Boolean choicesInitialized;
	protected Integer columns;
	protected Boolean nullable;
	protected String layout;
	
	/**/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getChoiceValue(Object choice) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getChoiceValue(this, choice);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getChoiceLabel(Object choice) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getChoiceLabel(this, choice);
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
		if(Boolean.TRUE.equals(choicesInitialized))
			return choices;
		choices = (Collection<Object>) ((Listener<VALUE>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).computeChoices(this);
		choicesInitialized = Boolean.TRUE;
		return choices;
	}
	
	public AbstractInputChoice<VALUE> updateChoices() {
		setChoices(null);
		setChoicesInitialized(null);
		getChoices();
		return this;
	}
	
	/**/
	
	public static final String FIELD_CHOICE_CLASS = "choiceClass";
	public static final String FIELD_CHOICES = "choices";
	public static final String FIELD_COLUMNS = "columns";
	public static final String FIELD_DISBALED = "disabled";
	public static final String FIELD_NULLABLE = "nullable";
	public static final String FIELD_LAYOUT = "layout";
	
	/**/
	
	public static interface Listener<VALUE> extends AbstractInput.Listener {
		
		Collection<VALUE> computeChoices(AbstractInputChoice<VALUE> input);
		
		Object getChoiceLabel(AbstractInputChoice<VALUE> input,VALUE choice);
		Object getChoiceValue(AbstractInputChoice<VALUE> input,VALUE choice);
		
		public static abstract class AbstractImpl<VALUE> extends AbstractInput.Listener.AbstractImpl implements Listener<VALUE>,Serializable {
			@Override
			public Collection<VALUE> computeChoices(AbstractInputChoice<VALUE> input) {
				Class<?> entityClass = input.choiceClass;
				if(entityClass == null) {
					if(ClassHelper.isInstanceOf(input.field.getType(),Collection.class)) {
						entityClass = ClassHelper.getParameterAt(input.field.getType(), 0);
					}else {
						entityClass = input.field.getType();
					}
				}
				return __computeChoices__(input, entityClass);
			}
			
			@SuppressWarnings("unchecked")
			protected Collection<VALUE> __computeChoices__(AbstractInputChoice<VALUE> input,Class<?> entityClass) {
				if(entityClass == null) {
					LogHelper.logWarning("Choice class cannot be deduced.", getClass());
					return null;
				}
				return (Collection<VALUE>) EntityReader.getInstance().readMany(entityClass);
			}
			
			@Override
			public Object getChoiceLabel(AbstractInputChoice<VALUE> input, VALUE choice) {
				if(choice == null)
					return "-- Aucune sélection --";
				if(choice instanceof SelectItem)
					return((SelectItem)choice).getLabel();
				return choice;
			}
			
			@Override
			public Object getChoiceValue(AbstractInputChoice<VALUE> input, VALUE choice) {
				if(choice instanceof SelectItem)
					return((SelectItem)choice).getValue();
				return choice;
			}
			
			public static class DefaultImpl extends Listener.AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}

	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInputChoice<VALUE>,VALUE> extends AbstractInput.AbstractConfiguratorImpl<INPUT> implements Serializable {

		public static Integer COLUMNS = 4;
		
		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
			if(StringHelper.isBlank(input.layout))
				input.layout = "responsive";
			if(input.columns == null) {
				if("responsive".equals(input.layout))
					input.columns = COLUMNS;
				else
					input.columns = 0;
			}
			if(input.converter == null) {
				Class<?> entityClass = input.choiceClass;
				if(entityClass == null) {
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
				}				
				if(!ClassHelper.isBelongsToJavaPackages(entityClass))
					//input.converter = DependencyInjection.inject(ObjectConverter.class);
					input.converter = __inject__(Converter.class);	
			}
						
			if(input.nullable == null)
				input.nullable = Boolean.FALSE;
			
			if(input.choices == null && Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_CHOICES_ARE_YES_NO_ONLY))) {
				input.choices = CHOICES_YES_NO;
			}
			if(input.choices == null && Boolean.TRUE.equals(ClassHelper.isInstanceOf(input.field.getType(),Boolean.class))) {
				input.choices = CHOICES_YES_NO;
			}
			
			if(input.choicesInitialized == null) {
				input.choicesInitialized = input.choices != null;
			}
		}
		
		public static final String FIELD_CHOICES_ARE_YES_NO_ONLY = "choicesAreYesOrNoOnly";
	}
	
	private static final Collection<Object> CHOICES_YES_NO = List.of(new SelectItem(Boolean.TRUE, "Oui"),new SelectItem(Boolean.FALSE, "Non"));
}