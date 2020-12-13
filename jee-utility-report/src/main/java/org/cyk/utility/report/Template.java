package org.cyk.utility.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Template extends AbstractObject implements Serializable {

	private InputStream inputStream;
	private Map<String,Object> arguments;
	private Class<? extends OutputStream> outputStreamClass = ByteArrayOutputStream.class;

}
