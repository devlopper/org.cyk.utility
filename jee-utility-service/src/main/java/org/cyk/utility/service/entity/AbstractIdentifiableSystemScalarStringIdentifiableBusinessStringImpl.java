package org.cyk.utility.service.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl extends AbstractIdentifiableSystemScalarStringImpl implements IdentifiableBusiness<String>,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String code;
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl(String identifier,String code) {
		super(identifier);
		setCode(code);
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl(String code) {
		this(null,code);
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public String getBusinessIdentifier() {
		return getCode();
	}
	
	@Override
	@XmlTransient
	@JsonbTransient
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl setBusinessIdentifier(String identifier) {
		setCode(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		String string = getCode();
		if(StringHelper.isBlank(code))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
}
