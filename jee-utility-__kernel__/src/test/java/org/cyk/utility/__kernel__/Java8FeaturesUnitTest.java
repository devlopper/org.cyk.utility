package org.cyk.utility.__kernel__;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class Java8FeaturesUnitTest {

	@Test
	public void nullCollection() {
		Collection<String> collection = null;
		Optional.ofNullable(collection).filter(x -> x.contains("a"));
	}
	
}
