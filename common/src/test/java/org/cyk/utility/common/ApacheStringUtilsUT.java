package org.cyk.utility.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class ApacheStringUtilsUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {
		super._execute_();
		
		String[][] a1 = {{"11","12"},{"21","22"}};
		for(String[] s : a1)
			System.out.println(StringUtils.join(s," , "));
		
		System.out.println("-------------------------------------------");
		
		a1 = ArrayUtils.addAll(a1, new String[][]{{"31","32"},{"41","42"}});
		for(String[] s : a1)
			System.out.println(StringUtils.join(s," , "));
	}
	
	

}
