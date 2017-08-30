package org.cyk.utility.common;

import java.util.Locale;

import org.cyk.utility.common.helper.LocaleHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class LocaleHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	{
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void get(){
		assertEquals(Locale.FRENCH, LocaleHelper.getInstance().get());	
	}
	
}
