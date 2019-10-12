package org.cyk.utility.clazz;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.string.Strings;

@Deprecated
public interface ClassesGetter extends FunctionWithPropertiesAsInput<Classes> {

	Strings getPackageNames();
	Strings getPackageNames(Boolean injectIfNull);
	ClassesGetter setPackageNames(Strings packageNames);
	ClassesGetter addPackageNames(Collection<String> packageNames);
	ClassesGetter addPackageNames(String...packageNames);
	
	Classes getBasesClasses();
	Classes getBasesClasses(Boolean injectIfNull);
	ClassesGetter setBasesClasses(Classes basesClasses);
	ClassesGetter addBasesClasses(@SuppressWarnings("rawtypes") Collection<Class> basesClasses);
	ClassesGetter addBasesClasses(@SuppressWarnings("rawtypes") Class...basesClasses);
	
	Boolean getIsInterface();
	ClassesGetter setIsInterface(Boolean isInterface);
}
