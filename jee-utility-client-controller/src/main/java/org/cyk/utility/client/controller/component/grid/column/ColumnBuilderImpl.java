package org.cyk.utility.client.controller.component.grid.column;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.grid.AbstractDimensionBuilderImpl;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewMap;

public class ColumnBuilderImpl extends AbstractDimensionBuilderImpl<Column> implements ColumnBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ViewBuilderMap viewMap;
	private OutputStringTextBuilder headerText,footerText;
	private Strings fieldNameStrings;
	private Object width;
	
	@Override
	protected void __execute__(Column column) {
		super.__execute__(column);
		Strings fieldNameStrings = getFieldNameStrings();
		if(CollectionHelper.isNotEmpty(fieldNameStrings))
			column.setFieldName(FieldHelper.join(fieldNameStrings.get()));
		
		String fieldName = column.getFieldName();
		if(StringHelper.isBlank(fieldName)) {
			if(column.getIdentifier() == null)
				throw new RuntimeException(getClass()+" : identifier is required when field name is blank");
			column.setValuePropertyName(column.getIdentifier().toString());
		}else
			column.setValuePropertyName(fieldName);
		
		ViewBuilderMap viewMap = getViewMap();
		if(viewMap == null)
			viewMap = __inject__(ViewBuilderMap.class);
		if(viewMap!=null) {
			ViewBuilder headerView = viewMap.get(ViewMap.HEADER);
			if(headerView == null) {
				OutputStringTextBuilder headerText = getHeaderText();
				if(headerText == null) {
					String valuePropertyName = column.getValuePropertyName();
					if(StringUtils.contains(valuePropertyName, "."))
						valuePropertyName = StringUtils.substringAfterLast(valuePropertyName, ".");
					valuePropertyName = __buildInternationalizationString__(valuePropertyName,Case.FIRST_CHARACTER_UPPER);
					headerText = __inject__(OutputStringTextBuilder.class);
					headerText.setValue(valuePropertyName);
				}
				
				if(headerText!=null) {
					headerView = __inject__(ViewBuilder.class).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
					headerView.getComponentsBuilder().addComponents(headerText);
					viewMap.set(ViewMap.HEADER,headerView);
				}
			}
			
			ViewBuilder footerView = viewMap.get(ViewMap.FOOTER);
			if(footerView == null) {
				OutputStringTextBuilder footerText = getFooterText();
				if(footerText!=null) {
					footerView = __inject__(ViewBuilder.class).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
					footerView.getComponentsBuilder().addComponents(footerText);
					viewMap.set(ViewMap.FOOTER,footerView);
				}
			}
			
			Collection<Map.Entry<String,ViewBuilder>> entries = viewMap.getEntries();
			if(CollectionHelper.isNotEmpty(entries)) {
				column.setViewMap(__inject__(ViewMap.class));
				for(Map.Entry<String,ViewBuilder> index : entries) {
					column.getViewMap().set(index.getKey(),index.getValue().execute().getOutput());
				}
			}
		}
		
		Object width = getWidth();
		if(width != null)
			column.setWidth(width);
	}
	
	@Override
	public ColumnBuilder setWidth(Object width) {
		this.width = width;
		return this;
	}
	
	@Override
	public Object getWidth() {
		return width;
	}
	
	@Override
	public ViewBuilderMap getViewMap() {
		return viewMap;
	}

	@Override
	public ViewBuilderMap getViewMap(Boolean injectIfNull) {
		if(viewMap == null && Boolean.TRUE.equals(injectIfNull))
			viewMap = __inject__(ViewBuilderMap.class);
		return viewMap;
	}

	@Override
	public ColumnBuilder setViewMap(ViewBuilderMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}

	@Override
	public ColumnBuilder setViews(Object... keyValues) {
		getViewMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	@Override
	public ViewBuilder getView(String key) {
		ViewBuilderMap viewMap = getViewMap();
		return viewMap == null ? null : viewMap.get(key);
	}
	
	@Override
	public OutputStringTextBuilder getHeaderText() {
		return headerText;
	}

	@Override
	public OutputStringTextBuilder getHeaderText(Boolean injectIfNull) {
		if(headerText == null && Boolean.TRUE.equals(injectIfNull))
			headerText = __inject__(OutputStringTextBuilder.class);
		return headerText;
	}

	@Override
	public ColumnBuilder setHeaderText(OutputStringTextBuilder headerText) {
		this.headerText = headerText;
		return this;
	}

	@Override
	public ColumnBuilder setHeaderTextValue(String headerTextValue) {
		getHeaderText(Boolean.TRUE).setValue(headerTextValue);
		return this;
	}
	
	@Override
	public OutputStringTextBuilder getFooterText() {
		return footerText;
	}

	@Override
	public OutputStringTextBuilder getFooterText(Boolean injectIfNull) {
		if(footerText == null && Boolean.TRUE.equals(injectIfNull))
			footerText = __inject__(OutputStringTextBuilder.class);
		return footerText;
	}

	@Override
	public ColumnBuilder setFooterText(OutputStringTextBuilder footerText) {
		this.footerText = footerText;
		return this;
	}

	@Override
	public ColumnBuilder setFooterTextValue(String footerTextValue) {
		getFooterText(Boolean.TRUE).setValue(footerTextValue);
		return this;
	}
	
	@Override
	public Strings getFieldNameStrings() {
		return fieldNameStrings;
	}
	
	@Override
	public ColumnBuilder setFieldNameStrings(Strings fieldNameStrings) {
		this.fieldNameStrings = fieldNameStrings;
		return this;
	}
	
	@Override
	public Strings getFieldNameStrings(Boolean injectIfNull) {
		if(fieldNameStrings == null && Boolean.TRUE.equals(injectIfNull))
			fieldNameStrings = __inject__(Strings.class);
		return fieldNameStrings;
	}
	
	@Override
	public ColumnBuilder addFieldNameStrings(Collection<String> fieldNameStrings) {
		getFieldNameStrings(Boolean.TRUE).add(fieldNameStrings);
		return this;
	}
	
	@Override
	public ColumnBuilder addFieldNameStrings(String... fieldNameStrings) {
		getFieldNameStrings(Boolean.TRUE).add(fieldNameStrings);
		return this;
	}
	
	@Override
	public ViewBuilder getBodyView() {
		return getView(ViewMap.BODY);
	}
	@Override
	public ViewBuilder getBodyView(Boolean injectIfNull) {
		ViewBuilder view = getBodyView();
		if(view == null)
			setBodyView(view = __inject__(ViewBuilder.class));
		return view;
	}
	
	@Override
	public ColumnBuilder setBodyView(ViewBuilder bodyView) {
		getViewMap(Boolean.TRUE).set(ViewMap.BODY,bodyView);
		return this;
	}
}
