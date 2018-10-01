package org.cyk.utility.server.representation.test;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
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
	
	
	
}
