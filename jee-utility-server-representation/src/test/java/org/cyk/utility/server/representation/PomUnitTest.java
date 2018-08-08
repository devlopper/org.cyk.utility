package org.cyk.utility.server.representation;

import org.cyk.utility.__kernel__.maven.pom.Pom;
import org.cyk.utility.__kernel__.maven.pom.PomBuilderImpl;
import org.junit.Test;

public class PomUnitTest {

	@Test
	public void show() {
		System.out.println(Pom.INSTANCE);
		Pom pom = new Pom();
		pom.setGroupId("gid");
		PomBuilderImpl.marshall(pom);
	}
	
}
