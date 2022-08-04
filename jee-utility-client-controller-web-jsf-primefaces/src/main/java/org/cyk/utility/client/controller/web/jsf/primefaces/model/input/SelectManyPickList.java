package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.converter.Converter;
import org.cyk.utility.controller.EntityReader;
import org.primefaces.model.DualListModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectManyPickList extends AbstractInput<DualListModel<?>> implements Serializable {

	/**/

	private String filterMatchMode,labelDisplay,orientation;
	private Boolean responsive,showCheckbox,showSourceControls,showSourceFilter,showTargetControls,showTargetFilter;
	
	protected Class<?> choiceClass;
	protected List<Object> choices,selected;
	protected Boolean choicesInitialized;
	protected Boolean selectedInitialized;
	
	/**/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getChoiceValue(Object choice) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getChoiceValue(this, choice);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getChoiceLabel(Object choice) {
		if(choice == null)
			return "NULL_NULL_NULL_NULL";
		if(choiceClass != null && !choiceClass.equals(choice.getClass()))
			return "NO_MATHCING_CLASS";
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getChoiceLabel(this, choice);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getChoiceDescription(Object choice) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getChoiceDescription(this, choice);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean getIsChoiceDisabled(Object choice) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getIsChoiceDisabled(this, choice);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean getIsChoiceLabelEscaped(Object choice) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getIsChoiceLabelEscaped(this, choice);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getChoices() {
		if(Boolean.TRUE.equals(choicesInitialized))
			return choices;
		choices = (List<Object>) ((Listener<?>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).computeChoices(this);
		choicesInitialized = Boolean.TRUE;
		return choices;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getSelected() {
		if(Boolean.TRUE.equals(selectedInitialized))
			return selected;
		selected = (List<Object>) ((Listener<?>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).computeSelected(this);
		selectedInitialized = Boolean.TRUE;
		return selected;
	}
	
	/**/
	
	public static final String FIELD_CHOICE_CLASS = "choiceClass";
	public static final String FIELD_CHOICES = "choices";
	public static final String FIELD_CHOICES_INITIALIZED = "choicesInitialized";
	public static final String FIELD_SELECTED = "selected";
	public static final String FIELD_FILTER_MATCH_MODE = "filterMatchMode";
	public static final String FIELD_LABEL_DISPLAY = "labelDisplay";
	public static final String FIELD_ORIENTATION = "orientation";
	public static final String FIELD_RESPONSIVE = "responsive";
	public static final String FIELD_SHOW_CHECK_BOX = "showCheckbox";
	public static final String FIELD_SHOW_SOURCE_CONTROLS = "showSourceControls";
	public static final String FIELD_SHOW_SOURCE_FILTER = "showSourceFilter";
	public static final String FIELD_SHOW_TARGET_CONTROLS = "showTargetControls";
	public static final String FIELD_SHOW_TARGET_FILTER = "showTargetFilter";
	
	/**/
	
	public static interface Listener<VALUE> extends AbstractInput.Listener {
		
		List<VALUE> computeChoices(SelectManyPickList input);
		List<VALUE> computeSelected(SelectManyPickList input);
		
		Object getChoiceLabel(SelectManyPickList input,VALUE choice);
		Object getChoiceValue(SelectManyPickList input,VALUE choice);
		Object getChoiceDescription(SelectManyPickList input,VALUE choice);
		Boolean getIsChoiceDisabled(SelectManyPickList input,VALUE choice);
		Boolean getIsChoiceLabelEscaped(SelectManyPickList input,VALUE choice);
		
		public static abstract class AbstractImpl<VALUE> extends AbstractInput.Listener.AbstractImpl implements Listener<VALUE>,Serializable {
			@Override
			public List<VALUE> computeChoices(SelectManyPickList input) {
				Class<?> entityClass = input.choiceClass;
				if(entityClass == null) {
					if(ClassHelper.isInstanceOf(input.field.getType(),Collection.class)) {
						entityClass = ClassHelper.getParameterAt(input.field.getType(), 0);
					}else {
						entityClass = input.field.getType();
					}
				}
				if(entityClass == null) {
					LogHelper.logWarning("Choice class cannot be deduced.", getClass());
					return null;
				}
				return __computeChoices__(input, entityClass);
			}
			
			@SuppressWarnings("unchecked")
			protected List<VALUE> __computeChoices__(SelectManyPickList input,Class<?> entityClass) {				
				return (List<VALUE>) EntityReader.getInstance().readMany(entityClass);
			}
			
			@Override
			public List<VALUE> computeSelected(SelectManyPickList input) {
				return __computeSelected__(input);
			}
			
			protected List<VALUE> __computeSelected__(SelectManyPickList input) {
				return null;
			}
			
			@Override
			public Object getChoiceLabel(SelectManyPickList input, VALUE choice) {
				if(choice == null)
					return "-- Aucune sélection --";
				if(choice instanceof SelectItem)
					return((SelectItem)choice).getLabel();
				return choice;
			}
			
			@Override
			public Object getChoiceValue(SelectManyPickList input, VALUE choice) {
				if(choice instanceof SelectItem)
					return((SelectItem)choice).getValue();
				return choice;
			}
			
			@Override
			public Object getChoiceDescription(SelectManyPickList input, VALUE choice) {
				if(choice instanceof SelectItem)
					return((SelectItem)choice).getDescription();
				return null;
			}
			
			@Override
			public Boolean getIsChoiceDisabled(SelectManyPickList input, VALUE choice) {
				if(choice instanceof SelectItem)
					return((SelectItem)choice).isDisabled();
				return null;
			}
			
			@Override
			public Boolean getIsChoiceLabelEscaped(SelectManyPickList input, VALUE choice) {
				if(choice instanceof SelectItem)
					return((SelectItem)choice).isEscape();
				return null;
			}
			
			public static class DefaultImpl extends Listener.AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectManyPickList> implements Serializable {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void configure(SelectManyPickList selectManyPickList, Map<Object, Object> arguments) {
			super.configure(selectManyPickList, arguments);
			DualListModel dualListModel = selectManyPickList.value;
			if(dualListModel == null) {
				dualListModel = selectManyPickList.value = new DualListModel<>();
				dualListModel.setSource(selectManyPickList.getChoices());
				if(dualListModel.getSource() == null)
					dualListModel.setSource(new ArrayList<>());
				
				dualListModel.setTarget(selectManyPickList.getSelected());
				if(dualListModel.getTarget() == null)
					dualListModel.setTarget(new ArrayList<>());
			}
			if(selectManyPickList.converter == null && !ClassHelper.isBelongsToJavaPackages(selectManyPickList.choiceClass))
				selectManyPickList.converter = new Converter();
			if(StringHelper.isBlank(selectManyPickList.filterMatchMode))
				selectManyPickList.filterMatchMode = "contains";
			if(StringHelper.isBlank(selectManyPickList.labelDisplay))
				selectManyPickList.labelDisplay = "tooltip";
			if(StringHelper.isBlank(selectManyPickList.orientation))
				selectManyPickList.orientation = "horizontal";
			if(selectManyPickList.responsive == null)
				selectManyPickList.responsive = Boolean.TRUE;
			
			if(selectManyPickList.showCheckbox == null)
				selectManyPickList.showCheckbox = Boolean.FALSE;
			
			if(selectManyPickList.showSourceControls == null)
				selectManyPickList.showSourceControls = Boolean.FALSE;
			if(selectManyPickList.showSourceFilter == null)
				selectManyPickList.showSourceFilter = Boolean.FALSE;
			
			if(selectManyPickList.showTargetControls == null)
				selectManyPickList.showTargetControls = Boolean.FALSE;
			if(selectManyPickList.showTargetFilter == null)
				selectManyPickList.showTargetFilter = Boolean.FALSE;
		}
		
		@Override
		protected Class<SelectManyPickList> __getClass__() {
			return SelectManyPickList.class;
		}
		
		@Override
		protected String __getTemplate__(SelectManyPickList selectManyPickList, Map<Object, Object> arguments) {
			return "/input/select/many/selectManyPickList.xhtml";
		}
	}
	
	public static SelectManyPickList build(Map<Object, Object> arguments) {
		return Builder.build(SelectManyPickList.class,arguments);
	}
	
	public static SelectManyPickList build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	/**/

	static {
		Configurator.set(SelectManyPickList.class, new ConfiguratorImpl());
	}
}