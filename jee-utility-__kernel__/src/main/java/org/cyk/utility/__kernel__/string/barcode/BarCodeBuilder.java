package org.cyk.utility.__kernel__.string.barcode;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface BarCodeBuilder {

	byte[] build(String string,Parameters parameters);
	
	/**/
	
	static BarCodeBuilder getInstance() {
		return Helper.getInstance(BarCodeBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public class Parameters extends AbstractObject implements Serializable {

		private Object format;
		private Integer width,height,resolution;
		private String extension = "png";
		private String mimeType;
	}

}
