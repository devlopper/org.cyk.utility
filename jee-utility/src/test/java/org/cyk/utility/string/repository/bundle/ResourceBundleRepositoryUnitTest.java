package org.cyk.utility.string.repository.bundle;

import java.util.Arrays;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourceBundleRepositoryUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Inject ResourceBundleRepository resourceBundleRepository;
	
	@BeforeClass
	public static void setup(){
		DependencyInjection.inject(ResourceBundleRepository.class).addBundle("org.cyk.utility.string.repository.bundle.message");
	}
	
	@Test
	public void isNullWhenKeyNotExist(){
		assertionHelper.assertNull(resourceBundleRepository.getOne("sale"));
		assertionHelper.assertNull(resourceBundleRepository.getOne("person"));
		assertionHelper.assertNull(resourceBundleRepository.getOne("abc"));
	}
	
	@Test
	public void isSalutWhenKeyIsHi(){
		Properties properties = new Properties();
		properties.setResourceBundles(Arrays.asList( new Bundle("org.cyk.utility.string.repository.bundle.message") ));
		properties.setKey("hi");
		assertionHelper.assertEquals("salut", resourceBundleRepository.getOne("hi"));
	}
	
	@Test
	public void isBonWhenKeyIsGood(){
		Properties properties = new Properties();
		properties.setResourceBundles(Arrays.asList( new Bundle("org.cyk.utility.string.repository.bundle.message") ));
		properties.setKey("good");
		assertionHelper.assertEquals("bon", resourceBundleRepository.getOne("good"));
	}
	
	@Test
	public void isSalutFrom01WhenMessage01BundleAddedAndKeyIsHi(){
		Properties properties = new Properties();
		properties.setResourceBundles(Arrays.asList( new Bundle("org.cyk.utility.string.repository.bundle.message_01") ));
		properties.setKey("hi");
		assertionHelper.assertEquals("salut from 01", resourceBundleRepository.getOne(properties));
	}
	
	@Test
	public void isBonWhenMessage01BundleAddedAndKeyIsGood(){
		Properties properties = new Properties();
		properties.setResourceBundles(Arrays.asList( new Bundle("org.cyk.utility.string.repository.bundle.message_01") ));
		properties.setKey("good");
		assertionHelper.assertEquals("bon", resourceBundleRepository.getOne(properties));
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createDeployment().addPackage(ResourceBundleRepositoryUnitTest.class.getPackage());
	}
}
