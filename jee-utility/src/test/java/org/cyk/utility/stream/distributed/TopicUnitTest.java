package org.cyk.utility.stream.distributed;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class TopicUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isIdentifierEqualToMail_whenTopicIsMailAndNoSystemPropertySet() {
		System.setProperty("stream.distributed.topic.mail", "");
		assertionHelper.assertEquals("mail", Topic.MAIL.initialise().getIdentifier());
	}
	
	@Test
	public void isIdentifierEqualToCustom_whenTopicIsMailAndSystemPropertySetToCustom() {
		System.setProperty("stream.distributed.topic.mail", "custom");
		assertionHelper.assertEquals("custom", Topic.MAIL.initialise().getIdentifier());
	}
	
}
