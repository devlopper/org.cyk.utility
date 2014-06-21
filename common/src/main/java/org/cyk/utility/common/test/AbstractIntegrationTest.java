package org.cyk.utility.common.test;

import java.util.ArrayList;

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
	
	public static ArchiveBuilder _deploymentOfPackage(String aPackage){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create();
		builder.addClasses(new ArrayList<>(CommonUtils.getInstance().getPackageClasses(aPackage, Object.class)).toArray(new Class<?>[]{}));
		return builder;
	}
	
	
	
}
