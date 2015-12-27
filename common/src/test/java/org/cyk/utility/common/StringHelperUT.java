package org.cyk.utility.common;

import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.test.unit.AbstractUnitTest;


public class StringHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertAppliedCaseType("mY pHraSE", CaseType.NONE,"mY pHraSE");
		assertAppliedCaseType("mY pHraSE", CaseType.FURL,"My phrase");
	}
	
	private String assertAppliedCaseType(String string,CaseType caseType,String expected){
		return StringHelper.getInstance().applyCaseType(string, caseType);
	}
}
