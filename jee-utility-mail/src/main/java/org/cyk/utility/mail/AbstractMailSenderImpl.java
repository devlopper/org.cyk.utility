package org.cyk.utility.mail;

import java.io.Serializable;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.variable.VariableName;

public abstract class AbstractMailSenderImpl extends AbstractObject implements MailSender,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void send(Message message,Properties protocolProperties, Listener listener) {
		if(ConfigurationHelper.isNot(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE)) {
			LogHelper.logWarning("we cannot send mail because it has not been enabled.", getClass());
			return;
		}
		if(protocolProperties == null)
			protocolProperties = Properties.DEFAULT;
		try {
			__send__(message, protocolProperties, listener);
			LogHelper.logInfo("Mail has been sent.", getClass());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected abstract void __send__(Message message,Properties protocolProperties, Listener listener) throws Exception;
}
