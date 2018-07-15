package org.cyk.utility.clazz;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ClassNameGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements ClassNameGetter , Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 *  1 - packageFrefix01.layer01.packageSuffix01.<className><SUFFIX01> : org.mycompany.persistence.entities.MyEntity
	 *  2 - packageFrefix02.layer02.packageSuffix02.<className><SUFFIX02> : org.mycompany.persistence.api.MyEntityPersistence
	 * 
	 * Based on 1 and suffix Persistence we should get 2
	 * 
	 * source      : {packagePrefixes} , {packageSuffixes} , {classNameSuffixes}
	 * destination : {packagePrefixes} , {packageSuffixes} , {classNameSuffixes}
	 */
	

	@Override
	protected String __execute__() {
		//Class<?> clazz = null;
		
		
		return null;
	}

}
