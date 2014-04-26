package org.cyk.utility.common.model.table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DefaultTableColumn extends DefaultDimension<String> {

	private static final long serialVersionUID = 8311076255598465773L;

	protected String fieldName;

	public DefaultTableColumn(String title, String fieldName) {
		super(title);
		this.fieldName = fieldName;
	}
	
	
}
