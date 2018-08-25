package org.cyk.utility.string.repository.bundle;

import java.util.Arrays;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.string.repository.ResourceBundle;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class StringRepositoryResourceBundleUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Inject private StringRepositoryResourceBundle repository;
	
	@Override
	protected void __listenBeforeCallCountIsZero__() {
		super.__listenBeforeCallCountIsZero__();
		repository.addBundle("org.cyk.utility.string.repository.bundle.message");
	}
	
	@Test
	public void isNullWhenKeyNotExist(){
		assertionHelper.assertNull(repository.getOne("sale"));
		assertionHelper.assertNull(repository.getOne("person"));
		assertionHelper.assertNull(repository.getOne("abc"));
	}
	
	@Test
	public void isSalutWhenKeyIsHi(){
		repository.addBundle("org.cyk.utility.string.repository.bundle.message");
		assertionHelper.assertEquals("salut", repository.getOne("hi"));
	}
	
	@Test
	public void isBonWhenKeyIsGood(){
		repository.addBundle("org.cyk.utility.string.repository.bundle.message");
		assertionHelper.assertEquals("bon", repository.getOne("good"));
	}
	
	@Test
	public void isSalutFrom01WhenMessage01BundleAddedAndKeyIsHi(){
		repository.addBundle("org.cyk.utility.string.repository.bundle.message");
		Properties properties = new Properties();
		properties.setResourceBundles(Arrays.asList( new ResourceBundle("org.cyk.utility.string.repository.bundle.message_01") ));
		properties.setKey("hi");
		assertionHelper.assertEquals("salut from 01", repository.getOne(properties));
	}
	
	@Test
	public void isBonWhenMessage01BundleAddedAndKeyIsGood(){
		repository.addBundle("org.cyk.utility.string.repository.bundle.message");
		Properties properties = new Properties();
		properties.setResourceBundles(Arrays.asList( new ResourceBundle("org.cyk.utility.string.repository.bundle.message_01") ));
		properties.setKey("good");
		assertionHelper.assertEquals("bon", repository.getOne(properties));
	}

}
