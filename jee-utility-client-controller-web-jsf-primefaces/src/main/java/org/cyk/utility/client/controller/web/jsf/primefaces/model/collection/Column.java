package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Column extends AbstractObject implements Serializable {

	private String header,footer,selectionMode,width,filterBy,fieldName;
	private Object filterValue; 
	
	/**/
	
	public static final String FIELD_HEADER = "header";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	public static final String FIELD_WIDTH = "width";
	public static final String FIELD_FIELD_NAME = "fieldName";
	public static final String FIELD_FILTER_BY = "filterBy";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractObject.AbstractConfiguratorImpl<Column> implements Serializable {

		@Override
		public void configure(Column column, Map<Object, Object> arguments) {
			super.configure(column, arguments);
			if(column.header == null && StringHelper.isNotBlank(column.fieldName)) {
				column.header = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(column.fieldName),null,null,Case.FIRST_CHARACTER_UPPER);
			}
			if(StringHelper.isBlank(column.getFilterBy()) && StringHelper.isNotBlank(column.getFieldName()))
				column.setFilterBy(column.getFieldName());
		}
		
		@Override
		protected Class<Column> __getClass__() {
			return Column.class;
		}
	}
	
	static {
		Configurator.set(Column.class, new ConfiguratorImpl());
	}
}
