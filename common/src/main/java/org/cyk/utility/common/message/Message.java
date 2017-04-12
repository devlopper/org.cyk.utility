package org.cyk.utility.common.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subject,content,mime="text/html";
	private Date date;
	private Collection<Attachement> attachments;
	
	private String senderIdentifier;
	private Set<String> receiverIdentifiers = new LinkedHashSet<>();
	
	/**/
	
	@Getter @Setter
	public static class Attachement implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String mime;
		private byte[] bytes;
		private String name;
		
	}
}