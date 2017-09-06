package org.cyk.utility.common.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Deprecated
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subject,content,mime="text/html";
	private Date date;
	private Collection<Attachement> attachments;
	
	private String senderIdentifier;
	private Set<String> receiverIdentifiers = new LinkedHashSet<>();
	
	/**/
	
	public Message addReceiverIdentifiers(String...identifiers){
		for(String identifier : identifiers)
			receiverIdentifiers.add(identifier);
		return this;
	}
	
	public Message addAttachement(byte[] bytes,String mime,String name){
		if(attachments==null)
			attachments = new ArrayList<>();
		Attachement attachement = new Attachement();
		attachement.setBytes(bytes);
		attachement.setMime(mime);
		attachement.setName(name);
		attachments.add(attachement);
		return this;
	}
	
	/**/
	
	@Getter @Setter
	public static class Attachement implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String mime;
		private byte[] bytes;
		private String name;
		
	}
}