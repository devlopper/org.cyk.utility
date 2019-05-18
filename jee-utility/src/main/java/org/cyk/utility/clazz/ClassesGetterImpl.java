package org.cyk.utility.clazz;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

public class ClassesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Classes> implements ClassesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Strings packageNames;
	private Classes basesClasses;
	private Integer modifiers;
	
	@Override
	protected Classes __execute__() throws Exception {
		Strings packageNames = __injectValueHelper__().returnOrThrowIfBlank("classes packages names", getPackageNames());
		Classes basesClasses = __injectValueHelper__().returnOrThrowIfBlank("classes bases", getBasesClasses());
		Integer modifiers = getModifiers();
		Classes classes = __inject__(Classes.class);
		SubTypesScanner subTypesScanner = new SubTypesScanner(false);
		for(@SuppressWarnings("rawtypes") Class indexBaseClass : basesClasses.get()) {
			
			ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
					.forPackages(packageNames.get().toArray(new String[] {}))
					//.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(indexPackageName)))
					//.setUrls(ClasspathHelper.forPackage(indexPackageName))
					.setScanners(subTypesScanner)
					;
			
			@SuppressWarnings("rawtypes")
			Collection<Class> result = new Reflections(configurationBuilder).getSubTypesOf(indexBaseClass);
			if(__injectCollectionHelper__().isNotEmpty(result)) {
				for(Class index : result) {
					//TODO handle modifiers
					//for()
					//if(Modifier.isInterface(modifiers) && index.getModifiers()
				}
			}
			
			classes.add(result);
			
		}
		
		return classes;
	}
	
	@Override
	public Strings getPackageNames() {
		return packageNames;
	}

	@Override
	public Strings getPackageNames(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_PACKAGE_NAMES, injectIfNull);
	}

	@Override
	public ClassesGetter setPackageNames(Strings packageNames) {
		this.packageNames = packageNames;
		return this;
	}

	@Override
	public ClassesGetter addPackageNames(Collection<String> packageNames) {
		getPackageNames(Boolean.TRUE).add(packageNames);
		return this;
	}

	@Override
	public ClassesGetter addPackageNames(String... packageNames) {
		getPackageNames(Boolean.TRUE).add(packageNames);
		return this;
	}

	@Override
	public Classes getBasesClasses() {
		return basesClasses;
	}

	@Override
	public Classes getBasesClasses(Boolean injectIfNull) {
		return (Classes) __getInjectIfNull__(FIELD_BASES_CLASSES, injectIfNull);
	}

	@Override
	public ClassesGetter setBasesClasses(Classes basesClasses) {
		this.basesClasses = basesClasses;
		return this;
	}

	@Override
	public ClassesGetter addBasesClasses(@SuppressWarnings("rawtypes") Collection<Class> basesClasses) {
		getBasesClasses(Boolean.TRUE).add(basesClasses);
		return this;
	}

	@Override
	public ClassesGetter addBasesClasses(@SuppressWarnings("rawtypes") Class... basesClasses) {
		getBasesClasses(Boolean.TRUE).add(basesClasses);
		return this;
	}
	
	@Override
	public Integer getModifiers() {
		return modifiers;
	}
	
	@Override
	public ClassesGetter setModifiers(Integer modifiers) {
		this.modifiers = modifiers;
		return this;
	}

	/**/
	
	public static final String FIELD_PACKAGE_NAMES = "packageNames";
	public static final String FIELD_BASES_CLASSES = "basesClasses";
	
}
