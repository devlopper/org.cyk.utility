package org.cyk.utility.common.model;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Area extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Interval length = new Interval(),width = new Interval();
	
	/**/
	
	@Override
	public String toString() {
		return "("+length+","+width+")";
	}
	
	public static final String FIELD_LENGTH = "length";
	public static final String FIELD_WIDTH = "width";

}
