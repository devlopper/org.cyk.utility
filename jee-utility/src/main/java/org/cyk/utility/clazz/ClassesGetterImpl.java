package org.cyk.utility.clazz;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

@Dependent
public class ClassesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Classes> implements ClassesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Strings packageNames;
	private Classes basesClasses;
	private Boolean isInterface;
	
	@Override
	protected Classes __execute__() throws Exception {
		Strings packageNames = ValueHelper.returnOrThrowIfBlank("classes packages names", getPackageNames());
		Classes basesClasses = ValueHelper.returnOrThrowIfBlank("classes bases", getBasesClasses());
		Boolean isInterface = getIsInterface();
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
			if(CollectionHelper.isNotEmpty(result)) {
				for(@SuppressWarnings("rawtypes") Class index : result) {
					Boolean isAddable = Boolean.TRUE;
					if(Boolean.TRUE.equals(isAddable) && isInterface!=null && 
							(Boolean.TRUE.equals(isInterface) && !Modifier.isInterface(index.getModifiers()) || Boolean.FALSE.equals(isInterface) 
									&& Modifier.isInterface(index.getModifiers()))
							)
						isAddable = Boolean.FALSE;	
					
					if(Boolean.TRUE.equals(isAddable))
						classes.add(index);
				}
			}
		}
		
		return classes;
	}
	
	@Override
	public Strings getPackageNames() {
		return packageNames;
	}

	@Override
	public Strings getPackageNames(Boolean injectIfNull) {
		if(packageNames == null && Boolean.TRUE.equals(injectIfNull))
			packageNames = DependencyInjection.inject(Strings.class);
		return packageNames;
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
		if(basesClasses == null && Boolean.TRUE.equals(injectIfNull))
			basesClasses = DependencyInjection.inject(Classes.class);
		return basesClasses;
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
	public Boolean getIsInterface() {
		return isInterface;
	}
	
	@Override
	public ClassesGetter setIsInterface(Boolean isInterface) {
		this.isInterface = isInterface;
		return this;
	}

	/**/
	
	public static final String FIELD_PACKAGE_NAMES = "packageNames";
	public static final String FIELD_BASES_CLASSES = "basesClasses";
	
}
