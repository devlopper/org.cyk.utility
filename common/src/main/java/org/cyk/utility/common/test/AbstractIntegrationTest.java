package org.cyk.utility.common.test;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.CommonUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

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
			classes.addAll(CommonUtils.getInstance().getPackageClasses(aPackage, Object.class));
		
		//for(Class<?> c : classes)
		//	System.out.print(c.getSimpleName()+" , ");
		
		builder.addClasses(classes.toArray(new Class<?>[]{}));
		return builder;
	}
	
	
	
}
