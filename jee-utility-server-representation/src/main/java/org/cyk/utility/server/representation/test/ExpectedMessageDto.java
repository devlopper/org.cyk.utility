package org.cyk.utility.server.representation.test;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.string.StringLocation;
import org.cyk.utility.test.TestExpectedString;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter @Getter @Accessors(chain=true)
public class ExpectedMessageDto extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Inject private TestExpectedString  codeExpectedString;
	@Inject private TestExpectedString  headExpectedString;
	@Inject private TestExpectedString  bodyExpectedString;
	
	public ExpectedMessageDto addCodeExpectedStrings(StringLocation location,String...strings) {
		getCodeExpectedString().getLocationStrings(location, Boolean.TRUE).add(strings);
		return this;
	}
	
	public ExpectedMessageDto addHeadExpectedStrings(StringLocation location,String...strings) {
		getHeadExpectedString().getLocationStrings(location, Boolean.TRUE).add(strings);
		return this;
	}
	
	public ExpectedMessageDto addBodyExpectedStrings(StringLocation location,String...strings) {
		getBodyExpectedString().getLocationStrings(location, Boolean.TRUE).add(strings);
		return this;
	}
}
