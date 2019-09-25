package org.cyk.utility.__kernel__.identifier.resource;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ProtocolUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPorts_http(){
		assertThat(Protocol.HTTP.getPorts()).containsExactly(80);
	}
	
	@Test
	public void getDefaultPortIndex_http(){
		assertThat(Protocol.HTTP.getDefaultPortIndex()).isEqualTo(0);
	}
	
	@Test
	public void getDefaultPort_http(){
		assertThat(Protocol.HTTP.getDefaultPort()).isEqualTo(80);
	}
	
	
}
