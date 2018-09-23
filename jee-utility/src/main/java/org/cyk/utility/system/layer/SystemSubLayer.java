package org.cyk.utility.system.layer;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.regularexpression.RegularExpressionInstance;

public interface SystemSubLayer extends Objectable {

	SystemSubLayer setPackageNameRegularExpression(RegularExpressionInstance instance);
	SystemSubLayer setPackageNameRegularExpression(String expression);
	RegularExpressionInstance getPackageNameRegularExpression();
	RegularExpressionInstance getPackageNameRegularExpression(Boolean injectIfNull);
	Boolean isPackage(String name);
	
	SystemSubLayer setInterfaceNameRegularExpression(RegularExpressionInstance instance);
	SystemSubLayer setInterfaceNameRegularExpression(String expression);
	RegularExpressionInstance getInterfaceNameRegularExpression();
	RegularExpressionInstance getInterfaceNameRegularExpression(Boolean injectIfNull);
	Boolean isInterface(String name);
	
	SystemSubLayer setClassNameRegularExpression(RegularExpressionInstance instance);
	SystemSubLayer setClassNameRegularExpression(String expression);
	RegularExpressionInstance getClassNameRegularExpression();
	RegularExpressionInstance getClassNameRegularExpression(Boolean injectIfNull);
	Boolean isClass(String name);
	
	String getInterfaceNameFromClassName(String className,SystemSubLayer systemSubLayer);
	
	@Override SystemSubLayer setParent(Object parent);
	@Override SystemLayer getParent();
	
	/**/
	
	String PACKAGE_NAME_REGULAR_EXPRESSION_FORMAT = "[.]{0,1}%s[.]{0,1}";
}
