package org.cyk.utility.mail;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;

import com.icegreen.greenmail.util.GreenMailUtil;

public class MailSenderImplTest extends AbstractMailSenderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __send__(Message message,Properties protocolProperties, Listener listener) throws Exception{
		GreenMailUtil.sendTextEmailTest(CollectionHelper.getFirst(message.getReceivers()).toString(), protocolProperties.getAuthenticationCredentials().getIdentifier()
				, message.getSubject(), message.getBody());
	}

}
