package org.cyk.utility.instant;

import java.util.Locale;

import org.cyk.utility.value.ValueLength;

public interface InstantHelper {

	InstantPattern getPattern(Locale locale,InstantPart part,ValueLength length);
	
}
