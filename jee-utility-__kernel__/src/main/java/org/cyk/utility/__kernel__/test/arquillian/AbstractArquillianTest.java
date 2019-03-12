package org.cyk.utility.__kernel__.test.arquillian;

import java.io.Serializable;

import org.cyk.utility.__kernel__.test.AbstractTest;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class AbstractArquillianTest extends AbstractTest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	protected static <ARCHIVE extends Archive<?>> ARCHIVE addBeanXml(ARCHIVE archive,Object beansXml){
		if(beansXml == null){
			if(archive instanceof JavaArchive)
				((JavaArchive)archive).addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml");
		}else{
			if(beansXml instanceof String)
				if(archive instanceof JavaArchive)
					((JavaArchive)archive).addAsManifestResource((String) beansXml,"beans.xml");
		}
		return archive;
	}
	
	protected static <ARCHIVE extends Archive<?>> ARCHIVE createDeployment(Class<ARCHIVE> aClass,Object beansXml){
		ARCHIVE archive = ShrinkWrap.create(aClass);
		if(archive instanceof JavaArchive)
			((JavaArchive)archive).addPackages(Boolean.FALSE,"org.cyk.utility");
		addBeanXml(archive, beansXml);
		
		return archive;
	}
	
	protected static <ARCHIVE extends Archive<?>> ARCHIVE createDeployment(Class<ARCHIVE> aClass){
		return createDeployment(aClass, null);
	}
	
	protected static JavaArchive createJavaArchiveDeployment(Object beansXml){
		return createDeployment(JavaArchive.class,beansXml);
	}
	
	protected static JavaArchive createJavaArchiveDeployment(){
		return createDeployment(JavaArchive.class);
	}
	
	protected static WebArchive createWebArchiveDeployment(){
		return createDeployment(WebArchive.class);
	}
	
	protected static JavaArchive createDeployment(){
		return createJavaArchiveDeployment();
	}
	
	/**/
	
}
