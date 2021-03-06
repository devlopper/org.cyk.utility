package org.cyk.utility.persistence.server.query;

import org.apache.commons.lang3.RegExUtils;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;

@Getter
public enum Clause {

	SELECT
	,FROM
	,WHERE
	,HAVING
	,GROUP_BY
	,ORDER_BY
	
	;
	
	private String value;
	private String format;
	
	private Clause() {
		value = RegExUtils.replaceAll(name(), "_", " ");
		format = value+" %s";
	}
	
	public String format(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return String.format(format, string);
	}
}