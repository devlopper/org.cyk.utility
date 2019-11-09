package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class PersonDto extends AbstractObjectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastNames;
	private String identityCard;
	private ContactDto contact;
	
	public ContactDto getContact(Boolean injectIfNull) {
		if(contact == null && Boolean.TRUE.equals(injectIfNull))
			contact = new ContactDto();
		return contact;
	}
	
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAMES = "lastNames";
	public static final String FIELD_IDENTITY_CARD = "identityCard";
	public static final String FIELD_CONTACT = "contact";
}
