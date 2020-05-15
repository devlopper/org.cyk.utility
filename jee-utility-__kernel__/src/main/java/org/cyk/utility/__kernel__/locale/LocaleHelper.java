package org.cyk.utility.__kernel__.locale;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface LocaleHelper {

	static Locale getDefault() {
		return DEFAULT_LOCALE.get() == null ? Locale.FRENCH : (Locale) DEFAULT_LOCALE.get();
	}
	
	static void addLocales(Collection<Locale> locales) {
		if(CollectionHelper.isEmpty(locales))
			return;
		Collection<Locale> collection = (Collection<Locale>) LOCALES.get();
		if(collection == null)
			LOCALES.set(collection = new LinkedHashSet<>());
		collection.addAll(locales);
	}
	
	static void addLocales(Locale...locales) {
		if(ArrayHelper.isEmpty(locales))
			return;
		addLocales(CollectionHelper.listOf(locales));
	}
	
	Value LOCALES = new Value();
	Value DEFAULT_LOCALE = new Value();
}
