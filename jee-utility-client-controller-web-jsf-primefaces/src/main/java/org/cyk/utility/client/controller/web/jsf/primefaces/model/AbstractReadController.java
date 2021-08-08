package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;

public abstract class AbstractReadController extends AbstractObject implements Serializable {

	public static void addLabelValue(Collection<Map<Object,Object>> cellsMaps,String label,String value) {
		OutputText valueOutputText = OutputText.buildFromValue(StringHelper.isBlank(value) ? "---" : value);
		valueOutputText.setEscape(Boolean.FALSE);
		addLabelControl(cellsMaps, label, valueOutputText);
	}
	
	public static void addLabelControl(Collection<Map<Object,Object>> cellsMaps,String label,Object control) {
		cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,OutputText.buildFromValue(label+StringUtils.repeat("&nbsp;",2)+":"+StringUtils.repeat("&nbsp;",2))
				.setEscape(Boolean.FALSE),Cell.FIELD_WIDTH));
		cellsMaps.add(MapHelper.instantiate(Cell.FIELD_CONTROL,control,Cell.FIELD_WIDTH));
	}
	
}