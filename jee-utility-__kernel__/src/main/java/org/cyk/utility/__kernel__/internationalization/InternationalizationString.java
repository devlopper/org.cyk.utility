package org.cyk.utility.__kernel__.internationalization;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.__kernel__.string.Case;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InternationalizationString implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private InternationalizationKey key;
	private Object[] arguments;
	private Locale locale;
	private Case kase;
	private String value;
	private Boolean isHasBeenProcessed;
	
	public InternationalizationString setValue(String value) {
		this.value = value;
		setIsHasBeenProcessed(Boolean.TRUE);
		return this;
	}
}