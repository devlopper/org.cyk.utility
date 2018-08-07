package org.cyk.utility.instant;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.value.ValueLength;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper=false,of={"locale","part","length"})
public class InstantPattern extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Locale locale;
	private InstantPart part;
	private ValueLength length;
	
	private String value;
	
	/**/
	
	public static final String FIELD_LOCALE = "locale";
	public static final String FIELD_PART = "part";
	public static final String FIELD_LENGTH = "length";
	
}
