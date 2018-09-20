package org.cyk.utility.system.layer;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.regularexpression.RegularExpressionInstance;

public abstract class AbstractSystemSubLayerImpl extends AbstractObject implements SystemSubLayer,Serializable {
	private static final long serialVersionUID = 1L;

	private RegularExpressionInstance packageNameRegularExpression,classNameRegularExpression,interfaceNameRegularExpression;
	
	@Override
	public RegularExpressionInstance getPackageNameRegularExpression() {
		return packageNameRegularExpression;
	}
	
	@Override
	public RegularExpressionInstance getPackageNameRegularExpression(Boolean injectIfNull) {
		RegularExpressionInstance instance = getPackageNameRegularExpression();
		if(instance == null && Boolean.TRUE.equals(injectIfNull))
			setPackageNameRegularExpression(instance = __inject__(RegularExpressionInstance.class));
		return instance;
	}
	
	@Override
	public SystemSubLayer setPackageNameRegularExpression(RegularExpressionInstance instance) {
		this.packageNameRegularExpression = instance;
		return this;
	}
	
	@Override
	public SystemSubLayer setPackageNameRegularExpression(String expression) {
		getPackageNameRegularExpression(Boolean.TRUE).setExpression(expression);
		return this;
	}
	
	@Override
	public Boolean isPackage(String name) {
		RegularExpressionInstance regularExpression = getPackageNameRegularExpression();
		return regularExpression == null ? Boolean.FALSE : regularExpression.match(name);
	}
	
	@Override
	public RegularExpressionInstance getClassNameRegularExpression() {
		return classNameRegularExpression;
	}
	
	@Override
	public RegularExpressionInstance getClassNameRegularExpression(Boolean injectIfNull) {
		RegularExpressionInstance instance = getClassNameRegularExpression();
		if(instance == null && Boolean.TRUE.equals(injectIfNull))
			setClassNameRegularExpression(instance = __inject__(RegularExpressionInstance.class));
		return instance;
	}
	
	@Override
	public SystemSubLayer setClassNameRegularExpression(RegularExpressionInstance regularExpression) {
		this.classNameRegularExpression = regularExpression;
		return this;
	}
	
	@Override
	public SystemSubLayer setClassNameRegularExpression(String expression) {
		getClassNameRegularExpression(Boolean.TRUE).setExpression(expression);
		return this;
	}
	
	@Override
	public Boolean isClass(String name) {
		RegularExpressionInstance regularExpression = getClassNameRegularExpression();
		return regularExpression == null ? Boolean.FALSE : regularExpression.match(name);
	}
	
	@Override
	public RegularExpressionInstance getInterfaceNameRegularExpression() {
		return interfaceNameRegularExpression;
	}
	
	@Override
	public RegularExpressionInstance getInterfaceNameRegularExpression(Boolean injectIfNull) {
		RegularExpressionInstance instance = getInterfaceNameRegularExpression();
		if(instance == null && Boolean.TRUE.equals(injectIfNull))
			setInterfaceNameRegularExpression(instance = __inject__(RegularExpressionInstance.class));
		return instance;
	}
	
	@Override
	public SystemSubLayer setInterfaceNameRegularExpression(RegularExpressionInstance regularExpression) {
		this.interfaceNameRegularExpression = regularExpression;
		return this;
	}
	
	@Override
	public SystemSubLayer setInterfaceNameRegularExpression(String expression) {
		getInterfaceNameRegularExpression(Boolean.TRUE).setExpression(expression);
		return this;
	}
	
	@Override
	public Boolean isInterface(String name) {
		RegularExpressionInstance regularExpression = getInterfaceNameRegularExpression();
		return regularExpression == null ? Boolean.FALSE : regularExpression.match(name);
	}
}
