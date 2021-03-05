package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.Strings;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class StringsUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void concatenate_empty(){
		assertThat(new Strings().concatenate()).isEqualTo(null);
	}
	
	@Test
	public void concatenate(){
		assertThat(new Strings().select("").concatenate()).isEqualTo("");
	}
}