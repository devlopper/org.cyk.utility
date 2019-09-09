package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.string.Case;

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
}