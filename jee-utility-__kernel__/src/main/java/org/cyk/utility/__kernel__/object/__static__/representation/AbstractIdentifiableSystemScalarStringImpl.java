package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper = false,of = "identifier") @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringImpl extends AbstractIdentifiableSystemScalarImpl<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String identifier;
	
	public AbstractIdentifiableSystemScalarStringImpl(String identifier) {
		setIdentifier(identifier);
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public String getSystemIdentifier() {
		return getIdentifier();
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public AbstractIdentifiableSystemScalarStringImpl setSystemIdentifier(String identifier) {
		setIdentifier(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		String identifier = getIdentifier();
		if(identifier == null)
			return super.toString();
		return identifier.toString();
	}
	
}
