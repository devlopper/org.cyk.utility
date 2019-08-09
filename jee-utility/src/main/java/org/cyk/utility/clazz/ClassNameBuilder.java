package org.cyk.utility.clazz;

import java.io.Serializable;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ClassNameBuilder extends FunctionWithPropertiesAsInput<String> {

	String getPackageName();
	ClassNameBuilder setPackageName(String packageName);
	
	String getSimpleName();
	ClassNameBuilder setSimpleName(String simpleName);
	
	NamingModel getSourceNamingModel();
	NamingModel getSourceNamingModel(Boolean injectIfNull);
	ClassNameBuilder setSourceNamingModel(NamingModel sourceNamingModel);
	
	NamingModel getDestinationNamingModel();
	NamingModel getDestinationNamingModel(Boolean injectIfNull);
	ClassNameBuilder setDestinationNamingModel(NamingModel destinationNamingModel);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class NamingModel implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String node,layer,subLayer,suffix;
		private Boolean isSuffixedByLayer;
		
	}
}
