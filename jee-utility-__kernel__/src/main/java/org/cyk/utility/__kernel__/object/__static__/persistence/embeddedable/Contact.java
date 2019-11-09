package org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Embeddable
public class Contact extends AbstractObjectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = FIELD_PHONE_NUMBER) private String phoneNumber;
	@Email @Column(name = FIELD_ELECTRONIC_MAIL_ADDRESS) private String electronicMailAddress;
	
	public Contact(String phoneNumber,String electronicMailAddress) {
		this.phoneNumber = phoneNumber;
		this.electronicMailAddress = electronicMailAddress;
	}
	
	public static final String FIELD_PHONE_NUMBER = "phoneNumber";
	public static final String FIELD_ELECTRONIC_MAIL_ADDRESS = "electronicMailAddress";
	
	public static final String COLUMN_PHONE_NUMBER = FIELD_PHONE_NUMBER;
	public static final String COLUMN_ELECTRONIC_MAIL_ADDRESS = FIELD_ELECTRONIC_MAIL_ADDRESS;
}
