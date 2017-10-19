package org.cyk.utility.common;

import org.cyk.utility.common.model.Interval;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class IntervalUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void set(){
		assertEquals(new Interval().setFrom(1).getFrom(), 1);
		assertEquals(new Interval().setFrom(1l).getFrom(), 1l);
		assertEquals(new Interval().setFrom(1.2).getFrom(), 1.2);
		assertEquals(new Interval().setFrom(1.2d).getFrom(), 1.2d);
	}
}
