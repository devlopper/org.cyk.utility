package org.cyk.utility.playground.client.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.playground.client.controller.entities.Person;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isPersonClassInstanceOfData() {
		Collection<Class<?>> classes = ClassHelper.filter(List.of(Person.class.getPackage()), List.of(Data.class), null);
		assertThat(classes).isNotEmpty();
		assertThat(classes).contains(Person.class);
	}
	
}
