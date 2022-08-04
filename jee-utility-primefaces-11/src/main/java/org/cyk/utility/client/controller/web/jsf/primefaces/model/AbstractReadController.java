package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractReadController extends AbstractObject implements Serializable {

	protected Layout layout;
	protected Integer labelWidth = 2;
	
	public AbstractReadController initialize() {
		layout = buildLayout();
		return this;
	}
	
	protected Layout buildLayout() {
		Collection<Map<Object,Object>> cellsMaps = buildLayoutCells();
		if(CollectionHelper.isEmpty(cellsMaps))
			return null;
		return buildLayout(cellsMaps);
	}
	
	protected Layout buildLayout(Collection<Map<Object,Object>> cellsMaps) {
		return Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.FLEX,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsMaps);
	}
	
	protected Collection<Map<Object,Object>> buildLayoutCells(){
		return null;
	}
	
	protected void addLabelValue(Collection<Map<Object,Object>> cellsMaps,String label,String value) {
		OutputText valueOutputText = buildValueOutputText(value);
		addLabelControl(cellsMaps, label, valueOutputText);
	}
	
	protected OutputText buildValueOutputText(String value) {
		OutputText valueOutputText = OutputText.buildFromValue(StringHelper.isBlank(value) ? "---" : value);
		valueOutputText.setEscape(Boolean.FALSE);
		return valueOutputText;
	}
	
	protected void addLabelControl(Collection<Map<Object,Object>> cellsMaps,String label,Object control) {
		//cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,OutputText.buildFromValue(label+StringUtils.repeat("&nbsp;",2)+":"+StringUtils.repeat("&nbsp;",2))
		//		.setEscape(Boolean.FALSE),Cell.FIELD_WIDTH,labelWidth));
		//cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,control,Cell.FIELD_WIDTH,12 - labelWidth));
		addLabelControlUsingMap(cellsMaps, label, MapHelper.instantiate(Cell.FIELD_CONTROL,control));
	}
	
	protected void addLabelControlUsingMap(Collection<Map<Object,Object>> cellsMaps,String label,Map<Object,Object> map) {
		cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,OutputText.buildFromValue(label+StringUtils.repeat("&nbsp;",2)+":"+StringUtils.repeat("&nbsp;",2))
				.setEscape(Boolean.FALSE),Cell.FIELD_WIDTH,labelWidth));
		map.put(Cell.FIELD_WIDTH,12 - labelWidth);
		cellsMaps.add(map);
	}
}