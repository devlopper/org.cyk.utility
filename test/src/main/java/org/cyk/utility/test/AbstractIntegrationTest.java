package org.cyk.utility.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

@RunWith(Arquillian.class)
public abstract class AbstractIntegrationTest extends AbstractTest  {
	
	private static final long serialVersionUID = -8873735551443449606L;
	  
	public static ArchiveBuilder _deployment(Class<?>[] classes){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create().addClasses(classes);
		return builder;
	}
	
	public static ArchiveBuilder _deploymentOfPackages(String...thePackages){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create();
		Collection<Class<?>> classes = new ArrayList<>();
		for(String aPackage : thePackages)
			classes.addAll(getPackageClasses(aPackage, Object.class));
		
		//for(Class<?> c : classes)
		//	System.out.print(c.getSimpleName()+" , ");
		
		builder.addClasses(classes.toArray(new Class<?>[]{}));
		return builder;
	}
	
	private static <T> Collection<Class<? extends T>> getPackageClasses(String aPackage,Class<T> aRootClass){
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
	    classLoadersList.add(ClasspathHelper.contextClassLoader());
	    classLoadersList.add(ClasspathHelper.staticClassLoader());

	    Reflections reflections = new Reflections(new ConfigurationBuilder()
	        .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
	        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
	        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(aPackage))));
	    
	    return reflections.getSubTypesOf(aRootClass);
	}
	
}
