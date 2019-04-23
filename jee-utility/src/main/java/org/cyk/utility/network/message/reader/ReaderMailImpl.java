package org.cyk.utility.network.message.reader;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.network.protocol.Protocol;
import org.cyk.utility.network.protocol.ProtocolInteractiveMailAccess;

public class ReaderMailImpl extends AbstractReadderImpl implements ReaderMail,Serializable {
	private static final long serialVersionUID = 1L;

	private SearchTerm searchTerm;
	
	@Override
	protected void ________execute________(Protocol protocol,Long firstMessageIndex,Long numberOfMessageToRead) throws Exception {
		java.util.Properties properties = System.getProperties();
		if(protocol.getHost()!=null)
			properties.put(ProtocolInteractiveMailAccess.PROPERTY_NAME_HOST, protocol.getHost());
		if(protocol.getPort()!=null)
			properties.put(ProtocolInteractiveMailAccess.PROPERTY_NAME_PORT, protocol.getPort());
		if(protocol.getIsAuthenticationRequired()!=null)
			properties.put(ProtocolInteractiveMailAccess.PROPERTY_NAME_AUTHENTICATION, protocol.getIsAuthenticationRequired());
		if(protocol.getIsSecuredConnectionRequired()!=null)
			properties.put(ProtocolInteractiveMailAccess.PROPERTY_NAME_STARTTLS_ENABLE, protocol.getIsSecuredConnectionRequired());
		
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication((String)protocol.getAuthenticationCredentials().getIdentifier(),(String)protocol.getAuthenticationCredentials().getSecret());
    		}
		});
	
		Store store = session.getStore(ProtocolInteractiveMailAccess.PROPERTY_NAME_IDENTIFIER_OVER_SSL);
		store.connect(protocol.getHost(), (String)protocol.getAuthenticationCredentials().getIdentifier(), (String)protocol.getAuthenticationCredentials().getSecret());
		Folder folder = store.getFolder(ProtocolInteractiveMailAccess.PROPERTY_NAME_FOLDER_INBOX);
		folder.open(Folder.READ_ONLY);

		SearchTerm searchTerm = getSearchTerm();
		if(searchTerm == null) {
			searchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
		}
		
		if(firstMessageIndex == null || firstMessageIndex == 0)
			firstMessageIndex = 1l;
		if(numberOfMessageToRead == null)
			numberOfMessageToRead = 1l;
      
		Long lastMessageIndex = firstMessageIndex + numberOfMessageToRead;
      
		//Message[] messages = folder.getMessages(firstMessageIndex.intValue(), lastMessageIndex.intValue());
		
		// Fetch unseen messages from inbox folder
	    Message[] messages = folder.search(searchTerm);
		
		//Message[] messages = folder.getMessages();
		//Long count = 0l;
		if(__inject__(ArrayHelper.class).isNotEmpty(messages)) {
			for(Message index : messages) {
				//System.out.println("ReaderMailImpl.________execute________() ::: "+index.getFlags());
				//if(!index.isSet(Flag.DELETED)) {
					addMessage(index.getSubject(), index.getContent().toString());
					//count++;
					//if(count == numberOfMessageToRead)
					//	break;
				//}
			}
		}
      
		folder.close(false);
		store.close();
	}

	@Override
	public SearchTerm getSearchTerm() {
		return searchTerm;
	}
	
	@Override
	public ReaderMail setSearchTerm(SearchTerm searchTerm) {
		this.searchTerm = searchTerm;
		return this;
	}
	
	@Override
	protected Class<? extends Protocol> __getProtocolClass__() {
		return ProtocolInteractiveMailAccess.class;
	}

}
