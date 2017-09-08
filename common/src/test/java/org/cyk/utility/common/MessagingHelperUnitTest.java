package org.cyk.utility.common;

import org.cyk.utility.common.helper.MessagingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MessagingHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static{
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void ping_kycdev_at_gmail_com(){
		MessagingHelper.Send.SendMail sender = new MessagingHelper.Send.SendMail.Adapter.Default();
		sender.setHostAndUserProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*").ping();
	}
}
