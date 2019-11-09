package org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Embeddable
public class Person extends AbstractObjectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @Column(name = COLUMN_FIRST_NAME) private String firstName;
	@Column(name = COLUMN_LAST_NAMES) private String lastNames;	
	@Column(name = COLUMN_IDENTITY_CARD) private String identityCard;
	@Embedded private Contact contact;
	
	public Person(String firstName,String lastNames,String identityCard,Contact contact) {
		this.firstName = firstName;
		this.lastNames = lastNames;
		this.identityCard = identityCard;
		this.contact = contact;
	}
	
	public Contact getContact(Boolean injectIfNull) {
		if(contact == null && Boolean.TRUE.equals(injectIfNull))
			contact = new Contact();
		return contact;
	}
	
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAMES = "lastNames";
	public static final String FIELD_IDENTITY_CARD = "identityCard";
	public static final String FIELD_CONTACT = "contact";
	
	public static final String COLUMN_FIRST_NAME = FIELD_FIRST_NAME;
	public static final String COLUMN_LAST_NAMES = FIELD_LAST_NAMES;
	public static final String COLUMN_IDENTITY_CARD = FIELD_IDENTITY_CARD;
}
