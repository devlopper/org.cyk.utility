package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Column extends AbstractObject implements Serializable {

	private String headerText,footerText,selectionMode,width,filterBy,fieldName;
	private Boolean visible = Boolean.TRUE;
	private Object filterValue;
	private Integer index;
	
	/**/
	
	public static final String FIELD_INDEX = "index";
	public static final String FIELD_HEADER_TEXT = "headerText";
	public static final String FIELD_FOOTER_TEXT = "footerText";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	public static final String FIELD_WIDTH = "width";
	public static final String FIELD_FIELD_NAME = "fieldName";
	public static final String FIELD_FILTER_BY = "filterBy";
	public static final String FIELD_VISIBLE = "visible";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractObject.AbstractConfiguratorImpl<Column> implements Serializable {

		@Override
		public void configure(Column column, Map<Object, Object> arguments) {
			super.configure(column, arguments);
			if(column.headerText == null && StringHelper.isNotBlank(column.fieldName)) {
				column.headerText = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(column.fieldName),null,null,Case.FIRST_CHARACTER_UPPER);
			}
			Boolean isFilterable = (Boolean) MapHelper.readByKey(arguments, FIELD_FILTERABLE);
			if(isFilterable == null)
				isFilterable = Boolean.FALSE;
			if(StringHelper.isBlank(column.getFilterBy()) && Boolean.TRUE.equals(isFilterable) && StringHelper.isNotBlank(column.getFieldName()))
				column.setFilterBy(column.getFieldName());
		}
		
		@Override
		protected Class<Column> __getClass__() {
			return Column.class;
		}
		
		public static final String FIELD_FILTERABLE = "filterable";
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
}
