package org.cyk.utility.test.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.cyk.utility.test.AbstractTest;
import org.cyk.utility.test.ArchiveBuilder;
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
	
	public static final String ARQUILLIAN_FILE = "arquillian.xml";
	public static final String CONTAINER_NODE = "container";
	public static final String CONFIGURATION_NODE = "configuration";
	public static final String PROPERTY_NODE = "property";
	
	public static final String GLASSFISH_EMBEDDED_RESOURCE_FILE_MEMORY = "src/test/resources-glassfish-embedded/glassfish-resources-memory.xml";
	
	public static final String GLASSFISH_EMBEDDED_RESOURCE_FILE_LIVE = "src/test/resources-glassfish-embedded/glassfish-resources-live.xml";
	
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

	public static void updateArquillianResourceXml(String value){
		updateXmlNode(ARQUILLIAN_FILE,ARQUILLIAN_FILE, ARQUILLIAN_NAMESPACE, new String[][]{
			new String[]{CONTAINER_NODE,CONFIGURATION_NODE,PROPERTY_NODE,value}
		});
	}
	
	public static String readArquillianResourceXml(){
		String[][] nodes = new String[][]{ new String[]{CONTAINER_NODE,CONFIGURATION_NODE,PROPERTY_NODE,null}};
		readXmlNode(ARQUILLIAN_FILE, ARQUILLIAN_NAMESPACE, nodes);
		return nodes[0][3];
	}
	
	
	
}
