package org.cyk.utility.instant;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.locale.LocaleHelper;
import org.cyk.utility.value.ValueHelper;
import org.cyk.utility.value.ValueLength;

@ApplicationScoped
public class InstantHelperImpl extends AbstractHelper implements InstantHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InstantPattern getPattern(Locale locale,InstantPart part,ValueLength length){
		locale = __inject__(ValueHelper.class).defaultToIfNull(__inject__(LocaleHelper.class).getDefaultLocale(), Locale.FRENCH);
		part = __inject__(ValueHelper.class).defaultToIfNull(part, InstantPart.DATE_ONLY);
		length = __inject__(ValueHelper.class).defaultToIfNull(length, ValueLength.SHORT);
		
		for(InstantPattern pattern : INSTANT_PATTERNS)
			if(locale.equals(pattern.getLocale()) && part.equals(pattern.getPart()) && length.equals(pattern.getLength()))
				return pattern;
		
		if(!Locale.FRENCH.equals(length))
			return getPattern(Locale.FRENCH, part, length);
		
		return null;
	}
	
	/**/
	
	private static final Collection<InstantPattern> INSTANT_PATTERNS = new HashSet<>();
	
	static{
		putPatterns(Locale.FRENCH, String.format(InstantConstant.DAY_MONTH_YEAR_PATTERN_FORMAT, "dd","MM","yyyy"),"EEEE , dd MMMM yyyy", InstantConstant.HOUR_MINUTE_PATTERN
				,InstantConstant.HOUR_MINUTE_PATTERN,InstantConstant.HOUR_MINUTE_SECOND_PATTERN,InstantConstant.HOUR_MINUTE_SECOND_MILLISECOND_PATTERN,"yyyy");
		putPatterns(Locale.ENGLISH, String.format(InstantConstant.DAY_MONTH_YEAR_PATTERN_FORMAT, "dd","MM","yyyy"),"EEEE , dd MMMM yyyy", InstantConstant.HOUR_MINUTE_PATTERN
				,InstantConstant.HOUR_MINUTE_PATTERN,InstantConstant.HOUR_MINUTE_SECOND_PATTERN,InstantConstant.HOUR_MINUTE_SECOND_MILLISECOND_PATTERN,"yyyy");
	}
	
	private static void putPatterns(Locale locale,String shortDateOnlyPattern,String longDateOnlyPattern,String shortTimeOnlyPattern,String shortTimeOnlyHourMinutePattern,String shortTimeOnlyHourMinuteSecondPattern,String shortTimeOnlyHourMinuteSecondMillisecondPattern,String dateYearOnlyPattern){
		String longTimeOnlyPattern = shortTimeOnlyPattern;
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_ONLY, ValueLength.SHORT, shortDateOnlyPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_ONLY, ValueLength.LONG, longDateOnlyPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.TIME_ONLY, ValueLength.SHORT, shortTimeOnlyPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.TIME_ONLY, ValueLength.LONG, longTimeOnlyPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME, ValueLength.SHORT, shortDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME, ValueLength.LONG, longDateOnlyPattern+ConstantCharacter.SPACE+longTimeOnlyPattern));
		
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE, ValueLength.SHORT, shortDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyHourMinutePattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE, ValueLength.LONG, longDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyHourMinutePattern));
		
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, ValueLength.SHORT, shortDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyHourMinuteSecondPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, ValueLength.LONG, longDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyHourMinuteSecondPattern));
		
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND, ValueLength.SHORT, shortDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyHourMinuteSecondMillisecondPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND, ValueLength.LONG, longDateOnlyPattern+ConstantCharacter.SPACE+shortTimeOnlyHourMinuteSecondMillisecondPattern));
		
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_YEAR_ONLY, ValueLength.SHORT, dateYearOnlyPattern));
		INSTANT_PATTERNS.add(new InstantPattern(locale, InstantPart.DATE_YEAR_ONLY, ValueLength.LONG, dateYearOnlyPattern));
	}
	
}
