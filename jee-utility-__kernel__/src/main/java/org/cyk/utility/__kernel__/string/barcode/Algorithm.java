package org.cyk.utility.__kernel__.string.barcode;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Algorithm extends AbstractObject implements Serializable {

	private Object format;
	private Integer width,height;
	private String extension;
}
