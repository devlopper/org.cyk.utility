package org.cyk.utility.mail.postmark;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.mail.AbstractMailSenderImpl;
import org.cyk.utility.mail.Message;
import org.cyk.utility.mail.Properties;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;

@org.cyk.utility.mail.postmark.Postmark
public class MailSenderImpl extends AbstractMailSenderImpl implements Serializable {

	private static ApiClient API_CLIENT;
	
	@Override
	protected void __send__(Message message, Properties protocolProperties, Listener listener) throws Exception {
		com.wildbit.java.postmark.client.data.model.message.Message __message__ = new com.wildbit.java.postmark.client.data.model.message.Message();
		
		if(CollectionHelper.isNotEmpty(message.getReceivers()))
			__message__.setTo(message.getReceivers().stream().map(x -> x.toString()).collect(Collectors.toList()));		
		
		if(StringHelper.isNotBlank(protocolProperties.getFrom()))
			__message__.setFrom(protocolProperties.getFrom());
		if(StringHelper.isNotBlank(message.getSubject()))
			__message__.setSubject(message.getSubject());
		if(StringHelper.isNotBlank(message.getBody()))
			__message__.setHtmlBody(message.getBody());
		
		if(CollectionHelper.isNotEmpty(message.getAttachments())) {
			for(Message.Attachment attachment : message.getAttachments()) {
				__message__.addAttachment(FileHelper.concatenateNameAndExtension(attachment.getName(), attachment.getExtension()), attachment.getBytes()
						, attachment.getExtension());
			}
		}
		
		getClient(protocolProperties.getAuthenticationCredentials().getIdentifier()).deliverMessage(__message__);
	}

	private static ApiClient getClient(String token) {
		if(API_CLIENT == null)
			API_CLIENT = Postmark.getApiClient(token);
		return API_CLIENT;
	}
}