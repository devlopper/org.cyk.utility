package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Column extends AbstractObject implements Serializable {

	private Class<?> fieldType;
	private Value.Type valueType;
	private FilterInputType filterInputType;
	private Collection<SelectItem> filterInputSelectItems;
	private String headerText,footerText,footerStyle,footerStyleClass,selectionMode,width,filterBy,fieldName,field;
	private Boolean visible = Boolean.TRUE;
	private Object filterValue;
	private Integer index;
	private OutputText footerOutputText;
	private CellEditor cellEditor;
	private CommandButton removeCommandButton;
	
	public void setFooterValueFromMaster(Object master) {
		((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).setFooterValueFromMaster(this, master);
	}
	
	/**/
	
	public static final String FIELD_FILTER_INPUT_SELECT_ITEMS = "filterInputSelectItems";
	public static final String FIELD_FILTER_INPUT_TYPE = "filterInputType";
	public static final String FIELD_INDEX = "index";
	public static final String FIELD_HEADER_TEXT = "headerText";
	public static final String FIELD_FOOTER_TEXT = "footerText";
	public static final String FIELD_FOOTER_OUTPUT_TEXT = "footerOutputText";
	public static final String FIELD_FOOTER_STYLE = "footerStyle";
	public static final String FIELD_FOOTER_STYLE_CLASS = "footerStyleClass";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	public static final String FIELD_WIDTH = "width";
	//public static final String FIELD_FIELD = "field";
	public static final String FIELD_FIELD_NAME = "fieldName";
	public static final String FIELD_FIELD_TYPE = "fieldType";
	public static final String FIELD_FILTER_BY = "filterBy";
	public static final String FIELD_VISIBLE = "visible";
	public static final String FIELD_VALUE_TYPE = "valueType";
	public static final String FIELD_REMOVE_COMMAND_BUTTON = "removeCommandButton";
	
	/**/
	
	public Boolean isFilterInputTypeInputText() {
		return filterInputType == null || FilterInputType.INPUT_TEXT.equals(filterInputType);
	}
	
	public Boolean isFilterInputTypeSelectOneMenu() {
		return FilterInputType.SELECT_ONE_MENU.equals(filterInputType);
	}
	
	/**/
	
	public static interface Listener {
		void setFooterValueFromMaster(Column column,Object master);
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {
			@Override
			public void setFooterValueFromMaster(Column column,Object master) {
				if(column == null || StringHelper.isBlank(column.getFieldName()) || column.getFooterOutputText() == null)
					return;
				Object value = master == null ? null : readFooterValueFromMaster(master, column.getFieldName());
				if(column.getFieldType() != null) {
					if(ClassHelper.isInstanceOfNumber(column.getFieldType()))
						value = NumberHelper.format((Number)value);
				}
				value = StringHelper.get(value);
				column.getFooterOutputText().setValue(StringHelper.get(value));
			}
			
			public Object readFooterValueFromMaster(Object master,String fieldName) {
				return StringHelper.isBlank(fieldName) ? null : FieldHelper.read(master,fieldName);
			}
			
			public static class DefaultImpl extends Listener.AbstractImpl implements Serializable {
				public static final Listener INSTANCE = new DefaultImpl();
			}
		}
	}
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractObject.AbstractConfiguratorImpl<Column> implements Serializable {

		@Override
		public void configure(Column column, Map<Object, Object> arguments) {
			super.configure(column, arguments);			
			if(column.field == null) {		
				column.field = column.fieldName;
			}			
			if(column.headerText == null) {				
				if(StringHelper.isNotBlank(column.fieldName)) {
					column.headerText = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(column.fieldName),null,null,Case.FIRST_CHARACTER_UPPER);	
				}				
			}
			Boolean isFilterable = (Boolean) MapHelper.readByKey(arguments, FIELD_FILTERABLE);
			if(isFilterable == null)
				isFilterable = Boolean.FALSE;
			if(StringHelper.isBlank(column.getFilterBy()) && Boolean.TRUE.equals(isFilterable) && StringHelper.isNotBlank(column.fieldName))
				column.setFilterBy(column.fieldName);			
			Boolean isEditable = (Boolean) MapHelper.readByKey(arguments, FIELD_EDITABLE);
			if(isEditable != null) {
				column.cellEditor = CellEditor.build(CellEditor.FIELD_DISABLED,!isEditable);
			}
			if(column.fieldType == null && Value.Type.CURRENCY.equals(column.valueType))
				column.fieldType = Long.class;
			if(column.width == null && Value.Type.CURRENCY.equals(column.valueType)) {
				Integer width = 100;
				if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_EDITABLE)))
					width += 30;
				column.width = width.toString();
			}
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_SHOW_FOOTER))) {
				if(Value.Type.CURRENCY.equals(column.valueType))
					column.footerOutputText = OutputText.build(OutputText.FIELD_VALUE,"-",OutputText.FIELD_STYLE,"float:right;font-weight: bold;");
				else
					column.footerOutputText = OutputText.build();
			}
			
			if(column.filterInputType == null && column.filterInputSelectItems != null)
				column.filterInputType = FilterInputType.SELECT_ONE_MENU;
		}
		
		@Override
		protected Class<Column> __getClass__() {
			return Column.class;
		}
		
		public static final String FIELD_FILTERABLE = "filterable";
		public static final String FIELD_EDITABLE = "editable";
		public static final String FIELD_SHOW_FOOTER = "showFooter";
	}
	
	public static interface FooterValueBuilder {
		String build(Column column);
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements FooterValueBuilder,Serializable {
			@Override
			public String build(Column column) {
				Object value = __build__(column);
				if(Boolean.TRUE.equals(getValueProcessableBasedOnFieldType()) && column.getFieldType() != null) {
					if(ClassHelper.isInstanceOfNumber(column.getFieldType()))
						value = NumberHelper.format((Number)value);
				}
				return StringHelper.get(value);
			}
			
			protected abstract Object __build__(Column column);
			
			protected Boolean getValueProcessableBasedOnFieldType() {
				return Boolean.TRUE;
			}
		}
	}
	
	public static Column build(Map<Object,Object> arguments) {
		return Builder.build(Column.class,arguments);
	}
	
	public static Column build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Column.class, new ConfiguratorImpl());
	}
	
	public static enum FilterInputType{
		INPUT_TEXT,SELECT_ONE_MENU
	}
}