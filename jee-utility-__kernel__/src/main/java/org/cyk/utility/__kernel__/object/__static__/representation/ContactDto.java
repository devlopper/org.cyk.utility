package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ContactDto extends AbstractObjectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String phoneNumber;
	private String electronicMailAddress;
	
	public static final String FIELD_PHONE_NUMBER = "phoneNumber";
	public static final String FIELD_ELECTRONIC_MAIL_ADDRESS = "electronicMailAddress";
}
